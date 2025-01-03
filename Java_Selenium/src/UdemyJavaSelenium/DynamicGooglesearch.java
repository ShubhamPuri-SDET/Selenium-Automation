package UdemyJavaSelenium;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class DynamicGooglesearch {
        public static void main(String[] args) {
        WebDriver driver = new ChromeDriver();
        driver.get("https://www.google.co.in/");
        driver.findElement(By.name("q")).sendKeys("testing");
        List<WebElement> list = driver
                .findElements(By.xpath("//ul[@role ='listbox']//li//descendant::div[@class = 'iQxPRb']"));
        System.out.println("Total number of suggestion " + list.size());

    }
}