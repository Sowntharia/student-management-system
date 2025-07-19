package net.javaguides.sms.selenium;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import io.github.bonigarcia.wdm.WebDriverManager;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class StudentSeleniumUITest {

	private WebDriver driver;
	
	@BeforeEach
	void setUp() {
		WebDriverManager.chromedriver().setup();
		driver = new ChromeDriver();
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
		
		driver.findElement(By.name("firstName")).sendKeys("Test");
		driver.findElement(By.name("lastName")).sendKeys("User");
		driver.findElement(By.name("email")).sendKeys("testuser@example.com");
		
		//submit button
		driver.findElement(By.cssSelector("buton[type='submit']")).click();
		
		String page = driver.getPageSource();
		assertTrue(page.contains("Test"));
		assertTrue(page.contains("User"));
		assertTrue(page.contains("testuser@example.com"));
		
	}
	
	@Test
	@Order(2)
	void testUpdateStudent() {
	WebElement editButton = driver.findElement(By.xpath("//td[contains(text(),'Test')]/following-sibling::td/a[text()='Edit']"));
	editButton.click();
	
	WebElement lastName = driver.findElement(By.name("lastName"));
	lastName.clear();
	lastName.sendKeys("Updated");
	
	driver.findElement(By.cssSelector("button[type='submit']")).click();
	
	String page = driver.getPageSource();
	assertTrue(page.contains("Updated"));
	
	}
	
	@Test
	@Order(3)
	void testDeleteStudent() {
		
		WebElement deleteButton = driver.findElement(By.xpath("//td[contains(text(),'Test')]/following-sibling::td/a[text()='Edit']"));
		deleteButton.click();
		
		String page = driver.getPageSource();
		assertFalse(page.contains("testuser@example.com"));
	}
	
	
}

