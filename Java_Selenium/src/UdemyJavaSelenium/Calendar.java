package UdemyJavaSelenium;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;

public class Calendar {
    public static void main(String[] args) { 
        WebDriver driver = new ChromeDriver();
        driver.get("https://www.hyrtutorials.com/p/calendar-practice.html");
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        WebElement datePicker = driver.findElement(By.xpath("//input[@id = 'third_date_picker']"));
        datePicker.click();
        
        // WebElement yearDropdown = driver.findElement(By.className("ui-datepicker-year"));
        // yearDropdown.click();

        Select yearDropdown = new Select(driver.findElement(By.className("ui-datepicker-year")));
        yearDropdown.selectByValue("2018");

        Select monthDropdown = new Select(driver.findElement(By.className("ui-datepicker-month")));
        monthDropdown.selectByIndex(4);
        // WebElement monthOption = driver.findElement(By.xpath("//option[@value='4']")); // May (0-based index)
        // monthOption.click();
        String beforexpath =  "ED";
        String AfterXpath = "Fd";
        WebElement day = driver.findElement(By.xpath("//div[@id='ui-datepicker-div']//tr[5]//td[4]"));
        day.click();

        String selectedDate = datePicker.getAttribute("value");
        System.out.println("Selected date is: " + selectedDate);

    }
}