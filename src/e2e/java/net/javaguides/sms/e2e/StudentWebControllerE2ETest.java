package net.javaguides.sms.e2e;

import static org.assertj.core.api.Assertions.assertThat;

import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Duration;
import java.util.UUID;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.*;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.client.RestTemplate;

import io.github.bonigarcia.wdm.WebDriverManager;
import net.javaguides.sms.BaseTestContainer;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class StudentWebControllerE2ETest extends BaseTestContainer {

    @LocalServerPort
    private int port;

    private static WebDriver driver;
    private WebDriverWait wait;
    private String baseUrl;
    @BeforeAll
    static void setupClass() throws Exception {
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();

        options.addArguments("--headless");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--remote-allow-origins=*");

        Path tempUserDataDir = Files.createTempDirectory("chrome-profile-" + UUID.randomUUID());
        options.addArguments("--user-data-dir=" + tempUserDataDir.toAbsolutePath());

        driver = new ChromeDriver(options);
        driver.manage().window().setSize(new Dimension(1280, 1024));
    }



    @BeforeEach
    void setupTest() {
        baseUrl = "http://localhost:" + port;
        wait = new WebDriverWait(driver, Duration.ofSeconds(20));
    }

    @AfterAll
    static void teardown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Test
    @Order(1)
    void shouldCreateStudent() {
        driver.get(baseUrl + "/students/new");

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("firstName"))).sendKeys("E2E");
        driver.findElement(By.id("lastName")).sendKeys("Test");
        driver.findElement(By.id("email")).sendKeys("e2e@test.com");

        driver.findElement(By.cssSelector("button[type='submit']")).click();

        WebElement table = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("studentTable")));
        assertThat(table.getText()).contains("E2E", "Test", "e2e@test.com");
    }

    @Test
    @Order(2)
    void shouldUpdateStudent() throws JSONException {
        String email = postStudent("Update", "User", "update@test.com");

        driver.get(baseUrl + "/students");

        WebElement row = wait.until(ExpectedConditions.presenceOfElementLocated(
            By.xpath("//tr[td[contains(text(),'" + email + "')]]")));

        WebElement editButton = row.findElement(By.cssSelector("a.edit-button"));
        editButton.click();

        WebElement lastNameInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("lastName")));
        lastNameInput.clear();
        lastNameInput.sendKeys("Updated");

        driver.findElement(By.cssSelector("button[type='submit']")).click();

        WebElement table = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("studentTable")));
        assertThat(table.getText()).contains("Updated");
    }

    @Test
    @Order(3)
    void shouldDeleteStudent() throws JSONException {
        String email = postStudent("Delete", "User", "delete@test.com");

        driver.get(baseUrl + "/students");

        WebElement deleteForm = wait.until(ExpectedConditions.presenceOfElementLocated(
            By.xpath("//tr[td[contains(text(),'" + email + "')]]//form[contains(@class,'delete-form')]")));

        deleteForm.submit();

        wait.until(ExpectedConditions.stalenessOf(deleteForm));
        driver.navigate().refresh();

        assertThat(driver.getPageSource()).doesNotContain(email);
    }

    private String postStudent(String firstName, String lastName, String email) throws JSONException {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        JSONObject body = new JSONObject();
        body.put("firstName", firstName);
        body.put("lastName", lastName);
        body.put("email", email);

        HttpEntity<String> entity = new HttpEntity<>(body.toString(), headers);
        ResponseEntity<String> response = restTemplate.postForEntity(baseUrl + "/api/students", entity, String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

        return email;
    }

}
