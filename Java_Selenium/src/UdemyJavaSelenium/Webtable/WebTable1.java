package UdemyJavaSelenium.Webtable;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class WebTable1 {
    public static void main(String[] args) {
        
        WebDriver driver = new ChromeDriver();
driver.get("https://seleniumpractise.blogspot.com/2021/08/webtable-in-html.html");
List<WebElement> headers = driver.findElements(By.xpath("//table[@id = 'customers']//th"));
System.out.println("Number of headers: " + headers.size());
for (WebElement header : headers) {
    String headervalue = header.getText();
    System.out.println("Header: " + headervalue);
    if(headervalue.equalsIgnoreCase("Country")){
        List<WebElement> rows = driver.findElements(By.xpath("//table[@id = 'customers']//tr"));
        for (int i = 1; i < rows.size(); i++) {
            List<WebElement> columns = rows.get(i).findElements(By.tagName("td"));
            System.out.println("Country: " + columns.get(headers.indexOf(header)).getText());
        }
    }
}
   //driver.close();
    }
    
}

// WebDriver driver = new ChromeDriver();
//         driver.get("https://seleniumpractise.blogspot.com/2021/08/webtable-in-html.html");
//         // List<WebElement> name = driver.findElements(By.xpath("//table[@id = 'customers']//td"));
//         List<WebElement> name = driver.findElements(By.xpath("//table[@id = 'customers']//th"));
//         System.out.println(name.size());
//         for (WebElement element : name) {
//             String headervalue = element.getText();
//         System.out.println(headervalue);
//         if(headervalue.contains("country")){
//            driver.findElement(By.xpath("//table[@id = 'customers']//td)")).getText();
           