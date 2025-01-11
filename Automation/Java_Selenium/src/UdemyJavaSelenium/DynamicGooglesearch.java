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
                driver.findElement(By.name("q")).sendKeys("rock");
                List<WebElement> list = driver
                                .findElements(By.xpath(
                                                "//ul[@role ='listbox']//li//descendant::div//div[@class = 'pcTkSc']"));
                System.out.println("Total number of suggestion " + list.size());

                for (int i = 0; i < list.size(); i++) {
                        // String text = list.get(i).getText();
                        // if (text.equals("Java")) {
                        // list.get(i).click();
                        // }

                        if (list.get(i).getText().contentEquals("JavaScript")) {
                                list.get(i).click();
                                break;
                        }
                }
        }
}
