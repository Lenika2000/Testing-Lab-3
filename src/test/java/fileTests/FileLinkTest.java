package fileTests;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import util.Init;

import java.time.Duration;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

public class FileLinkTest {
    private static WebDriver driver;
    private static Init init = new Init();
    private static JavascriptExecutor jse;

    @BeforeClass
    public static void setUp() {
        driver = init.initWebDriver();
        jse = (JavascriptExecutor) driver;
        init.login();
        driver.get("https://dfiles.eu/");
    }

    @Before
    public void setMyFilesFolder() {
        // переход на страницу Мои файлы
        driver.findElement(By.xpath("//div[@id=\'main\']/div/ul/li[2]/a")).click();
        waitUntilNotificationDisappears();
    }

    @AfterClass
    public static void tearDown() {
        driver.quit();
    }

    public void choseFile() {
        // переходим во все файлы
        WebElement allFiles = driver.findElement(By.xpath("//*[@id=\"df_share\"]/div[1]/ul/li[4]/a"));
        jse.executeScript("arguments[0].click()", allFiles);
        waitUntilNotificationDisappears();
        WebElement file = driver.findElement(By.xpath("//*[@id=\"tbl_filelist\"]/tbody/tr[1]/td[1]/input"));
        jse.executeScript("arguments[0].click()", file);
    }

    public void waitUntilNotificationDisappears() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@id=\"ajaxStatus\"]")));
    }

    @Test
    public void getFileLinkTest() {
        choseFile();
        String fileName = driver.findElement(By.xpath("//td[contains(@class, 'filename_source')]/div/span/a")).getText();
        WebElement getLinkBtn = driver.findElement(By.xpath("//*[@id=\"df_share\"]/div[2]/div[1]/a[1]"));
        jse.executeScript("arguments[0].click()", getLinkBtn);
        String link = driver.findElement(By.xpath("//*[@id=\"depositbox\"]/div[1]/div[2]/div/textarea")).getAttribute("value");
        driver.get(link);
        // проверить, что ссылка ведет на скачивание выбранного файла
        assertThat(driver.findElement(By.xpath("//*[@id=\"main\"]/div[4]/div[2]/div/div[2]/div[1]/div[1]/div[1]/b")).getText(), is(fileName));
    }
}
