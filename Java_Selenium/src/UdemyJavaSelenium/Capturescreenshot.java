package UdemyJavaSelenium;

import java.io.File;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class Capturescreenshot {

    public static WebDriver driver;

    public static void main(String[] args) {
        WebDriver driver = new ChromeDriver();
        capture(driver);
    }

    public static void capture(WebDriver driver) {
        driver.get("https://www.google.com/");
        try {
            // Capture screenshot
            File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            FileUtils.copyFile(screenshot, new File("C:\\Automation\\CapturedScreenshot\\screenshot.png"));
            System.out.println("Screenshot captured.");
        } catch (Exception e) {
            System.out.println("Failed to capture screenshot: " + e.getMessage());
        }
        driver.quit();

    }
}
