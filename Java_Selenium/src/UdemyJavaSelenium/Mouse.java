package UdemyJavaSelenium;

import java.net.MalformedURLException;

import java.net.URL;

import java.util.HashMap;

import org.openqa.selenium.By;

import org.openqa.selenium.MutableCapabilities;

import org.openqa.selenium.WebDriver;

import org.openqa.selenium.WebElement;

import org.openqa.selenium.interactions.Actions;

import org.openqa.selenium.remote.RemoteWebDriver;

import org.testng.annotations.AfterMethod;

import org.testng.annotations.BeforeTest;

import org.testng.annotations.Test;

public class Mouse {

    public static String username = "<username>";

    public static String accesskey = "<access key>";

    public static final String URL = "https://" + username + ":" + accesskey + "@hub-cloud.browserstack.com/wd/hub";

    WebDriver driver;

    String url = "https://www.browserstack.com/";

    Actions act;

    MutableCapabilities capabilities = new MutableCapabilities();

    HashMap<String, Object> bstackOptions = new HashMap<String, Object>();

    @BeforeTest

    public void setUp() throws MalformedURLException {

        capabilities.setCapability("browserName", "Chrome");

        bstackOptions.put("os", "Windows");

        bstackOptions.put("osVersion", "11");

        bstackOptions.put("browserVersion", "latest");

        bstackOptions.put("consoleLogs", "info");

        bstackOptions.put("seleniumVersion", "3.14.0");

        capabilities.setCapability("bstack:options", bstackOptions);

        driver = new RemoteWebDriver(new URL(URL), capabilities);

        driver.get(url);

        driver.manage().window().maximize();

        act = new Actions(driver);

    }

    @Test

    void doubleClick() {

        WebElement trialaction = driver.findElement(By.cssSelector("a[title='Free Trial']"));

        //Double click on element
        act.doubleClick(trialaction).perform();

    }

    @Test

    void rightClick() {

        WebElement trialaction = driver.findElement(By.cssSelector("a[title='Free Trial']"));

        //Right click on element
        act.contextClick(trialaction).perform();

    }

    @Test

    void hover() {

        WebElement products = driver.findElement(By.cssSelector("button#products-dd-toggle"));

        //Mouse hover on element
        act.moveToElement(products).perform();

    }

    @Test

    void clickAndsendKeys() {

        WebElement search = driver.findElement(By.xpath(
                "//button[@class='bstack-mm-search-menu doc-search-menu dropdown-toggle doc-search-cta doc-search-menu-icon doc-menu-toggle hide-sm hide-xs']"));

        //Click on element
        act.click(search).perform();

        WebElement searchBox = driver.findElement(By.cssSelector("input#doc-search-box-input"));

        //Send keys on element
        act.sendKeys(searchBox, "Selenium").perform();

    }

    @AfterMethod

    void tearDown() {

        driver.get(url);

    }

}
