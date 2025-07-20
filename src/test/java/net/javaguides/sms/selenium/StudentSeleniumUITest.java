package net.javaguides.sms.selenium;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.Duration;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
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
        wait = new WebDriverWait(driver, Duration.ofSeconds(5));
		driver.get("http://localhost:8080/students");
	}
	
	@AfterEach
	void tearDown() {
		if (driver != null) {
			driver.quit();
		}
	}
	
	
	@Test
	@Order(1)
	void testAddStudent() {
		driver.findElement(By.linkText("Add Student")).click();
		
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("firstName")));
		
		driver.findElement(By.name("firstName")).sendKeys("Test");
		driver.findElement(By.name("lastName")).sendKeys("User");
		driver.findElement(By.name("email")).sendKeys("testuser@example.com");
		
		//submit button
		driver.findElement(By.cssSelector("button[type='submit']")).click();
		
        wait.until(ExpectedConditions.textToBePresentInElementLocated(By.tagName("body"), "testuser@example.com"));
		
		String page = driver.getPageSource();
		assertTrue(page.contains("Test"));
		assertTrue(page.contains("User"));
		assertTrue(page.contains("testuser@example.com"));
		
	}
	
	@Test
	@Order(2)
	void testUpdateStudent() {
		driver.get("http://localhost:8080/students");

		WebElement row = wait.until(ExpectedConditions.presenceOfElementLocated(
				By.xpath("//tr[td[contains(text(),'Test')]]")));

		WebElement editButton = row.findElement(By.cssSelector("a.edit-button"));
		editButton.click();

		wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("lastName")));
		WebElement lastName = driver.findElement(By.name("lastName"));
		lastName.clear();
		lastName.sendKeys("Updated");

		driver.findElement(By.cssSelector("button[type='submit']")).click();

		wait.until(ExpectedConditions.textToBePresentInElementLocated(By.tagName("body"), "Updated"));

		String page = driver.getPageSource();
		assertTrue(page.contains("Updated"));
	}
	
	@Test
	@Order(3)
	void testDeleteStudent() {
		driver.findElement(By.linkText("Add Student")).click();

		wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("firstName")));
		driver.findElement(By.name("firstName")).sendKeys("Delete");
		driver.findElement(By.name("lastName")).sendKeys("Me");
		driver.findElement(By.name("email")).sendKeys("delete@example.com");
		driver.findElement(By.cssSelector("button[type='submit']")).click();

		wait.until(ExpectedConditions.textToBePresentInElementLocated(By.tagName("body"), "delete@example.com"));

		driver.get("http://localhost:8080/students");

		WebElement row = wait.until(ExpectedConditions.presenceOfElementLocated(
			By.xpath("//tr[td[contains(text(),'delete@example.com')]]")));

		WebElement deleteButton = row.findElement(By.cssSelector("form.delete-form button[type='submit']"));
		deleteButton.click();

		try {
			Alert alert = wait.until(ExpectedConditions.alertIsPresent());
			alert.accept();
		} catch (TimeoutException e) {
			System.out.println("No alert appeared.");
		}

		driver.navigate().refresh();

		String page = driver.getPageSource();
		assertFalse(page.contains("delete@example.com"), "Deleted student still present in table!");
	}

	
	
}

