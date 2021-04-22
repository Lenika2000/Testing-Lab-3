package pagesTests;

import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import util.Init;

import java.time.Duration;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class GoToThePaymentsPageTest {
    private static WebDriver driver;
    private static final Init init = new Init();

    @BeforeClass
    public static void setUp() {
        driver = init.initWebDriver();
        driver.get("https://dfiles.eu/");
    }

    @After
    public void tearDown() {
        driver.quit();
    }

    @Test
    public void goToThePaymentsPage() {
        // Переход на платежи
        driver.findElement(By.xpath("//div[@id='main']/div/ul/li[3]/a")).click();
        // Ожидание загрузки страницы
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@id='payments_gold']/div/div")));
        // Выбираем тип членства 1 месяц, оплата картой
        driver.findElement(By.xpath("//div[@id='payments_gold']/div/div[4]/div/a/nowrap")).click();
        // Вводим неверный адрес почты
        driver.findElement(By.xpath("(//input[@name='email'])[2]")).click();
        driver.findElement(By.xpath("(//input[@name='email'])[2]")).sendKeys("123");
        driver.findElement(By.xpath("(//input[@name='email'])[2]")).sendKeys(Keys.ENTER);
        // Подтверждаю условия
        driver.findElement(By.xpath("//*[@id=\"i_undertake_conditions\"]")).click();
        // Подтвердить
        driver.findElement(By.xpath("(//input[@value='Подтвердите'])[2]")).click();
        // Появилось сообщение об ошибке
        assertThat(driver.findElement(By.xpath("//*[@id=\"dialogEmailRequest\"]/form/div[1]/div[1]")).getAttribute("class"), is("error_msg"));
        // Вводим неверный адрес почты
        driver.findElement(By.xpath("(//input[@name='email'])[2]")).click();
        driver.findElement(By.xpath("(//input[@name='email'])[2]")).sendKeys("123@mail.ru");
        driver.findElement(By.xpath("(//input[@name='email'])[2]")).sendKeys(Keys.ENTER);
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id=\"aspnetForm\"]/div[3]/div/div[1]/h2")));
        // Переход на страницу оплаты
        assertThat(driver.findElement(By.xpath("//*[@id=\"aspnetForm\"]/div[3]/div/div[1]/h2")).getText(), is("www.accountlicense.com"));
    }
}
