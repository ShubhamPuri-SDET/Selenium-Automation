import PyPDF2
import pytesseract
from pdf2image import convert_from_path
import requests
from bs4 import BeautifulSoup
from termcolor import colored  # For coloring the output
import re

# Function to extract text from PDF using PyPDF2 and OCR (if necessary)
def extract_text_from_pdf(pdf_path, page_number):
    try:
        with open(pdf_path, 'rb') as file:
            # Initialize PDF reader
            reader = PyPDF2.PdfReader(file)
            # Check if the page number is valid
            if page_number < 1 or page_number > len(reader.pages):
                print(colored(f"Invalid page number. PDF has {len(reader.pages)} pages.", "red"))
                return None
            # Extract text from the specified page
            page = reader.pages[page_number - 1]  # Convert to 0-based index
            return page.extract_text()
    except Exception as e:
        print(colored(f"Error extracting text from PDF: {e}", "red"))
        return None

# Function to fetch and clean email content from URL (HTML to text)
def fetch_email_content(url):
    try:
        response = requests.get(url)  # Fetch the content from the given URL
        if response.status_code == 200:
            # Use BeautifulSoup to parse HTML content and get plain text
            soup = BeautifulSoup(response.text, "html.parser")
            email_text = soup.get_text(separator="\n", strip=True)  # Extract plain text
            return email_text.strip()
        else:
            print(f"Failed to fetch email content. Status code: {response.status_code}")
            return None
    except Exception as e:
        print(f"Error fetching email content: {e}")
        return None

# Function to save extracted text as backup in separate files (with UTF-8 encoding)
def save_backup(pdf_text, email_text, pdf_backup_file="pdf_backup.txt", email_backup_file="email_backup.txt"):
    try:
        # Save PDF text
        with open(pdf_backup_file, "w", encoding="utf-8") as file:
            file.write(pdf_text)
        print(f"PDF text saved to {pdf_backup_file}")

        # Save email text
        with open(email_backup_file, "w", encoding="utf-8") as file:
            file.write(email_text)
        print(f"Email content saved to {email_backup_file}")
    except Exception as e:
        print(f"Error saving backup: {e}")

# Function to normalize text (remove unnecessary whitespaces)
def normalize_text(text):
    return re.sub(r'\s+', ' ', text.strip())

# Function to compare and highlight matches/mismatches with better output format
def compare_texts_and_highlight(pdf_text, email_text):
    # Normalize both texts before comparison
    pdf_text = normalize_text(pdf_text)
    email_text = normalize_text(email_text)

    # Split PDF and email text into lines for better comparison
    pdf_lines = pdf_text.splitlines()
    email_lines = email_text.splitlines()

    print("\nComparison Result:\n")

    matches = []
    mismatches = []

    # Compare PDF lines to email content (searching each line of the PDF in the email text)
    for pdf_line in pdf_lines:
        pdf_line = pdf_line.strip()
        match_found = False  # Flag to track if the line is found in email content

        for email_line in email_lines:
            email_line = email_line.strip()

            if pdf_line == email_line:
                # If match found, store it in matches and break out of inner loop
                matches.append((pdf_line, email_line))
                match_found = True
                break

        if not match_found:
            # If no match found, store it in mismatches
            mismatches.append((pdf_line, "No match found in email"))

    # Output matching lines (passing)
    print("\n== Matching (Passing) Lines ==\n")
    for match in matches:
        print(colored(f"PDF Text: {match[0]}", "green"))
        print(colored(f"Email Text: {match[1]}", "green"))
        print("="*50)  # Separator between matching lines

    # Output mismatched lines (failing)
    print("\n== Mismatched (Failing) Lines ==\n")
    for mismatch in mismatches:
        print(colored(f"PDF Text: {mismatch[0]}", "red"))
        print(colored(f"Email Text: {mismatch[1]}", "red"))
        print("="*50)  # Separator between mismatched lines

# Helper function to normalize text (strip spaces, lower case, etc.)
def normalize_text(text):
    # Normalize by stripping extra spaces and lowercasing the text
    return text.strip().lower()

# Main function
if __name__ == "__main__":
    pdf_path = input("Enter PDF file path: ").strip()
    email_url = input("Enter email content URL: ").strip()

    if not pdf_path or not email_url:
        print("Error: PDF path and Email URL must be provided.")
        exit()

    # Ask the user for the page number to extract text from
    try:
        page_number = int(input("Enter the page number to extract text from: "))
    except ValueError:
        print(colored("Please enter a valid integer for the page number.", "red"))
        exit()

    # Extract text from the specified page of the PDF
    pdf_text = extract_text_from_pdf(pdf_path, page_number)
    if not pdf_text:
        exit()

    # Fetch and clean the email content
    email_text = fetch_email_content(email_url)
    if not email_text:
        exit()

    # Save backups for reference
    save_backup(pdf_text, email_text)

    # Compare the texts and print the highlighted results
    compare_texts_and_highlight(pdf_text, email_text)


# https://m.e.genentech.com/nl/jsp/m.jsp?c=%40AVgtZu9Z9RpqKZTcvGdRMRV%2FwszAn5ClQcrBl5QriZk%3D
# C:\Users\shubham.puri\Desktop\TestWeb\ENSPRYNG.pdf