package Main;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class PrintLinks {

    public static void main(String[] args) {

        WebDriver driver = new ChromeDriver();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        String url = "https://m.e.genentech.com/nl/jsp/m.jsp?c=%40AVgtZu9Z9RpqKZTcvGdRMRV%2FwszAn5ClQcrBl5QriZk%3D";
        driver.get(url);

        // Wait for the page to load
        wait.until(ExpectedConditions.presenceOfElementLocated(By.tagName("a")));

        // Get all links and store their href attributes
        List<WebElement> links = driver.findElements(By.tagName("a"));
        List<String> hrefs = new ArrayList<>();

        for (WebElement link : links) {
            String linkURL = link.getAttribute("href");
            if (linkURL != null && !linkURL.isEmpty()) {
                hrefs.add(linkURL);
            }
        }

        // Visit each link separately
        for (String linkURL : hrefs) {
            try {
                System.out.println("Navigating to: " + linkURL);
                driver.get(linkURL);

                // Wait for page load
                wait.until(ExpectedConditions.presenceOfElementLocated(By.tagName("body")));

                // Print the landing page URL
                System.out.println("Landed on: " + driver.getCurrentUrl());

                // Navigate back
                driver.navigate().back();
                wait.until(ExpectedConditions.presenceOfElementLocated(By.tagName("a")));
            } catch (Exception e) {
                System.out.println("Error navigating to link: " + linkURL + " | " + e.getMessage());
            }
        }

        driver.quit();
    }
}
