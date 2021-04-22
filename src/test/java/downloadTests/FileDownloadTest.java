package downloadTests;

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

public class FileDownloadTest {
    private static WebDriver driver;
    private static final Init init = new Init();

    @BeforeClass
    public static void setUp() {
        driver = init.initWebDriver();
        init.login();
        driver.get("https://dfiles.eu/");
    }

    public void waitUntilNotificationDisappears() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@id=\"ajaxStatus\"]")));
    }

    @After
    public void tearDown() {
        driver.quit();
    }

    @Test
    public void fileDownload() {
        // переход в Мой файлы
        driver.findElement(By.xpath("//div[@id='main']/div/ul/li[2]/a")).click();
        waitUntilNotificationDisappears();
        String fileName = driver.findElement(By.xpath("//td[contains(@class, 'filename_source')]/div/span/a")).getText();
        // переходим на скачивание файла
        driver.findElement(By.xpath("//table[@id='tbl_filelist']/tbody/tr/td[2]/div/span")).click();
        // проверка, что попали на скачивание нужного файла
        assertThat(driver.findElement(By.xpath("//*[@id=\"main\"]/div[4]/div[2]/div/div[2]/div[1]/div[1]/div[1]/b")).getText(), is(fileName));
        // ждем появления продолжить
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(100));
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id=\"download_recaptcha_container\"]/input")));
        // ввод капчи
        WebDriverWait wait2 = new WebDriverWait(driver, Duration.ofSeconds(200));
        wait2.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id=\"downloader_file_form\"]/a")));
        // проверка, что появилась кнопка скачать
        assertThat(driver.findElement(By.xpath("//*[@id=\"downloader_file_form\"]/a")).getText(), is("Скачать файл\nбраузером в обычном режиме"));
        // скачать
        driver.findElement(By.xpath("//*[@id=\"downloader_file_form\"]/a")).click();
        assertThat(driver.findElement(By.xpath("//*[@id=\"main\"]/div[4]/div[2]/div/div[4]/div[1]")).getText().split("\n")[0], is("Началось скачивание файла..."));
    }
}
