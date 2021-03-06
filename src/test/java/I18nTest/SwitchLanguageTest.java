package I18nTest;//generatedBySeleniumIDE

import org.junit.After;
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

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class SwitchLanguageTest {
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
    public void switchLanguage() {
        {
            WebElement element = driver.findElement(By.xpath("//a[contains(@class, 'flag_en')]"));
            JavascriptExecutor jse = (JavascriptExecutor) driver;
            jse.executeScript("arguments[0].scrollIntoView()", element);
        }
        driver.findElement(By.xpath("//a[contains(@class, 'flag_en')]")).click();
        {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
            wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//a[contains(@class, 'active') and contains(@class, 'flag_en')]")));
        }
        assertThat(driver.findElement(By.xpath("//div[@id='main']/div[3]/div[3]/div/h2")).getText(), is("Why to use DepositFiles?"));


        {
            WebElement element = driver.findElement(By.xpath("//a[contains(@class, 'flag_de')]"));
            JavascriptExecutor jse = (JavascriptExecutor) driver;
            jse.executeScript("arguments[0].scrollIntoView()", element);
        }
        driver.findElement(By.xpath("//a[contains(@class, 'flag_de')]")).click();
        {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
            wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//a[contains(@class, 'active') and contains(@class, 'flag_de')]")));
        }

        assertThat(driver.findElement(By.xpath("//div[@id='main']/div[3]/div[3]/div/h2")).getText(), is("Warum benutzt man DepositFiles?"));


        {
            WebElement element = driver.findElement(By.xpath("//a[contains(@class, 'flag_es')]"));
            JavascriptExecutor jse = (JavascriptExecutor) driver;
            jse.executeScript("arguments[0].scrollIntoView()", element);
        }
        driver.findElement(By.xpath("//a[contains(@class, 'flag_es')]")).click();
        {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
            wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//a[contains(@class, 'active') and contains(@class, 'flag_es')]")));
        }
        assertThat(driver.findElement(By.xpath("//div[@id='main']/div[3]/div[3]/div/h2")).getText(), is("??Por qu?? utilizar DepositFiles?"));


        {
            WebElement element = driver.findElement(By.xpath("//a[contains(@class, 'flag_pt')]"));
            JavascriptExecutor jse = (JavascriptExecutor) driver;
            jse.executeScript("arguments[0].scrollIntoView()", element);
        }
        driver.findElement(By.xpath("//a[contains(@class, 'flag_pt')]")).click();
        {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
            wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//a[contains(@class, 'active') and contains(@class, 'flag_pt')]")));
        }
        assertThat(driver.findElement(By.xpath("//div[@id='main']/div[3]/div[3]/div/h2")).getText(), is("Por que usar DepositFiles?"));


        {
            WebElement element = driver.findElement(By.xpath("//a[contains(@class, 'flag_fr')]"));
            JavascriptExecutor jse = (JavascriptExecutor) driver;
            jse.executeScript("arguments[0].scrollIntoView()", element);
        }
        driver.findElement(By.xpath("//a[contains(@class, 'flag_fr')]")).click();
        {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
            wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//a[contains(@class, 'active') and contains(@class, 'flag_fr')]")));
        }
        assertThat(driver.findElement(By.xpath("//div[@id='main']/div[3]/div[3]/div/h2")).getText(), is("Pourquoi utiliser DepositFiles?"));


        {
            WebElement element = driver.findElement(By.xpath("//a[contains(@class, 'flag_ja')]"));
            JavascriptExecutor jse = (JavascriptExecutor) driver;
            jse.executeScript("arguments[0].scrollIntoView()", element);
        }
        driver.findElement(By.xpath("//a[contains(@class, 'flag_ja')]")).click();
        {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
            wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//a[contains(@class, 'active') and contains(@class, 'flag_ja')]")));
        }
        assertThat(driver.findElement(By.xpath("//div[@id='main']/div[3]/div[3]/div/h2")).getText(), is("DepositFiles????????????????????????"));


        {
            WebElement element = driver.findElement(By.xpath("//a[contains(@class, 'flag_nl')]"));
            JavascriptExecutor jse = (JavascriptExecutor) driver;
            jse.executeScript("arguments[0].scrollIntoView()", element);
        }
        driver.findElement(By.xpath("//a[contains(@class, 'flag_nl')]")).click();
        {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
            wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//a[contains(@class, 'active') and contains(@class, 'flag_nl')]")));
        }
        assertThat(driver.findElement(By.xpath("//div[@id='main']/div[3]/div[3]/div/h2")).getText(), is("Waarom gebruikt men DepositFiles?"));


        {
            WebElement element = driver.findElement(By.xpath("//a[contains(@class, 'flag_it')]"));
            JavascriptExecutor jse = (JavascriptExecutor) driver;
            jse.executeScript("arguments[0].scrollIntoView()", element);
        }
        driver.findElement(By.xpath("//a[contains(@class, 'flag_it')]")).click();
        {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
            wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//a[contains(@class, 'active') and contains(@class, 'flag_it')]")));
        }
        assertThat(driver.findElement(By.xpath("//div[@id='main']/div[3]/div[3]/div/h2")).getText(), is("Perch?? usare DepositFiles?"));


        {
            WebElement element = driver.findElement(By.xpath("//a[contains(@class, 'flag_sv')]"));
            JavascriptExecutor jse = (JavascriptExecutor) driver;
            jse.executeScript("arguments[0].scrollIntoView()", element);
        }
        driver.findElement(By.xpath("//a[contains(@class, 'flag_sv')]")).click();
        {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
            wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//a[contains(@class, 'active') and contains(@class, 'flag_sv')]")));
        }
        assertThat(driver.findElement(By.xpath("//div[@id='main']/div[3]/div[3]/div/h2")).getText(), is("Varf??r anv??nder man DepositFiles?"));


        {
            WebElement element = driver.findElement(By.xpath("//a[contains(@class, 'flag_tr')]"));
            JavascriptExecutor jse = (JavascriptExecutor) driver;
            jse.executeScript("arguments[0].scrollIntoView()", element);
        }
        driver.findElement(By.xpath("//a[contains(@class, 'flag_tr')]")).click();
        {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
            wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//a[contains(@class, 'active') and contains(@class, 'flag_tr')]")));
        }
        assertThat(driver.findElement(By.xpath("//div[@id='main']/div[3]/div[3]/div/h2")).getText(), is("DepositFiles'?? neden kullan??yorsan??z?"));


        {
            WebElement element = driver.findElement(By.xpath("//a[contains(@class, 'flag_da')]"));
            JavascriptExecutor jse = (JavascriptExecutor) driver;
            jse.executeScript("arguments[0].scrollIntoView()", element);
        }
        driver.findElement(By.xpath("//a[contains(@class, 'flag_da')]")).click();
        {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
            wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//a[contains(@class, 'active') and contains(@class, 'flag_da')]")));
        }
        assertThat(driver.findElement(By.xpath("//div[@id='main']/div[3]/div[3]/div/h2")).getText(), is("Why to use DepositFiles?"));

    }
}
