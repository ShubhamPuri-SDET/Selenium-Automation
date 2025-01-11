package POMProject.testcases;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import POMProject.googlepages.googlesearchpage;
import UdemyJavaSelenium.Capturescreenshot;

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
try {
    Capturescreenshot.capture(driver, "google_search_result");
} catch (Exception e) {
    System.out.println("Error capturing screenshot: " + e.getMessage());
}        // driver.findElement(By.name("btnk")).sendKeys(Keys.RETURN);
        // System.out.println(driver.getTitle());
        driver.close();

    }
}
