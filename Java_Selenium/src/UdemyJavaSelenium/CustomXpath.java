package UdemyJavaSelenium;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import Utility.basepackage;

public class CustomXpath extends basepackage {
    public static void main(String[] args) {

        driver = new ChromeDriver();
        driver.get(URL);
        // String textlink = driver.findElement(By.xpath("//a")).getText();
        List<WebElement> links = driver.findElements(By.tagName("a"));

        System.out.println(links.size());

        for (int i = 0; i < links.size(); i++) {
            String textLink = links.get(i).getText();
            String href = links.get(i).getAttribute("href");
            System.out.println("text of the link" + " - " + textLink);
            System.out.println("Text URLs" +  " - " + href);
        }
        driver.quit();
    }
}
