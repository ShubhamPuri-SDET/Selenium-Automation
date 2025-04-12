package Practice;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.github.bonigarcia.wdm.WebDriverManager;

public class Inven {

    public static void main(String[] args) {
// Set the path to the WebDriver executable (for example, ChromeDriver)        
        WebDriverManager.chromedriver().setup();
        WebDriver driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        driver.get("https://www.international.com/products/trucks/inventory?s=RH");

        // try {            // Wait for the accept button to be visible           
            WebElement acceptCookiesButton = wait.until(ExpectedConditions.elementToBeClickable(By.id("//a[normalize-space()='Confirm']")));
            // Update with the correct ID or selector            
            acceptCookiesButton.click();
// // Click the accept button        
//         } catch (TimeoutException e) {
//             System.out.println("Cookies dialog did not appear or was already closed.");
        // } // Loop through pagination (assume there are multiple pages)       
        boolean hasNextPage = true;
        while (hasNextPage) {
            wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//a[@class='result-title']")));
            // Adjust XPath for your car            // Find all the card links by their XPath or CSS selector           
            List<WebElement> cardLinks = driver.findElements(By.xpath("//a[@class='result-title']")); // Update this XPath            
            // Ensure you have 9 cards on the page            // Iterate through each card and simulate CTRL + CLICK         
            for (WebElement cardLink : cardLinks) {
                // Simulate the keypress for opening the link in a new tab               
                Actions action = new Actions(driver);
                action.keyDown(Keys.CONTROL)
                        // Hold down the CTRL key                        
                        .click(cardLink)         // Click the card link                      
                        .keyUp(Keys.CONTROL)// Release the CTRL key                       
                        .perform();              // Perform the action             
                WebElement nextButton = driver.findElement(By.xpath("//button[@title='Next Page']"));
                // Wait for the "Next" button to be clickable
                WebDriverWait wait1 = new WebDriverWait(driver, Duration.ofSeconds(10));
                wait1.until(ExpectedConditions.elementToBeClickable(nextButton));

                List<WebElement> nextPageLinks = null;
                try {
                    nextPageLinks = driver.findElements(By.xpath("//button[@title='Next Page']"));
                    WebDriverWait wait2 = new WebDriverWait(driver, Duration.ofSeconds(10));
                    nextButton = wait2.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[@title='Next Page']")));
                } catch (TimeoutException e) {
                    System.out.println("Next button is not visible!");
                    nextButton.click();
                    if (nextPageLinks.size() > 0) {
                        // Click the "Next" button to go to the next page                        
                        nextPageLinks.get(0).click();
                        // Wait for the page to load and the new cards to appear//            
                        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//a[@class='result-title']")));
                        // Wait for the cards on the next page                
                    } else {
                        // No more "Next" page, exit the loop            
                        hasNextPage = false;
                    }
                }
            }
        }
    }
}