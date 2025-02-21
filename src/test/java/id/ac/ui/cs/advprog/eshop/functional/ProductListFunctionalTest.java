package id.ac.ui.cs.advprog.eshop.functional;

import io.github.bonigarcia.seljup.SeleniumJupiter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.annotation.DirtiesContext;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@DirtiesContext
@SpringBootTest(webEnvironment = RANDOM_PORT)
@ExtendWith(SeleniumJupiter.class)
public class ProductListFunctionalTest {

    @LocalServerPort
    private int serverPort;

    @Value("${app.baseUrl:http://localhost}")
    private String testBaseUrl;

    private String baseUrl;

    @BeforeEach
    void setUpTest() {
        baseUrl = String.format("%s:%d/product/list", testBaseUrl, serverPort);
    }

    @Test
    void pageTitle_isCorrect(ChromeDriver driver) throws Exception {
        driver.get(baseUrl);
        String pageTitle = driver.getTitle();
        assertEquals("Product List", pageTitle);
    }

    @Test
    void message_productList_isCorrect(ChromeDriver driver) throws Exception {
        driver.get(baseUrl);
        String message = driver.findElement(By.tagName("h2")).getText();
        assertEquals("Product' List", message);
    }

    @Test
    void deleteProduct_isCorrect(ChromeDriver driver) throws Exception {
        driver.get(String.format("%s:%d/product/create", testBaseUrl, serverPort));
        String productName = "Product 1 DELETE";
        WebElement productNameInput = driver.findElement(By.id("nameInput"));
        productNameInput.sendKeys(productName);
        int productQuantity = 100;
        WebElement productQuantityInput = driver.findElement(By.id("quantityInput"));
        productQuantityInput.sendKeys(String.valueOf(productQuantity));

        WebElement createButton = driver.findElement(By.tagName("button"));
        createButton.click();

        String currentUrl = driver.getCurrentUrl();
        assertEquals(String.format("%s:%d/product/list", testBaseUrl, serverPort), currentUrl);

        String pageSource = driver.getPageSource();
        boolean isContainCreatedProductName = pageSource.contains(productName);
        assertTrue(isContainCreatedProductName);
        boolean isContainCreatedProductQuantity = pageSource.contains(String.valueOf(productQuantity));
        assertTrue(isContainCreatedProductQuantity);

        WebElement deleteButton = driver.findElement(By.partialLinkText("Delete"));
        deleteButton.click();

        pageSource = driver.getPageSource();
        isContainCreatedProductName = pageSource.contains(productName);
        assertFalse(isContainCreatedProductName);
        isContainCreatedProductQuantity = pageSource.contains(String.valueOf(productQuantity));
        assertFalse(isContainCreatedProductQuantity);
    }

}
