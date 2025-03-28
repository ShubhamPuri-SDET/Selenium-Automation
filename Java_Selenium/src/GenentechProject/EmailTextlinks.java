package GenentechProject;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.FileOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class EmailTextlinks {

    public static void main(String[] args) throws IOException {

        WebDriver driver = new ChromeDriver();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));

        String url = "https://m.e.genentech.com/nl/jsp/m.jsp?c=%40AVgtZu9Z9RpqKZTcvGdRMRV%2FwszAn5ClQcrBl5QriZk%3D";
        driver.get(url);

        // Ensure page is fully loaded
        wait.until(driver1 -> ((JavascriptExecutor) driver1)
                .executeScript("return document.readyState").equals("complete"));

        // Handle iframe if present
        try {
            WebElement iframe = driver.findElement(By.tagName("iframe"));
            driver.switchTo().frame(iframe);
            System.out.println("Switched to iframe.");
        } catch (NoSuchElementException e) {
            System.out.println("No iframe found, continuing...");
        }

        // Wait until at least one link is visible
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.tagName("a")));

        // Get all links
        List<WebElement> links = driver.findElements(By.tagName("a"));
        List<String[]> linkData = new ArrayList<>();

        for (WebElement link : links) {
            String linkURL = link.getAttribute("href");
            if (linkURL == null || linkURL.isEmpty()) {
                continue;
            }

            // Get link text or image alt attribute
            String linkText = link.getText().trim();
            if (linkText.isEmpty()) {
                try {
                    WebElement img = link.findElement(By.tagName("img"));
                    linkText = (img.getAttribute("alt") != null) ? img.getAttribute("alt") : "No Text";
                } catch (NoSuchElementException e) {
                    linkText = "No Text";
                }
            }

            // If the link is a phone number (tel:), handle it separately
            if (linkURL.startsWith("tel:")) {
                System.out.println("Phone number link detected: " + linkURL);
                String extractedNumber = handlePhoneNumberLink(driver, wait, linkURL);
                linkData.add(new String[]{linkText, extractedNumber, "Phone Number"});
                continue;
            }

            // Open the link in a new tab
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("window.open(arguments[0], '_blank');", linkURL);
            ArrayList<String> tabs = new ArrayList<>(driver.getWindowHandles());
            driver.switchTo().window(tabs.get(1)); // Switch to new tab

            try {
                // Wait for page to load completely
                wait.until(driver1 -> ((JavascriptExecutor) driver1)
                        .executeScript("return document.readyState").equals("complete"));

                // Get the actual landed URL
                String landedURL = driver.getCurrentUrl();

                // Get HTTP status code of landed URL
                int statusCode = getStatusCode(landedURL);

                // Print details
                System.out.println("Text of the link: " + linkText);
                System.out.println("Text link URL " + landedURL);
                System.out.println("Status Code: " + statusCode);

                // Store data for Excel
                linkData.add(new String[]{linkText, landedURL, String.valueOf(statusCode)});

            } catch (Exception e) {
                System.out.println("Error navigating: " + linkURL + " | " + e.getMessage());
            }

            // Close the new tab and switch back
            driver.close();
            driver.switchTo().window(tabs.get(0));
        }

        // Write data to Excel
        writeToExcel(linkData);

        driver.quit();
    }

    // Method to handle phone number links
    public static String handlePhoneNumberLink(WebDriver driver, WebDriverWait wait, String phoneNumber) {
        try {
            // Click the phone number link
            driver.findElement(By.xpath("//a[@href='" + phoneNumber + "']")).click();
            System.out.println("Clicked on phone number link: " + phoneNumber);

            // Wait for the 'Pick an App' popup and try to close it (if it's a browser alert)
            try {
                wait.until(ExpectedConditions.alertIsPresent());
                Alert alert = driver.switchTo().alert();
                alert.dismiss();
                System.out.println("Closed browser alert popup.");
            } catch (Exception e) {
                System.out.println("No browser alert popup appeared, continuing...");
            }

            // Switch to the new page
            ArrayList<String> tabs = new ArrayList<>(driver.getWindowHandles());
            driver.switchTo().window(tabs.get(1));

            // Wait for the search bar and capture the mobile number
            WebElement searchBar = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("search-bar")));
            String searchedNumber = searchBar.getAttribute("value");

            System.out.println("Extracted phone number from search bar: " + searchedNumber);

            driver.close();
            driver.switchTo().window(tabs.get(0)); // Switch back to the email page

            return searchedNumber;
        } catch (Exception e) {
            System.out.println("Error handling phone number link: " + phoneNumber + " | " + e.getMessage());
            return "Error Fetching Number";
        }
    }

    // Method to get HTTP status code
    public static int getStatusCode(String urlString) {
        try {
            URL url = new URL(urlString);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(5000);
            connection.setReadTimeout(5000);
            connection.connect();
            return connection.getResponseCode();
        } catch (Exception e) {
            System.out.println("Error fetching status code for: " + urlString);
            return -1;
        }
    }

    // Method to write data to Excel
    public static void writeToExcel(List<String[]> data) throws IOException {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Link Data");

        // Create header row
        Row headerRow = sheet.createRow(0);
        String[] headers = {"Text of Link", "Landed URL", "Status Code"};
        for (int i = 0; i < headers.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(headers[i]);
        }

        // Fill data rows
        int rowNum = 1;
        for (String[] rowData : data) {
            Row row = sheet.createRow(rowNum++);
            for (int i = 0; i < rowData.length; i++) {
                row.createCell(i).setCellValue(rowData[i]);
            }
        }

        // Save Excel file
        try (FileOutputStream fileOut = new FileOutputStream("Landed_Links.xlsx")) {
            workbook.write(fileOut);
        }
        workbook.close();
        System.out.println("Excel file created: Landed_Links.xlsx");
    }
}
