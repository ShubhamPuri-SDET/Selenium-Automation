package Main;

import java.io.*;
import java.nio.file.*;
import java.time.Duration;
import java.util.*;
import org.apache.pdfbox.pdmodel.*;
import org.apache.pdfbox.text.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.*;
import org.openqa.selenium.support.ui.*;

public class EmailAutomation {
    
    public static void main(String[] args) throws Exception {
        String emailText = extractTextFromEmail("https://view.email.professionalnetwork2.com/?qs=22971515e55041505c20ca6b9068a86bb8fc80734f48cf833280128444ff2e3ae0ea3537d5e64e14763f028e790aeb8d9dbb38c272cb8ad1df739812d732c0e89585ce1522338f8a8401c562b186d136");
        String pdfText = extractTextFromPDF("C:\\Users\\shubham.puri\\Genentech\\Alt Tag\\DET-Genentech\\Email\\ThirdPartyEmails\\data\\pdf\\Alecensa AFD.pdf", 2);
        List<String> differences = compareTexts(emailText, pdfText);
        highlightDifferencesInBrowser(differences, "https://view.email.professionalnetwork2.com/?qs=22971515e55041505c20ca6b9068a86bb8fc80734f48cf833280128444ff2e3ae0ea3537d5e64e14763f028e790aeb8d9dbb38c272cb8ad1df739812d732c0e89585ce1522338f8a8401c562b186d136");
    }

    public static String extractTextFromEmail(String url) {
        WebDriver driver = new ChromeDriver();
        driver.get(url);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.tagName("body")));
        String emailText = driver.findElement(By.tagName("body")).getText();
        driver.quit();
        return emailText;
    }

    public static String extractTextFromPDF(String filePath, int startPage) throws IOException {
        PDDocument document = PDDocument.load(new File(filePath));
        PDFTextStripper pdfStripper = new PDFTextStripper();
        pdfStripper.setStartPage(startPage);
        pdfStripper.setEndPage(startPage); // Ensure only page 2 is extracted
        String pdfText = pdfStripper.getText(document);
        document.close();
        return pdfText;
    }

    public static List<String> compareTexts(String text1, String text2) {
        List<String> differences = new ArrayList<>();
        String[] paragraphs1 = text1.split("\n\n");
        String[] paragraphs2 = text2.split("\n\n");
        int maxLength = Math.max(paragraphs1.length, paragraphs2.length);
        for (int i = 0; i < maxLength; i++) {
            String para1 = i < paragraphs1.length ? paragraphs1[i] : "";
            String para2 = i < paragraphs2.length ? paragraphs2[i] : "";
            if (!para1.equals(para2)) {
                String difference = "Difference at paragraph " + (i + 1) + ":\nEmail: " + para1 + "\nPDF: " + para2;
                differences.add(difference);
                System.out.println(difference); // Print differences live
            }
        }
        return differences;
    }

    public static void highlightDifferencesInBrowser(List<String> differences, String url) throws IOException, InterruptedException {
        WebDriver driver = new ChromeDriver();
        driver.get(url);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.tagName("body")));
        
        for (String difference : differences) {
            String[] parts = difference.split("\nPDF: ");
            if (parts.length > 1) {
                String emailText = parts[0].replace("Difference at paragraph ", "").replaceFirst(":\\nEmail: ", "");
                String pdfText = parts[1];
                ((JavascriptExecutor) driver).executeScript(
                    "var emailText = arguments[0];" +
                    "var pdfText = arguments[1];" +
                    "document.body.innerHTML = document.body.innerHTML.replace(new RegExp(emailText, 'g'), '<span style=\"background-color:yellow;\">' + emailText + '</span>');" +
                    "document.body.innerHTML = document.body.innerHTML.replace(new RegExp(pdfText, 'g'), '<span style=\"background-color:yellow;\">' + pdfText + '</span>');",
                    emailText, pdfText);
            }
        }
        
        // Save the modified HTML content to a file
        String modifiedHtml = (String) ((JavascriptExecutor) driver).executeScript("return document.documentElement.outerHTML;");
        saveHtmlToFile(modifiedHtml, "highlighted_email.html");
        
        // Keep the browser open for 30 seconds to view the highlights
        Thread.sleep(30000);
        driver.quit();
    }

    public static void saveHtmlToFile(String htmlContent, String filePath) throws IOException {
        Files.write(Paths.get(filePath), htmlContent.getBytes());
    }
}
