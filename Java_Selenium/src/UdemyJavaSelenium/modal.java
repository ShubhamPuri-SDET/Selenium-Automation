package UdemyJavaSelenium;

import org.codehaus.plexus.util.FileUtils;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.devtools.v130.filesystem.model.File;

public class modal {

    public static void main(String[] args) {
        abc();

    }

    public static void abc() {
        WebDriver driver = new ChromeDriver();
        driver.get("https://www.tutorialspoint.com/selenium/practice/modal-dialogs.php");
        driver.findElement(By.xpath("//button[contains (text(), 'Small Modal')]")).click();

        Alert alert = driver.switchTo().alert();
        System.out.println(alert.getText());
        alert.accept();
        driver.quit();

    }

}
