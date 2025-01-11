package UdemyJavaSelenium.Webtable;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import Utility.basepackage;

public class Webtable3 extends basepackage {
    public static void main(String[] args) {
        driver = new ChromeDriver();
        driver.get(URL);

        driver.findElement(By.xpath("//div[@id='BlogArchive1']//ul//li[a[contains(text(), '2019')]]//span[contains(@class, 'zippy')]")).click();

        List<WebElement> toggles = driver.findElements(By.xpath(
                "//div[@id='BlogArchive1']//ul//li[a[contains(text(), '2019')]]//span[contains(@class, 'zippy')]"));

        if (toggles.size() >= 2) {
            WebElement secondToggle = toggles.get(1);

            // Click the second toggle
            secondToggle.click();
        } else {
            System.out.println("Second toggle element not found.");
        }
    }
}
