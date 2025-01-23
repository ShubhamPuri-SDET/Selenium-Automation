import fitz  # PyMuPDF for PDF text extraction
from bs4 import BeautifulSoup  # For HTML parsing
import requests  # For fetching HTML from a URL
from difflib import SequenceMatcher
import tkinter as tk
from tkinter import ttk, scrolledtext

def extract_text_from_pdf(pdf_path, page_number):
    """
    Extracts text from a specific page in a PDF file using PyMuPDF.
    """
    try:
        pdf_document = fitz.open(pdf_path)
        if page_number < 1 or page_number > len(pdf_document):
            return f"Error: Page {page_number} does not exist in the PDF."

        page = pdf_document[page_number - 1]
        text_blocks = page.get_text("blocks")
        pdf_document.close()
        # Extract the actual text content from the blocks
        return [block[4] for block in text_blocks if block[4].strip()]
    except Exception as e:
        print(f"Error reading PDF: {e}")
        return []

def extract_text_from_url(html_url):
    """
    Fetches HTML content from a URL and extracts visible text using BeautifulSoup.
    """
    try:
        response = requests.get(html_url)
        response.raise_for_status()
        soup = BeautifulSoup(response.text, "html.parser")
        # Extract text and split it into blocks by paragraphs
        return [p.get_text().strip() for p in soup.find_all("p") if p.get_text().strip()]
    except Exception as e:
        print(f"Error fetching HTML from URL: {e}")
        return []

def compare_texts(pdf_text, html_text):
    """
    Compare the PDF text with the HTML text.
    Returns a tuple of (result, difference if any).
    """
    if pdf_text == html_text:
        return "Pass", ""
    
    matcher = SequenceMatcher(None, pdf_text, html_text)
    diff = matcher.ratio()
    
    if diff == 1:
        return "Pass", ""
    elif diff >= 0.7:  # Partial match
        difference = generate_diff(pdf_text, html_text)
        return "Partial", difference
    else:
        return "Fail", generate_diff(pdf_text, html_text)

def generate_diff(pdf_text, html_text):
    """
    Generates the difference between PDF and HTML text.
    """
    diff = SequenceMatcher(None, pdf_text, html_text)
    diff_blocks = list(diff.get_opcodes())
    differences = []
    for tag, i1, i2, j1, j2 in diff_blocks:
        if tag == 'replace' or tag == 'delete':
            differences.append(f"PDF: {pdf_text[i1:i2]} | HTML: {html_text[j1:j2]}")
        elif tag == 'insert':
            differences.append(f"HTML: {html_text[j1:j2]}")
    return '\n'.join(differences)

def update_comparison(pdf_blocks, html_blocks, diff_results):
    """
    Updates the GUI with the current comparison results.
    """
    pdf_textbox.delete(1.0, tk.END)
    html_textbox.delete(1.0, tk.END)
    diff_textbox.delete(1.0, tk.END)

    for i, (pdf_text, html_text, result, difference) in enumerate(diff_results):
        pdf_textbox.insert(tk.END, f"Block {i+1}:\n{pdf_text}\n\n", "block")
        html_textbox.insert(tk.END, f"Block {i+1}:\n{html_text}\n\n", "block")

        if result == "Pass":
            diff_textbox.insert(tk.END, f"Block {i+1} - Result: PASS\n", "match")
        elif result == "Partial":
            diff_textbox.insert(tk.END, f"Block {i+1} - Result: PARTIAL MATCH\n", "partial")
            diff_textbox.insert(tk.END, f"Differences:\n{difference}\n", "difference")
        else:
            diff_textbox.insert(tk.END, f"Block {i+1} - Result: FAIL\n", "fail")
            diff_textbox.insert(tk.END, f"Differences:\n{difference}\n", "fail")

def on_compare():
    """
    Triggered when the 'Compare' button is clicked.
    """
    try:
        page_number = int(page_number_entry.get())
    except ValueError:
        diff_textbox.insert(tk.END, "Invalid page number. Please enter a valid number.\n", "error")
        return

    pdf_blocks = extract_text_from_pdf(pdf_path_entry.get(), page_number)
    if isinstance(pdf_blocks, str):  # Error message
        diff_textbox.insert(tk.END, pdf_blocks + "\n", "error")
        return

    html_blocks = extract_text_from_url(html_url_entry.get())
    
    diff_results = []
    for pdf_text in pdf_blocks:
        found = False
        for html_text in html_blocks:
            result, difference = compare_texts(pdf_text, html_text)
            if result == "Pass":
                diff_results.append((pdf_text, html_text, result, ""))
                found = True
                break
            elif result == "Partial":
                diff_results.append((pdf_text, html_text, result, difference))
                found = True
                break
        if not found:
            diff_results.append((pdf_text, "", "Fail", f"PDF block not found in HTML.\nPDF: {pdf_text}"))

    update_comparison(pdf_blocks, html_blocks, diff_results)

# Create the GUI
root = tk.Tk()
root.title("Live PDF-HTML Comparison Tool")
root.geometry("1200x700")

# Input fields
ttk.Label(root, text="PDF Path:").grid(row=0, column=0, padx=10, pady=10, sticky="w")
pdf_path_entry = ttk.Entry(root, width=80)
pdf_path_entry.grid(row=0, column=1, padx=10, pady=10, sticky="w")

ttk.Label(root, text="HTML URL:").grid(row=1, column=0, padx=10, pady=10, sticky="w")
html_url_entry = ttk.Entry(root, width=80)
html_url_entry.grid(row=1, column=1, padx=10, pady=10, sticky="w")

ttk.Label(root, text="PDF Page Number:").grid(row=2, column=0, padx=10, pady=10, sticky="w")
page_number_entry = ttk.Entry(root, width=10)
page_number_entry.grid(row=2, column=1, padx=10, pady=10, sticky="w")
page_number_entry.insert(0, "2")  # Default page number

compare_button = ttk.Button(root, text="Compare", command=on_compare)
compare_button.grid(row=3, column=1, padx=10, pady=10, sticky="e")

# Text areas for PDF, HTML, and Differences
pdf_textbox = scrolledtext.ScrolledText(root, wrap=tk.WORD, width=40, height=30)
pdf_textbox.grid(row=4, column=0, padx=10, pady=10)
pdf_textbox.tag_configure("block", foreground="black")

html_textbox = scrolledtext.ScrolledText(root, wrap=tk.WORD, width=40, height=30)
html_textbox.grid(row=4, column=1, padx=10, pady=10)
html_textbox.tag_configure("block", foreground="black")

diff_textbox = scrolledtext.ScrolledText(root, wrap=tk.WORD, width=60, height=30)
diff_textbox.grid(row=4, column=2, padx=10, pady=10)
diff_textbox.tag_configure("highlight", foreground="red")
diff_textbox.tag_configure("match", foreground="green")
diff_textbox.tag_configure("partial", foreground="orange")
diff_textbox.tag_configure("difference", foreground="blue")
diff_textbox.tag_configure("fail", foreground="red")
diff_textbox.tag_configure("error", foreground="orange")

root.mainloop()
