package authTests;

import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import util.Init;

import java.time.Duration;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class PasswordRecoveryTest {
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
    public void passwordRecovery() {
        driver.findElement(By.xpath("//div[@id='main']/div/div/a/span")).click();
        driver.findElement(By.xpath("//form[@id='login_frm']/table/tbody/tr[8]/td/a")).click();
        driver.findElement(By.xpath("//input[@name='login']")).click();
        driver.findElement(By.xpath("//input[@name='login']")).sendKeys("Lenika2000");
        // капча
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(100));
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@id=\"main\"]/div[4]/div[2]/div[2]/form/table/tbody/tr[4]/td[2]/input")));
        assertThat(driver.findElement(By.xpath("//*[@id=\"main\"]/div[4]/div[2]/div[2]")).getText(), is("Инструкции по восстановлению пароля отправлены на ваш e-mail, указанный в контактной информации."));
    }
}
