package POMProject.testcases;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import POMProject.googlepages.googlesearchpage;

public class NavigationwithTestNg {

	private static WebDriver driver;

	@BeforeTest
	public static void setupdriver() {
		driver = new ChromeDriver();
		driver.manage().window().maximize();
	}

	@Test
	public void testNavigation() {
		driver.get("https://www.google.com");
		googlesearchpage.searchbar(driver).sendKeys("Saucedemo");
		googlesearchpage.searchbutton(driver).sendKeys(Keys.RETURN);
	}

	// static void googlesearch() {
	// 	driver.get("https://www.google.com/");
	// 	driver.findElement(By.name("q")).sendKeys("Amazon");
	// 	// driver.findElement(By.xpath("(//div[@class='aFn4tc NNMgCf
	// 	// DZm15c'])[2]")).sendKeys(Keys.RETURN);
	// }

	@AfterTest
	static void closebrowser(WebDriver driver) {
		driver.quit();
	}

}