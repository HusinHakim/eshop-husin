package id.ac.ui.cs.advprog.eshop.functional;

import io.github.bonigarcia.seljup.SeleniumJupiter;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
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
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class EditProductFunctionalTest {

    @LocalServerPort
    private int serverPort;

    @Value("${app.baseUrl:http://localhost}")
    private String testBaseUrl;

    private String baseUrl;

    @BeforeAll
    void setUpTest(ChromeDriver driver) throws Exception {
        baseUrl = String.format("%s:%d/product/create", testBaseUrl, serverPort);

        driver.get(baseUrl);
        String productName = "Product 1";
        WebElement productNameInput = driver.findElement(By.id("nameInput"));
        productNameInput.sendKeys(productName);
        int productQuantity = 100;
        WebElement productQuantityInput = driver.findElement(By.id("quantityInput"));
        productQuantityInput.sendKeys(String.valueOf(productQuantity));

        WebElement createButton = driver.findElement(By.tagName("button"));
        createButton.click();

        baseUrl = String.format("%s:%d/product/list", testBaseUrl, serverPort);
    }

    @Test
    void pageTitle_isCorrect(ChromeDriver driver) throws Exception {
        driver.get(baseUrl);
        WebElement editButton = driver.findElement(By.partialLinkText("Edit"));
        editButton.click();
        String pageTitle = driver.getTitle();
        assertEquals("Edit Product", pageTitle);
    }

    @Test
    void message_editProduct_isCorrect(ChromeDriver driver) throws Exception {
        driver.get(baseUrl);
        WebElement editButton = driver.findElement(By.partialLinkText("Edit"));
        editButton.click();
        String message = driver.findElement(By.tagName("h3")).getText();
        assertEquals("Edit Product", message);
    }

    @Test
    void editProduct_isCorrect(ChromeDriver driver) throws Exception {
        driver.get(baseUrl);
        WebElement editButton = driver.findElement(By.partialLinkText("Edit"));
        editButton.click();
        String productNameEdit = "Product 1 EDIT";
        WebElement productNameEditInput = driver.findElement(By.id("nameInput"));
        productNameEditInput.clear();
        productNameEditInput.sendKeys(productNameEdit);
        int productQuantityEdit = 90000;
        WebElement productQuantityInputEdit = driver.findElement(By.id("quantityInput"));
        productQuantityInputEdit.clear();
        productQuantityInputEdit.sendKeys(String.valueOf(productQuantityEdit));

        WebElement saveEditButton = driver.findElement(By.tagName("button"));
        saveEditButton.click();

        String currentUrl = driver.getCurrentUrl();
        assertEquals(String.format("%s:%d/product/list", testBaseUrl, serverPort), currentUrl);

        String pageSource = driver.getPageSource();
        boolean isContainCreatedProductName = pageSource.contains(productNameEdit);
        assertTrue(isContainCreatedProductName);
        boolean isContainCreatedProductQuantity = pageSource.contains(String.valueOf(productQuantityEdit));
        assertTrue(isContainCreatedProductQuantity);
    }

}
