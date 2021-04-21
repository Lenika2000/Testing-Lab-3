package folderTests;

import org.junit.*;

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

public class FolderTest {
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

    public void waitUntilNotificationDisappears() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@id=\"ajaxStatus\"]")));
    }

    @AfterClass
    public static void tearDown() {
        driver.quit();
    }

    @Test
    public void addFolderTest() {
        // Клик Создать
        WebElement newFolderBtn = driver.findElement(By.xpath("//div[@id='df_share']/div/div/a/span"));
        jse.executeScript("arguments[0].click()", newFolderBtn);
        // Вводим название папки
        driver.findElement(By.xpath("//*[@id=\"depositbox\"]/div[1]/div[2]/div/input")).click();
        driver.findElement(By.xpath("//*[@id=\"depositbox\"]/div[1]/div[2]/div/input")).sendKeys(init.getProperty("folderName"));
        // Клик Готово
        WebElement createBtn = driver.findElement(By.xpath("//div[@id='depositbox']/div/div[2]/div/div"));
        jse.executeScript("arguments[0].click()", createBtn);

//        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
//        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//div[@id=\'depositbox\']/div/div[2]/div/div")));
        waitUntilNotificationDisappears();
        List<WebElement> allElements = driver.findElements(By.xpath("//*[@id=\"df_share\"]/div[1]/ul/li[2]/ul/li"));
        boolean result = allElements.stream().anyMatch(folder -> folder.getText().contains(init.getProperty("folderName")));
        assertTrue(result);
    }

    @Test
    public void renameFolderTest() {
        // Открыть список папок
        WebElement myFileGroups = driver.findElement(By.xpath("//*[@id=\"df_share\"]/div[1]/ul/li[2]"));
        if (myFileGroups.getAttribute("class").contains("collapsed")) {
            jse.executeScript("arguments[0].click()", myFileGroups);
        }
        // Клик Выбрать папку
        WebElement selectedFolder = driver.findElement(By.xpath("//*[@id=\"df_share\"]/div[1]/ul/li[2]/ul/li[1]/a"));
        jse.executeScript("arguments[0].click()", selectedFolder);
        // Клик Переименовать
        WebElement renameFolderBtn = driver.findElement(By.xpath("//*[@id=\"df_share\"]/div[1]/div[1]/a[3]"));
        jse.executeScript("arguments[0].click()", renameFolderBtn);
        // Очистили поле
        WebElement renameInput = driver.findElement(By.xpath("//*[@id=\"depositbox\"]/div[1]/div[2]/div/input"));
        renameInput.sendKeys(Keys.CONTROL + "a");
        renameInput.sendKeys(Keys.DELETE);
        renameInput.sendKeys(init.getProperty("newFolderName"));

        // Клик Готово
        WebElement renameBtn = driver.findElement(By.xpath("//*[@id=\"depositbox\"]/div[1]/div[2]/div/div"));
        jse.executeScript("arguments[0].click()", renameBtn);

//        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
//        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@id=\"depositbox\"]/div[1]/div[2]/div/div")));
        waitUntilNotificationDisappears();
        List<WebElement> allElements = driver.findElements(By.xpath("//*[@id=\"df_share\"]/div[1]/ul/li[2]/ul/li"));
        boolean result = allElements.stream().anyMatch(folder -> folder.getText().contains(init.getProperty("newFolderName")));
        assertTrue(result);
    }
}
