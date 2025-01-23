package UdemyJavaSelenium;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class PageViewIncrease {

    public static void main(String[] args) {
        int count = 50;
        String URL = "https://blogs.perficient.com/2025/01/11/debugging-error-handling-vba-excel-macros/";

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless");
        WebDriver driver = new ChromeDriver(options);

        for (int i = 0; i < count; i++) {
            driver.get(URL);
            try {
                Thread.sleep(1000); // wait for 1 second
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            System.out.println("Page opened " + (i + 1) + " times");
        }
        driver.quit();
    }
}
