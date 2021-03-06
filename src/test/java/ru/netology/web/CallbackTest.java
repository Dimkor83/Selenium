package ru.netology.web;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import io.github.bonigarcia.wdm.WebDriverManager;

import static org.junit.jupiter.api.Assertions.assertEquals;

class OrderSeleniumTest {

    private WebDriver driver;

    @BeforeAll
    public static void setUpAll() {
        WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
    public void setUp() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--no-sandbox");
        options.addArguments("--headless");
        driver = new ChromeDriver(options);
        driver.get("http://localhost:9999/");
    }

    @AfterEach
    void tearDown() {
        driver.quit();
        driver = null;
    }

    @Test
    void testOrderSuccess() {
        driver.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("Иван Иванов");
        driver.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("+79012345678");
        driver.findElement(By.cssSelector("[data-test-id=agreement]")).click();
        driver.findElement(By.tagName("button")).click();
        String actualText = driver.findElement(By.cssSelector("[data-test-id=order-success]")).getText().trim();
        String expected = "Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время.";
        assertEquals(expected, actualText);
    }

    @Test
    void testAllNull() {
        driver.findElement(By.tagName("button")).click();
        String actualText = driver.findElement(By.cssSelector("[data-test-id=name].input_invalid .input__sub")).getText().trim();
        String expected = "Поле обязательно для заполнения";
        assertEquals(expected, actualText);
    }

    @Test
    void testNameInvalid() {
        driver.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("Иван4 Иванов");
        driver.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("+79012345678");
        driver.findElement(By.cssSelector("[data-test-id=agreement]")).click();
        driver.findElement(By.tagName("button")).click();
        String actualText = driver.findElement(By.cssSelector("[data-test-id=name].input_invalid .input__sub")).getText().trim();
        String expected = "Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.";
        assertEquals(expected, actualText);
    }

    @Test
    void testNameNull() {
        driver.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("+79012345678");
        driver.findElement(By.cssSelector("[data-test-id=agreement]")).click();
        driver.findElement(By.tagName("button")).click();
        String actualText = driver.findElement(By.cssSelector("[data-test-id=name].input_invalid .input__sub")).getText().trim();
        String expected = "Поле обязательно для заполнения";
        assertEquals(expected, actualText);
    }

    @Test
    void testPhoneInvalid() {
        driver.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("Иван Иванов");
        driver.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("+790123456789");
        driver.findElement(By.cssSelector("[data-test-id=agreement]")).click();
        driver.findElement(By.tagName("button")).click();
        String actualText = driver.findElement(By.cssSelector("[data-test-id=phone].input_invalid .input__sub")).getText().trim();
        String expected = "Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.";
        assertEquals(expected, actualText);
    }

    @Test
    void testPhoneNull() {
        driver.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("Иван Иванов");
        driver.findElement(By.cssSelector("[data-test-id=agreement]")).click();
        driver.findElement(By.tagName("button")).click();
        String actualText = driver.findElement(By.cssSelector("[data-test-id=phone].input_invalid .input__sub")).getText().trim();
        String expected = "Поле обязательно для заполнения";
        assertEquals(expected, actualText);
    }

    @Test
    void testCheckboxInvalid() {
        driver.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("Иван Иванов");
        driver.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("+79012345678");
        driver.findElement(By.tagName("button")).click();
        boolean actualText = driver.findElement(By.cssSelector("[data-test-id=agreement].input_invalid")).isDisplayed();
        boolean expected = true;
        assertEquals(expected, actualText);
    }

    @Test
    void testAllInvalidCheckboxChecked() {
        driver.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("Иван1 Иванов");
        driver.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("+790123456789");
        driver.findElement(By.cssSelector("[data-test-id=agreement]")).click();
        driver.findElement(By.tagName("button")).click();
        String actualText = driver.findElement(By.cssSelector("[data-test-id=name].input_invalid .input__sub")).getText().trim();
        String expected = "Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.";
        assertEquals(expected, actualText);
    }

    @Test
    void testAllInvalidCheckboxUnchecked() {
        driver.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("Иван Иванов1");
        driver.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("+790123456789");
        driver.findElement(By.tagName("button")).click();
        String actualText = driver.findElement(By.cssSelector("[data-test-id=name].input_invalid .input__sub")).getText().trim();
        String expected = "Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.";
        assertEquals(expected, actualText);
    }
}