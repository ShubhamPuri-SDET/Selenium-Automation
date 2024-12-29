package Practice.Webtable;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class Basepage {

    public static WebDriver driver;
    public static String url = "https://seleniumpractise.blogspot.com/2021/08/webtable-in-html.html";

    public static void driversd() {
        driver = new ChromeDriver();
        // String url =
        // "https://seleniumpractise.blogspot.com/2021/08/webtable-in-html.html";

    }

    // public static WebDriver getDriver(){
    // return driver;
    // }
}