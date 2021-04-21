package uploadTests;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.After;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import util.Init;
import java.util.*;

public class FTPPageTest {
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

    @Test
    public void fTPPageTest() {
        // Переход на FTP загрузку
        driver.findElement(By.xpath("//a[contains(text(),\'FTP загрузка\')]")).click();
        // Проверка наличия логина и пароля
        {
            List<WebElement> elements = driver.findElements(By.xpath("//div/div[2]/div/div/div"));
            assert (elements.size() > 0);
        }
        // Переход на вкладку Информация
        driver.findElement(By.xpath("//div[@id=\'ftpuploadbox\']/div/ul/li[2]/span[2]")).click();
        {
            List<WebElement> elements = driver.findElements(By.xpath("//div[@id=\'ftpuploadbox\']/div[2]/div[3]/div/h3"));
            assert (elements.size() > 0);
        }
        // Переход на вкладку Журнал
        driver.findElement(By.xpath("//div[@id=\'ftpuploadbox\']/div/ul/li[3]/span[2]")).click();
        {
            List<WebElement> elements = driver.findElements(By.xpath("//table[@id=\'filelist_tab\']/thead/tr/th/span"));
            assert (elements.size() > 0);
        }
    }
}
