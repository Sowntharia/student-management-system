package net.javaguides.sms.selenium;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.time.Duration;

import org.junit.jupiter.api.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.*;
import org.springframework.test.context.ActiveProfiles;

import io.github.bonigarcia.wdm.WebDriverManager;
@ActiveProfiles("test")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class StudentSeleniumUITest {

    private WebDriver driver;
    private WebDriverWait wait;

    @BeforeEach
    void setUp() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        driver.get("http://localhost:8080/students");
    }

    @AfterEach
    void tearDown() {
        if (driver != null) driver.quit();
    }

    @Test
    @Order(1)
    void testAddStudent() {
        driver.findElement(By.linkText("Add Student")).click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("firstName"))).sendKeys("Test");
        driver.findElement(By.id("lastName")).sendKeys("User");
        driver.findElement(By.id("email")).sendKeys("testuser@example.com");

        driver.findElement(By.cssSelector("button[type='submit']")).click();

        String body = driver.findElement(By.tagName("body")).getText();
        assertTrue(body.contains("Test"));
        assertTrue(body.contains("User"));
        assertTrue(body.contains("testuser@example.com"));
    }

    @Test
    @Order(2)
    void testUpdateStudent() {
        driver.get("http://localhost:8080/students");

        WebElement row = wait.until(ExpectedConditions.presenceOfElementLocated(
            By.xpath("//tr[td[contains(text(),'testuser@example.com')]]")));

        WebElement editBtn = row.findElement(By.cssSelector("a.edit-button"));
        editBtn.click();

        WebElement lastNameField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("lastName")));
        lastNameField.clear();
        lastNameField.sendKeys("Updated");

        driver.findElement(By.cssSelector("button[type='submit']")).click();

        String page = driver.getPageSource();
        assertTrue(page.contains("Updated"));
    }

    
    @Test
    @Order(3)
    void testDeleteStudent() {
        driver.get("http://localhost:8080/students");

        WebElement row = wait.until(ExpectedConditions.presenceOfElementLocated(
            By.xpath("//tr[td[contains(text(),'testuser@example.com')]]")));

        WebElement form = row.findElement(By.cssSelector("form.delete-form"));
        form.submit();

        wait.until(ExpectedConditions.stalenessOf(row)); // Wait until row disappears

        driver.navigate().refresh(); // Reload to confirm deletion
        String page = driver.getPageSource();

        assertFalse(page.contains("testuser@example.com"));
    }
}
