package UdemyJavaSelenium;

import java.awt.Window;
import java.sql.DriverPropertyInfo;
import java.time.Duration;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Windowhandel {

    public static void main(String[] args) {
        WebDriver driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        driver.manage().window().maximize();
        driver.get("https://www.lucentis.com/");

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.cssSelector("div[class='ot-sdk-container'] div[class='ot-sdk-row']"))));

        driver.findElement(By.xpath("//button[@id='onetrust-accept-btn-handler']")).click();

        // driver.findElement(By.xpath("//span[@class='cmp-masthead__utility-nav-item-text cmp-masthead__utility-nav-item-text--desktop'][normalize-space()='Prescribing information']")).click();
        // System.out.println(driver.getCurrentUrl());
        // driver.navigate().back();
        // driver.close();
        driver.findElement(By.linkText("Privacy Policy"));
        String parentwindow = driver.getWindowHandle();

        Set<String> child = driver.getWindowHandles();

        for (String wind : child) {

            if (!parentwindow.equals(wind)) {
                driver.switchTo().window(wind);
            }

        }
        // driver.switchTo().window(parentwindow);
        // driver.quit();

    }

}
