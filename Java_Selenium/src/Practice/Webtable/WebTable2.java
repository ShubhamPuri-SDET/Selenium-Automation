package Practice.Webtable;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WindowType;
import org.openqa.selenium.chrome.ChromeDriver;

public class WebTable2 extends Basepage {
    public static void main(String[] args) {
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        driver.get(url);
        driver.findElement(By.xpath("//td[text()='Selenium']//preceding-sibling::td//input")).click();
        driver.close();
        driver.switchTo().newWindow(WindowType.TAB).get(url);
        driver.findElement(By.xpath("//td[text( ) ='Ola']//following-sibling::td[3]//a")).click();

        // WebElement seleniumCheckbox = driver
        // .findElement(By.xpath("//td[text() =
        // 'Selenium']//preceding-sibling::td//input"));
        // seleniumCheckbox.click();
        // driver.switchTo().newWindow(WindowType.TAB).get(url);
        // Set<String> windowHandles = driver.getWindowHandles();
        // WebElement olaLink = driver.findElement(By.xpath("//td[text() =
        // 'Ola']//following-sibling::td[3]//a"));
        // olaLink.click();

    }
}
