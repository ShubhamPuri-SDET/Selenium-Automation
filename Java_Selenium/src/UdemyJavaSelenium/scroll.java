package UdemyJavaSelenium;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;

public class scroll {

    public static void main(String[] args) {
        WebDriver driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        driver.manage().window().maximize();
        // driver.get("https://www.lucentis.com/");

        driver.get("https://www.geeksforgeeks.org/selenium-scrolling-a-web-page/");

        // WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        // wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.cssSelector("div[class='ot-sdk-container'] div[class='ot-sdk-row']"))));
        // driver.findElement(By.xpath("//button[@id='onetrust-accept-btn-handler']")).click();
        WebElement pb = driver.findElement(By.xpath("//span[normalize-space()='Click here.']"));

        Actions ac = new Actions(driver);

        ac.scrollToElement(pb).build().perform();
        pb.click();


        // System.out.println(driver.getCurrentUrl());
        // driver.navigate().back();
        // driver.close();
    }

}
