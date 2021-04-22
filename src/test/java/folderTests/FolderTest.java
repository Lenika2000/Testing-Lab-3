package folderTests;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import util.Init;

import java.time.Duration;
import java.util.List;

import static org.junit.Assert.assertTrue;

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
        driver.findElement(By.xpath("//div[@id='df_share']/div/div/a/span")).click();
        // Вводим название папки
        driver.findElement(By.xpath("//*[@id=\"depositbox\"]/div[1]/div[2]/div/input")).click();
        driver.findElement(By.xpath("//*[@id=\"depositbox\"]/div[1]/div[2]/div/input")).sendKeys(init.getProperty("folderName"));
        // Клик Готово
        driver.findElement(By.xpath("//div[@id='depositbox']/div/div[2]/div/div")).click();

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
            myFileGroups.click();
        }
        // Клик Выбрать папку
        driver.findElement(By.xpath("//*[@id=\"df_share\"]/div[1]/ul/li[2]/ul/li[1]/a")).click();
        // Клик Переименовать
        driver.findElement(By.xpath("//*[@id=\"df_share\"]/div[1]/div[1]/a[3]")).click();
        // Очистили поле
        WebElement renameInput = driver.findElement(By.xpath("//*[@id=\"depositbox\"]/div[1]/div[2]/div/input"));
        renameInput.sendKeys(Keys.CONTROL + "a");
        renameInput.sendKeys(Keys.DELETE);
        renameInput.sendKeys(init.getProperty("newFolderName"));

        // Клик Готово
        driver.findElement(By.xpath("//*[@id=\"depositbox\"]/div[1]/div[2]/div/div")).click();

        waitUntilNotificationDisappears();
        List<WebElement> allElements = driver.findElements(By.xpath("//*[@id=\"df_share\"]/div[1]/ul/li[2]/ul/li"));
        boolean result = allElements.stream().anyMatch(folder -> folder.getText().contains(init.getProperty("newFolderName")));
        assertTrue(result);
    }
}
