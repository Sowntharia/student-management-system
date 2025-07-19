package net.javaguides.sms.e2e;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.time.Duration;
import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.github.bonigarcia.wdm.WebDriverManager;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class StudentE2ETest {
	
	
	private static WebDriver driver;
	private static WebDriverWait wait;
	private static final String BASE_URL = "http://localhost:8080";
	
	@BeforeAll
	public static void setup() {
		WebDriverManager.chromedriver().setup();
		ChromeOptions options = new ChromeOptions();
		driver = new ChromeDriver(options);
		driver.manage().window().maximize();
		wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		
	}
	
	@AfterAll
	public static void tearDown() {
		if (driver != null) {
			driver.quit();
		}
	}

	
	@Test
	@Order(1)
	void shouldCreateNewStudent() {
		driver.get(BASE_URL + "/students/new");
		
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("firstName"))).sendKeys("E2E");
		driver.findElement(By.id("lastName")).sendKeys("Test");
		driver.findElement(By.id("email")).sendKeys("e2e@test.com");
		
		driver.findElement(By.cssSelector("button[type='submit']")).click();
		
		WebElement table = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("studentTable")));
		assertThat(table.getText()).contains("E2E", "Test", "e2e@test.com");
		
	}
	
	@Test
	@Order(2)
	void shouldEditStudent() {
		driver.get(BASE_URL + "/students");
		
		List<WebElement> editButtons = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy
				(By.cssSelector("a.btn.btn-primary.edit-button")));
		assertFalse(editButtons.isEmpty(), "Edit button not found");
		
		editButtons.get(0).click();
		
		WebElement firstName = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("firstName")));
		firstName.clear();
		firstName.sendKeys("Updated");
		
		WebElement lastName = driver.findElement(By.id("lastName"));
		lastName.clear();
		lastName.sendKeys("Student");
		
		WebElement email = driver.findElement(By.id("email"));
		email.clear();
		email.sendKeys("updated@test.com");
		
		driver.findElement(By.cssSelector("button[type='submit']")).click();
		
		WebElement table = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("studentTable")));
		assertThat(table.getText()).contains("Updated", "updated@test.com");
		
	}
	
	@Test
	@Order(3)
	void shouldDeleteStudent() {
		driver.get(BASE_URL + "/students");
		
		List<WebElement> deleteForms = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.cssSelector("form.delete-form")));
		assertFalse(deleteForms.isEmpty(), "Delete form not found");
		
		deleteForms.get(0).submit();
		
		WebElement table = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("studentTable")));
		assertThat(table.getText()).doesNotContain("Updated", "updated@test.com");
	}
	
}
