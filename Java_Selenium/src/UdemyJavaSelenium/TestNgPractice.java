package UdemyJavaSelenium;

import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class TestNgPractice {

//    public static void main(String[] args) {
//
//    }

    @BeforeClass
    public void beforeClass() {
        System.out.println("This is before class method");
    }

    @AfterClass
    public void afterClass() {
        System.out.println("This is after class method");
    }

    @BeforeMethod
    public void beforeMethod() {
        System.out.println("This is before method");
    }

    @AfterMethod
    public void afterMethod() {
        System.out.println("This is after method");
    }

    @Test
    public void test1() {
        System.out.println("This is test1");
    }

    @BeforeTest
    public void beforeTest() {
        System.out.println("This is before test");
    }

    @AfterTest
    public void afterTest() {
        System.out.println("This is after test");
    }

    @BeforeSuite
    public void beforeSuite() {
        System.out.println("This is before suite");
    }

    @AfterSuite
    public void afterSuite() {
        System.out.println("This is after suite");
    }

}
