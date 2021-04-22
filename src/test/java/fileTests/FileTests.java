package fileTests;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import util.Init;

import java.time.Duration;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

public class FileTests {
    private static WebDriver driver;
    private static final Init init = new Init();
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
        driver.findElement(By.xpath("//div[@id='main']/div/ul/li[2]/a")).click();
        waitUntilNotificationDisappears();
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

    @Test
    public void renameFileTest() {
        choseFile();
        // Переименовать
        WebElement rename = driver.findElement(By.xpath("//*[@id=\"df_share\"]/div[2]/div[1]/a[8]"));
        jse.executeScript("arguments[0].click()", rename);
        // Очистили поле
        WebElement renameInput = driver.findElement(By.xpath("//*[@id=\"depositbox\"]/div[1]/div[2]/div/input"));
        renameInput.sendKeys(Keys.CONTROL + "a");
        renameInput.sendKeys(Keys.DELETE);
        renameInput.sendKeys(init.getProperty("newFileName"));
        // Клик Готово
        driver.findElement(By.xpath("//*[@id=\"depositbox\"]/div[1]/div[2]/div/div")).click();

        waitUntilNotificationDisappears();

        List<WebElement> allElements = driver.findElements(By.xpath("//*[@id=\"tbl_filelist\"]/tbody/tr"));
        boolean result = allElements.stream().anyMatch(tr -> tr.findElement(By.xpath("./td[contains(@class, 'filename_source')]/div/span/a")).getText().equals(init.getProperty("newFileName")));
        assertTrue(result);
    }

    @Test
    public void removeFileTest() {
        choseFile();
        String fileName = driver.findElement(By.xpath("//td[contains(@class, 'filename_source')]/div/span/a")).getText();
        int filesQuality = driver.findElements(By.xpath("//*[@id=\"tbl_filelist\"]/tbody/tr")).size();
        // Удалить
        WebElement removeBtn = driver.findElement(By.xpath("//*[@id=\"df_share\"]/div[2]/div[1]/a[7]"));
        jse.executeScript("arguments[0].click()", removeBtn);
        // Подтвердить удаление
        driver.findElement(By.xpath("//*[@id=\"depositbox\"]/div[1]/div[2]/div/div/input[1]")).click();

        waitUntilNotificationDisappears();
        int newFilesQuality = driver.findElements(By.xpath("//*[@id=\"tbl_filelist\"]/tbody/tr")).size();
        List<WebElement> allElements = driver.findElements(By.xpath("//*[@id=\"tbl_filelist\"]/tbody/tr"));
        boolean result = allElements.stream().noneMatch(tr -> tr.findElement(By.xpath("./td[contains(@class, 'filename_source')]/div/span/a")).getText().equals(fileName));
        assertTrue(result || (filesQuality- newFilesQuality == 1));
    }

    @Test
    public void getFilePropertiesTest() {
        choseFile();
        // Свойства
        driver.findElement(By.xpath("//*[@id=\"df_share\"]/div[2]/div[1]/a[10]")).click();
        assertThat(driver.findElement(By.xpath("//*[@id=\"depositbox\"]/div[1]/div[1]/span")).getText(), is("Свойства файла"));
        driver.findElement(By.xpath("//*[@id=\"depositbox\"]/div[1]/div[2]/div/div[7]")).click();
    }

    @Test
    public void moveFileTest() {
        choseFile();
        String fileName = driver.findElement(By.xpath("//td[contains(@class, 'filename_source')]/div/span/a")).getText();
        // Перемещение
        driver.findElement(By.xpath("//*[@id=\"df_share\"]/div[2]/div[1]/a[5]")).click();
        // Выбор папки, в которую перемещаем
        driver.findElement(By.xpath("//*[@id=\"depositbox\"]/div[1]/div[2]/div/div/ul/li[2]/ul/li[1]/a")).click();
        // Перемещение
        driver.findElement(By.xpath("//*[@id=\"depositbox\"]/div[1]/div[2]/div/input")).click();
        waitUntilNotificationDisappears();
        // переход в папку, куда осуществлялось перемещение
        driver.findElement(By.xpath("//*[@id=\"df_share\"]/div[1]/ul/li[2]/ul/li[1]/a")).click();
        waitUntilNotificationDisappears();
        // проверка успешного перемещения файла
        List<WebElement> allElements = driver.findElements(By.xpath("//*[@id=\"tbl_filelist\"]/tbody/tr"));
        boolean result = allElements.stream().anyMatch(tr -> tr.findElement(By.xpath("./td[contains(@class, 'filename_source')]/div/span/a")).getText().equals(fileName));
        assertTrue(result);
    }

    @Test
    public void groupFileTest() {
        choseFile();
        WebElement secondFile = driver.findElement(By.xpath("//*[@id=\"tbl_filelist\"]/tbody/tr[2]/td[2]/div/span/input"));
        jse.executeScript("arguments[0].click()", secondFile);
        WebElement[] files = driver.findElements(By.xpath("//td[contains(@class, 'filename_source')]/div/span/a")).toArray(new WebElement[0]);
        String fileName1 = files[0].getText();
        String fileName2 = files[1].getText();
        // Группировка
        driver.findElement(By.xpath("//*[@id=\"df_share\"]/div[2]/div[1]/a[6]")).click();
        // Выбор папки, в которую перемещаем
        driver.findElement(By.xpath("//*[@id=\"depositbox\"]/div[1]/div[2]/div/input")).click();
        driver.findElement(By.xpath("//*[@id=\"depositbox\"]/div[1]/div[2]/div/input")).sendKeys(init.getProperty("newGroupFolderName"));
        // готово
        driver.findElement(By.xpath("//*[@id=\"depositbox\"]/div[1]/div[2]/div/div")).click();
        waitUntilNotificationDisappears();
        // Поиск созданной папки
        List<WebElement> folders = driver.findElements(By.xpath("//*[@id=\"df_share\"]/div[1]/ul/li[2]/ul/li"));
        for (WebElement folder: folders) {
            if (folder.findElement(By.xpath("./a")).getText().split(" ")[0].equals(init.getProperty("newGroupFolderName"))) {
                folder.findElement(By.xpath("./a")).click();
                break;
            }
        }
        waitUntilNotificationDisappears();
        // проверка успешной группировки файлов todo
        List<WebElement> allElements = driver.findElements(By.xpath("//*[@id=\"tbl_filelist\"]/tbody/tr"));
        boolean result = allElements.stream().allMatch(tr ->  {
            String selectedFileName = tr.findElement(By.xpath("./td[contains(@class, 'filename_source')]/div/span/a")).getText();
            return selectedFileName.equals(fileName1) || selectedFileName.equals(fileName2);
        });
        assertTrue(result);
    }
}
