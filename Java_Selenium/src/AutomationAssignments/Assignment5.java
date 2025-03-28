package AutomationAssignments;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class Assignment5 {

    public static void main(String[] args) {
        // Load properties
        Properties properties = new Properties();
        try (FileInputStream fis = new FileInputStream("AssignmentProp.properties")) {
            properties.load(fis);
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        String keyword = properties.getProperty("keyword");
        WebDriver driver = new ChromeDriver();

        try {

            driver.get("https://www.saucedemo.com/v1/");

            driver.findElement(By.id("user-name")).sendKeys("standard_user");
            driver.findElement(By.id("password")).sendKeys("secret_sauce");
            driver.findElement(By.id("login-button")).click();

            driver.findElement(By.id("search-input")).sendKeys(keyword);
            driver.findElement(By.id("search-button")).click();

            WebElement product = driver.findElement(By.className("inventory_item"));
            if (product.isDisplayed()) {
                System.out.println("Successfully Searched \"" + keyword + "\"");
            } else {
                System.out.println("No product found");
            }
        } catch (Exception e) {
            System.out.println("No product found");
        } finally {
            // Close the browser
            // driver.quit();
        }
    }
}
