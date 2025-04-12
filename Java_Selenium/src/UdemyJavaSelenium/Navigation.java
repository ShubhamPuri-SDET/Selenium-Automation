package UdemyJavaSelenium;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class Navigation {
public static void main(String[] args) {
    WebDriver driver = new ChromeDriver();
    driver.manage().window().maximize();
    driver.get("https://automationpanda.com/2021/12/29/want-to-practice-test-automation-try-these-demo-sites/");

    String initialUrl = driver.getCurrentUrl();
    System.out.println("Initial URL: " + initialUrl);

    driver.findElement(By.xpath("//div//ul[@id='menu-primary']//li//a[contains(text(), 'Contact')]")).click();

    String contactUrl = driver.getCurrentUrl();
    System.out.println("Navigated to contact: " + contactUrl);

    driver.navigate().back();

    String backUrl = driver.getCurrentUrl();
    System.out.println("Navigated back: " + backUrl);

    if (initialUrl.equals(backUrl)) {
        System.out.println("Navigation successful");
    } else {
        System.out.println("Navigation failed");
    }

    driver.quit();
}
}