package id.ac.ui.cs.advprog.eshop.functional;
import io.github.bonigarcia.seljup.SeleniumJupiter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import java.time.Duration;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
@SpringBootTest(webEnvironment = RANDOM_PORT)
@ExtendWith(SeleniumJupiter.class)
public class CreateProductFunctionalTest {
    @LocalServerPort
    private int serverPort;
    @Value("${app.baseUrl:http://localhost}")
    private String testBaseUrl;
    private String baseUrl;
    @BeforeEach
    void setupTest() {
        baseUrl = String.format("%s:%d", testBaseUrl, serverPort);
    }
    @Test
    void testCreateProduct_Success(ChromeDriver driver) {
        driver.get(baseUrl + "/product/create");
        WebElement nameInput = driver.findElement(By.name("productName"));
        WebElement quantityInput = driver.findElement(By.name("productQuantity"));
        WebElement submitButton = driver.findElement(By.cssSelector("button[type='submit']"));
        nameInput.sendKeys("Valid Product");
        quantityInput.sendKeys("10");
        submitButton.click();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.urlContains("/product/list"));
        wait.until(ExpectedConditions.textToBePresentInElementLocated(By.tagName("body"), "Valid Product"));
        assertTrue(driver.getCurrentUrl().contains("/product/list"));
        assertTrue(driver.getPageSource().contains("Valid Product"));
    }
    @Test
    void testCreateProduct_Failure(ChromeDriver driver) {
        driver.get(baseUrl + "/product/create");
        WebElement nameInput = driver.findElement(By.name("productName"));
        WebElement quantityInput = driver.findElement(By.name("productQuantity"));
        WebElement submitButton = driver.findElement(By.cssSelector("button[type='submit']"));
        nameInput.sendKeys("");
        quantityInput.sendKeys("-1");
        submitButton.click();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement errorMessage = wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.className("error"))
        );
        assertTrue(errorMessage.getText().contains("Product name cannot be empty"));
    }
}