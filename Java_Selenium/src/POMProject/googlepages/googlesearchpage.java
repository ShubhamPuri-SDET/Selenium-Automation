package POMProject.googlepages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class googlesearchpage {
    
    public static WebDriver driver = null;
    public static WebElement element = null;

    public static WebElement searchbar(WebDriver driver){
        element = driver.findElement(By.name("q"));
        return element;
        
    }

    public static WebElement searchbutton(WebDriver driver){
        element = driver.findElement(By.name("btnk"));
        return element;
    }
}
