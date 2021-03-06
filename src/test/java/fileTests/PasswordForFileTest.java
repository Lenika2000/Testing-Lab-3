package fileTests;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import util.Init;

import java.time.Duration;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class PasswordForFileTest {
    private static WebDriver driver;
    private static final Init init = new Init();
    private static JavascriptExecutor jse;

    @BeforeClass
    public static void setUp() {
        driver = init.initWebDriver();
        jse = (JavascriptExecutor) driver;
        init.login();
        driver.get("https://dfiles.eu/");
        // переход на страницу Мои файлы
        driver.findElement(By.xpath("//div[@id='main']/div/ul/li[2]/a")).click();
    }

    @AfterClass
    public static void tearDown() {
        driver.quit();
    }

    public void choseFile() {
        // переходим во все файлы
        driver.findElement(By.xpath("//*[@id=\"df_share\"]/div[1]/ul/li[4]/a")).click();
        waitUntilNotificationDisappears();
        driver.findElement(By.xpath("//*[@id=\"tbl_filelist\"]/tbody/tr[1]/td[1]/input")).click();
    }

    public void waitUntilNotificationDisappears() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@id=\"ajaxStatus\"]")));
    }

    // todo
    @Test
    public void setPasswordForFileTest() {
        choseFile();
        // Свойства
        driver.findElement(By.xpath("//*[@id=\"df_share\"]/div[2]/div[1]/a[10]")).click();
        // Установка пароля
        driver.findElement(By.xpath("//*[@id=\"depositbox\"]/div[1]/div[2]/div/div[6]/input")).click();
        driver.findElement(By.xpath("//*[@id=\"depositbox\"]/div[1]/div[2]/div/div[6]/input")).sendKeys("111111");
        // Готово
        driver.findElement(By.xpath("//*[@id=\"depositbox\"]/div[1]/div[2]/div/div[7]")).click();
        waitUntilNotificationDisappears();
        // Переход на скачивание файла
        driver.findElement(By.xpath("//*[@id=\"tbl_filelist\"]/tbody/tr[1]/td[2]/div/span/a")).click();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(100));
        wait.until(ExpectedConditions.textToBePresentInElement(driver.findElement(By.xpath("//*[@id=\"main\"]/div[4]/div/div[4]/form/div/p/strong")), "Пожалуйста, введите пароль для этого файла:"));

        assertThat(driver.findElement(By.xpath("//*[@id=\"main\"]/div[4]/div/div[4]/form/div/p/strong")).getText(), is("Пожалуйста, введите пароль для этого файла:"));
    }
}
