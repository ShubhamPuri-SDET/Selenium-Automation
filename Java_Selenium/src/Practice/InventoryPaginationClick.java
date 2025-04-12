package Practice;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import java.util.List;

public class InventoryPaginationClick {

    public static void main(String[] args) throws InterruptedException {
        WebDriver driver = new ChromeDriver();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        driver.manage().window().maximize();
        driver.get("https://www.international.com/products/trucks/inventory?s=RH");

        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[normalize-space()='Confirm']")));
        // Locate and click the 'Accept Cookies' button
        WebElement acceptButton = driver.findElement(By.xpath("//a[normalize-space()='Confirm']"));
        acceptButton.click();
        System.out.println("Cookies accepted.");

        boolean hasNextPage = true;

        while (hasNextPage) {
            wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//a[@class='result-title']")));

            List<WebElement> inventoryItems = driver.findElements(By.xpath("//a[@class='result-title']"));
            System.out.println("Items on this page: " + inventoryItems.size());

            for (int i = 0; i < inventoryItems.size(); i++) {

                inventoryItems = driver.findElements(By.xpath("//a[@class='result-title']"));
                WebElement item = inventoryItems.get(i);
                System.out.println("Clicking item: " + item.getText());

                Actions action = new Actions(driver);
                action.keyDown(Keys.CONTROL)
                        .click(item)
                        .keyUp(Keys.CONTROL)// 
                        .perform();

                // try {
                //     // Thread.sleep(2000);
                //     Thread.sleep(1000);
                // } catch (InterruptedException ex) {

                //     System.out.println("No more pages or Next button not found.");
                //     hasNextPage = false;

                // }

            }

            WebElement nextButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[@title = 'Next Page']")));

            // Scroll to the element and click on it
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true); arguments[0].click();", nextButton);

            
            Thread.sleep(2000);
            // Check if the "Next" button is still present
            List<WebElement> nextPageLinks = driver.findElements(By.xpath("//button[@title='Next Page']"));
            if (nextPageLinks.isEmpty()) {
                System.out.println("No more pages to navigate.");
                hasNextPage = false;
            } else {
                System.out.println("Navigating to the next page.");
            }
  
        }
    }
}
