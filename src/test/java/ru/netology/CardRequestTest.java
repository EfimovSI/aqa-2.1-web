package ru.netology;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CardRequestTest {
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
    public void tearDown() {
        driver.quit();
        driver = null;
    }

    @Test
    public void shouldSendForm() {
        driver.findElement(By.cssSelector("[data-test-id='name'] input")).sendKeys("Сергей Иванов");
        driver.findElement(By.cssSelector("[data-test-id='phone'] input")).sendKeys("+79991112233");
        driver.findElement(By.cssSelector("[data-test-id='agreement'] span")).click();
        driver.findElement(By.cssSelector("button")).click();
        String actualText = driver.findElement(By.cssSelector("[data-test-id='order-success']")).getText().trim();
        String expected = "Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время.";
        assertEquals(expected, actualText); //Как на лекции
    }

    @Test
    public void shouldExceptЁ() {
        driver.findElement(By.cssSelector("[data-test-id='name'] input")).sendKeys("Алёна Петрова");
        driver.findElement(By.cssSelector("[data-test-id='phone'] input")).sendKeys("+79991112233");
        driver.findElement(By.cssSelector("[data-test-id='agreement'] span")).click();
        driver.findElement(By.cssSelector("button")).click();
        driver.findElement(By.cssSelector("[data-test-id='order-success']")).isDisplayed(); //Так тесты будут стабильнее
    }

    @Test
    public void shouldExceptDash() {
        driver.findElement(By.cssSelector("[data-test-id='name'] input")).sendKeys("Анна-Мария Петрова");
        driver.findElement(By.cssSelector("[data-test-id='phone'] input")).sendKeys("+79991112233");
        driver.findElement(By.cssSelector("[data-test-id='agreement'] span")).click();
        driver.findElement(By.cssSelector("button")).click();
        driver.findElement(By.cssSelector("[data-test-id='order-success']")).isDisplayed();
    }

    @Test
    public void shouldNotSendEmptyForm() {
        driver.findElement(By.cssSelector("button")).click();
        driver.findElement(By.cssSelector(".input_invalid")).isDisplayed();
    }

    @Test
    public void shouldNotSendSpaceForName() {
        driver.findElement(By.cssSelector("[data-test-id='name'] input")).sendKeys(" ");
        driver.findElement(By.cssSelector("[data-test-id='phone'] input")).sendKeys("+79991112233");
        driver.findElement(By.cssSelector("[data-test-id='agreement'] span")).click();
        driver.findElement(By.cssSelector("button")).click();
        driver.findElement(By.cssSelector(".input_invalid")).isDisplayed();
    }

    @Test
    public void shouldNotSendDashForName() {
        driver.findElement(By.cssSelector("[data-test-id='name'] input")).sendKeys("-");
        driver.findElement(By.cssSelector("[data-test-id='phone'] input")).sendKeys("+79991112233");
        driver.findElement(By.cssSelector("[data-test-id='agreement'] span")).click();
        driver.findElement(By.cssSelector("button")).click();
        driver.findElement(By.cssSelector(".input_invalid")).isDisplayed();
    }

    @Test
    public void shouldNotSendLatinName() {
        driver.findElement(By.cssSelector("[data-test-id='name'] input")).sendKeys("Ivan Ivanov");
        driver.findElement(By.cssSelector("[data-test-id='phone'] input")).sendKeys("+79991112233");
        driver.findElement(By.cssSelector("[data-test-id='agreement'] span")).click();
        driver.findElement(By.cssSelector("button")).click();
        driver.findElement(By.cssSelector(".input_invalid")).isDisplayed();
    }

    @Test
    public void shouldNotSendSpecialSymbols() {
        driver.findElement(By.cssSelector("[data-test-id='name'] input")).sendKeys("!#&?");
        driver.findElement(By.cssSelector("[data-test-id='phone'] input")).sendKeys("+79991112233");
        driver.findElement(By.cssSelector("[data-test-id='agreement'] span")).click();
        driver.findElement(By.cssSelector("button")).click();
        driver.findElement(By.cssSelector(".input_invalid")).isDisplayed();
    }

    @Test
    public void shouldNotSendNumbersInName() {
        driver.findElement(By.cssSelector("[data-test-id='name'] input")).sendKeys("Иван 1");
        driver.findElement(By.cssSelector("[data-test-id='phone'] input")).sendKeys("+79991112233");
        driver.findElement(By.cssSelector("[data-test-id='agreement'] span")).click();
        driver.findElement(By.cssSelector("button")).click();
        driver.findElement(By.cssSelector(".input_invalid")).isDisplayed();
    }

    @Test
    public void shouldNotSendNameWithoutSurname() {
        driver.findElement(By.cssSelector("[data-test-id='name'] input")).sendKeys("Денис");
        driver.findElement(By.cssSelector("[data-test-id='phone'] input")).sendKeys("+79991112233");
        driver.findElement(By.cssSelector("[data-test-id='agreement'] span")).click();
        driver.findElement(By.cssSelector("button")).click();
        driver.findElement(By.cssSelector(".input_invalid")).isDisplayed();
    }

    @Test
    public void shouldNotSendWithoutPlusInPhone() {
        driver.findElement(By.cssSelector("[data-test-id='name'] input")).sendKeys("Иван Иванов");
        driver.findElement(By.cssSelector("[data-test-id='phone'] input")).sendKeys("79991112233");
        driver.findElement(By.cssSelector("[data-test-id='agreement'] span")).click();
        driver.findElement(By.cssSelector("button")).click();
        driver.findElement(By.cssSelector(".input_invalid")).isDisplayed();
    }

    @Test
    public void shouldNotSendLessDigitsInPhone() {
        driver.findElement(By.cssSelector("[data-test-id='name'] input")).sendKeys("Иван Иванов");
        driver.findElement(By.cssSelector("[data-test-id='phone'] input")).sendKeys("+7999111222");
        driver.findElement(By.cssSelector("[data-test-id='agreement'] span")).click();
        driver.findElement(By.cssSelector("button")).click();
        driver.findElement(By.cssSelector(".input_invalid")).isDisplayed();
    }

    @Test
    public void shouldNotSendWithoutCheckbox() {
        driver.findElement(By.cssSelector("[data-test-id='name'] input")).sendKeys("Иван Иванов");
        driver.findElement(By.cssSelector("[data-test-id='phone'] input")).sendKeys("+79991112233");
        driver.findElement(By.cssSelector("button")).click();
        driver.findElement(By.cssSelector(".input_invalid")).isDisplayed();
    }
}
