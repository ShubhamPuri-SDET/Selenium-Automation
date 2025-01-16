from selenium import webdriver
from selenium.webdriver.chrome.service import Service
from selenium.webdriver.common.by import By
from selenium.webdriver.support.ui import WebDriverWait
from selenium.webdriver.support import expected_conditions as EC
import time
import fitz  # PyMuPDF
import difflib
import requests

def download_email_html(url, output_path):
    driver = webdriver.Chrome()
    driver.get(url)
    wait = WebDriverWait(driver, 10)
    wait.until(EC.visibility_of_element_located((By.TAG_NAME, "body")))
    email_html = driver.page_source
    with open(output_path, 'w', encoding='utf-8') as file:
        file.write(email_html)
    driver.quit()

def extract_text_from_html(file_path):
    with open(file_path, 'r', encoding='utf-8') as file:
        html_content = file.read()
    driver = webdriver.Chrome()
    driver.get("data:text/html;charset=utf-8," + html_content)
    wait = WebDriverWait(driver, 10)
    wait.until(EC.visibility_of_element_located((By.TAG_NAME, "body")))
    email_text = driver.find_element(By.TAG_NAME, "body").text
    driver.quit()
    return email_text, html_content

def extract_text_from_pdf(file_path, start_page):
    document = fitz.open(file_path)
    pdf_text = ""
    for page_num in range(start_page - 1, document.page_count):
        page = document.load_page(page_num)
        pdf_text += page.get_text()
    return pdf_text

def compare_texts(text1, text2):
    differences = []
    paragraphs1 = text1.split("\n\n")
    paragraphs2 = text2.split("\n\n")
    max_length = max(len(paragraphs1), len(paragraphs2))
    for i in range(max_length):
        para1 = paragraphs1[i] if i < len(paragraphs1) else ""
        para2 = paragraphs2[i] if i < len(paragraphs2) else ""
        if para1 != para2:
            difference = f"Difference at paragraph {i + 1}:\nEmail: {para1}\nPDF: {para2}"
            differences.append(difference)
            print(difference)  # Print differences live
    return differences

def highlight_differences_in_html(differences, html_content, output_path):
    for difference in differences:
        parts = difference.split("\nPDF: ")
        if len(parts) > 1:
            pdf_text = parts[1]
            email_text = parts[0].split("\nEmail: ")[1]
            diff = difflib.ndiff(email_text.split(), pdf_text.split())
            highlighted_text = ""
            for word in diff:
                if word.startswith("+ "):
                    highlighted_text += f'<span style="background-color:yellow;">{word[2:]}</span> '
                else:
                    highlighted_text += word[2:] + " "
            html_content = html_content.replace(email_text, highlighted_text)
    with open(output_path, 'w', encoding='utf-8') as file:
        file.write(html_content)

def highlight_differences_in_pdf(differences, file_path, output_path):
    document = fitz.open(file_path)
    for difference in differences:
        parts = difference.split("\nPDF: ")
        if len(parts) > 1:
            pdf_text = parts[1]
            email_text = parts[0].split("\nEmail: ")[1]
            diff = difflib.ndiff(email_text.split(), pdf_text.split())
            highlighted_words = [word[2:] for word in diff if word.startswith("+ ")]
            for page_num in range(document.page_count):
                page = document.load_page(page_num)
                for word in highlighted_words:
                    rects = page.search_for(word)
                    for rect in rects:
                        highlight = page.add_highlight_annot(rect)
                        highlight.set_colors(stroke=(1, 1, 0))  # Yellow color
                        highlight.update()
    document.save(output_path)

if __name__ == "__main__":
    email_url = "https://view.email.professionalnetwork2.com/?qs=22971515e55041505c20ca6b9068a86bb8fc80734f48cf833280128444ff2e3ae0ea3537d5e64e14763f028e790aeb8d9dbb38c272cb8ad1df739812d732c0e89585ce1522338f8a8401c562b186d136"
    email_html_path = "C:\\Users\\shubham.puri\\Desktop\\TestMedia\\Diff\\email.html"
    highlighted_html_path = "C:\\Users\\shubham.puri\\Desktop\\TestMedia\\Diff\\highlighted_email.html"
    pdf_path = "C:\\Users\\shubham.puri\\Genentech\\Alt Tag\\DET-Genentech\\Email\\ThirdPartyEmails\\data\\pdf\\Alecensa AFD.pdf"
    output_pdf_path = "C:\\Users\\shubham.puri\\Desktop\\TestMedia\\Diff\\highlighted_differences.pdf"

    download_email_html(email_url, email_html_path)
    email_text, html_content = extract_text_from_html(email_html_path)
    pdf_text = extract_text_from_pdf(pdf_path, 2)
    differences = compare_texts(email_text, pdf_text)
    highlight_differences_in_html(differences, html_content, highlighted_html_path)
    highlight_differences_in_pdf(differences, pdf_path, output_pdf_path)
