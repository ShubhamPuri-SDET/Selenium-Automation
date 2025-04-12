package AutomationAssignments;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.Duration;
import java.util.List;
import java.util.Properties;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class Assignment6 {

    public static void main(String[] args) throws IOException {
        // Load properties file
        Properties prop = new Properties();
        FileInputStream input = new FileInputStream("AssignmentProp.properties");
        prop.load(input);
        String url = prop.getProperty("url2");

        // Set up Chrome driver
        WebDriver driver = new ChromeDriver();
        driver.get(url);

        // Extract header links
        List<WebElement> headerLinks = driver.findElements(By.cssSelector("header a"));
        // Extract footer links
        List<WebElement> footerLinks = driver.findElements(By.cssSelector("footer a"));

        // Create Excel workbook and sheets
        Workbook workbook = new XSSFWorkbook();
        Sheet headerSheet = workbook.createSheet("Header Links");
        Sheet footerSheet = workbook.createSheet("Footer Links");

        // Write header information to Excel
        writeLinkInfoToExcel(headerLinks, driver, headerSheet);

        // Write footer information to Excel
        writeLinkInfoToExcel(footerLinks, driver, footerSheet);

        // Save the Excel file
        FileOutputStream fileOut = new FileOutputStream("LinkInfo.xlsx");
        workbook.write(fileOut);
        fileOut.close();
        workbook.close();

        // Close the browser
        driver.quit();
    }

    private static void writeLinkInfoToExcel(List<WebElement> links, WebDriver driver, Sheet sheet) {
        int rowNum = 0;
        for (WebElement link : links) {
            String linkText = link.getText();
            String linkUrl = link.getAttribute("href");
            link.click();
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            wait.until(ExpectedConditions.urlToBe(linkUrl));
            String linkTitle = driver.getTitle();
            String redirectedUrl = driver.getCurrentUrl();
            String status = redirectedUrl.contains("404") ? "Fail" : "Pass";

            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(linkText);
            row.createCell(1).setCellValue(linkUrl);
            row.createCell(2).setCellValue(linkTitle);
            row.createCell(3).setCellValue(status);

            driver.navigate().back();
        }
    }
}
