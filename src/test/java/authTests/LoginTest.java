package authTests;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import util.Init;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class LoginTest {
    private WebDriver driver;
    private final Init init = new Init();

    @Before
    public void setUp() {
        driver = init.initWebDriver();
    }

    public void fillLoginForm() {
        driver.findElement(By.xpath("//input[@name='login']")).click();
        driver.findElement(By.xpath("//input[@name='login']")).sendKeys("Lenika2000");
        driver.findElement(By.xpath("//input[@name='password']")).click();
        driver.findElement(By.xpath("//input[@name='password']")).sendKeys("111111");
    }

    @After
    public void tearDown() {
        driver.quit();
    }

    @Test
    public void login() {
        init.login();
        assertThat(driver.findElement(By.xpath("//div[@id='main']/div/div[2]/a[2]/strong")).getText(), is("lena"));
        driver.findElement(By.xpath("//div[@id='main']/div/div[2]/a[2]")).click();
        WebElement element = driver.findElement(By.xpath("//a[contains(text(),'Выход')]"));
        JavascriptExecutor jse = (JavascriptExecutor) driver;
        jse.executeScript("arguments[0].click()", element);
    }

    @Test
    public void wrongLoginOrPassword() {
        driver.get("https://dfiles.eu/");
        driver.manage().window().setSize(new Dimension(960, 1053));
        driver.findElement(By.xpath("//div[@id='main']/div/div/a/span")).click();
        fillLoginForm();
        driver.findElement(By.xpath("//input[@name='password']")).click();
        driver.findElement(By.xpath("//input[@name='password']")).sendKeys("111111");
        driver.findElement(By.xpath("//input[@id='login_btn']")).click();
        // на случай капчи
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(100));
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@id=\"rc-anchor-container\"]/div[3]")));
        driver.findElement(By.xpath("//input[@id='login_btn']")).click();
        wait.until(ExpectedConditions.textToBePresentInElement(driver.findElement(By.xpath("//form[@id=\"login_frm\"]/table/tbody/tr[3]/td/div")), "Неверный логин или пароль"));
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        assertThat(driver.findElement(By.xpath("//form[@id=\"login_frm\"]/table/tbody/tr[3]/td/div")).getText(), is("Неверный логин или пароль"));
    }

}
