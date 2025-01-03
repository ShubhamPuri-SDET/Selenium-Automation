package POMProject.testcases;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import POMProject.googlepages.googlesearchpage;

public class NavigateToUrl {


    public static void main(String[] args) {
        Googlesearch();
    }

    static void Googlesearch() {
        WebDriver driver = new ChromeDriver();
        driver.get("https://www.google.com/");

        googlesearchpage.searchbar(driver).sendKeys("Amazon");
        googlesearchpage.searchbar(driver).sendKeys(Keys.RETURN);
        googlesearchpage.search(driver).click();
        // driver.findElement(By.name("btnk")).sendKeys(Keys.RETURN);
        // System.out.println(driver.getTitle());
        driver.close();

    }
}
