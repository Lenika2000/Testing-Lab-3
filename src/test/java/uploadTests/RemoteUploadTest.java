package uploadTests;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.Before;
import org.junit.After;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.core.IsNot.not;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Alert;
import org.openqa.selenium.Keys;
import util.Init;

import java.time.Duration;
import java.util.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

public class RemoteUploadTest {
    private static WebDriver driver;
    private static Init init = new Init();

    @BeforeClass
    public static void setUp() {
        driver = init.initWebDriver();
        init.login();
        driver.get("https://dfiles.eu/");
    }

    @After
    public void tearDown() {
        driver.quit();
    }

    public void waitUntilNotificationDisappears() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@id=\"ajaxload\"]")));
    }

    @Test
    public void remoteUpload() {
        // переходим в удаленную загрузку
        driver.findElement(By.xpath("//a[contains(text(),\'Удаленная загрузка\')]")).click();
        // ввод url
        driver.findElement(By.xpath("//input[@name=\'remote_download_url\']")).click();
        driver.findElement(By.xpath("//input[@name=\'remote_download_url\']")).sendKeys("https://images.ua.prom.st/1440764527_w640_h640_1440764527.jpg");
        // заполнение логина и пароля
        driver.findElement(By.xpath("//input[@name=\'remote_download_login\']")).click();
        driver.findElement(By.xpath("//input[@name=\'remote_download_login\']")).sendKeys("lena123");
        driver.findElement(By.xpath("//input[@name=\'remote_download_password\']")).click();
        driver.findElement(By.xpath("//input[@name=\'remote_download_password\']")).sendKeys("111111");
        // Загрузить
        driver.findElement(By.xpath("//button[@name=\'remote_upload_btn\']")).click();
        // ждем появления диалогового окна
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[@name=\'cancel\']")));
        // закрыть диалоговое окно
        driver.findElement(By.xpath("//button[@name=\'cancel\']")).click();
        // Информация
        driver.findElement(By.xpath("//div[@id=\'remoteuploadbox\']/div/ul/li[2]/span[2]")).click();
        driver.findElement(By.xpath("//div[@id=\'remoteuploadbox\']/div[2]/div[2]/div/h3")).click();
        List<WebElement> element = driver.findElements(By.xpath("//div[@id=\'remoteuploadbox\']/div[2]/div[2]/div/h3"));
        assertEquals ("Проверка наличия информации", "Внимание!", driver.findElement(By.xpath("//div[@id=\'remoteuploadbox\']/div[2]/div[2]/div/h3")).getText());
        // Журнал
        driver.findElement(By.xpath("//div[@id=\'remoteuploadbox\']/div/ul/li[3]")).click();
        List<WebElement> elements = driver.findElements(By.xpath("//table[@id=\'filelist_tab\']/thead/tr/th/span"));
        assertTrue ("Проверка наличия колонки URL",elements.size() > 0);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
//        //перезагрузка страницы
//        driver.navigate().refresh();
//        // Журнал
//        driver.findElement(By.xpath("//div[@id=\'remoteuploadbox\']/div/ul/li[3]")).click();
        waitUntilNotificationDisappears();
        assertEquals("Проверка начала загрузки","https://images.ua.prom.st/1440764527_w640_h640_1440764527.jpg", driver.findElement(By.xpath("//*[@id=\"filelist_tab_body\"]/tr[1]/td[1]/span/a")).getAttribute("title")) ;
    }
}
