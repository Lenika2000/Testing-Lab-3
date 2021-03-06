package pagesTests;

import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import util.Init;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class LoyaltyPageviewTest {
    private static WebDriver driver;
    private static final Init init = new Init();
    private final Map<String, Object> vars = new HashMap<>();

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
    public void loyaltyPageview() {
        vars.put("window_handle", driver.getWindowHandle());
        // Переход в программу лояльности
        driver.findElement(By.xpath("//div[@id='main']/div/ul/li[4]/a")).click();
        assertThat(driver.findElement(By.xpath("//*[@id=\"main\"]/div[4]/div[2]/div/div/h1/strong")).getText(), is("Loyalty Program!"));
        // Переход к U-point'ам
        driver.findElement(By.xpath("//a[contains(text(),'U-поинты')]")).click();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(100));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"main\"]/div[4]/div[2]/div/div/p[1][contains(text(), 'U-Points')]")));
        assertThat(driver.findElement(By.xpath("//*[@id=\"main\"]/div[4]/div[2]/div/div/p[1]")).getText(), is("U-Points - are Points you get for your files (uploaded by you to Depositfiles.com earlier) being downloaded."));
        // Переход к D-point'ам
        driver.findElement(By.xpath("//a[contains(text(),'D-поинты')]")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='main']/div[4]/div[2]/div/div/p[contains(text(),'D-Points')]")));

        assertThat(driver.findElement(By.xpath("//div[@id='main']/div[4]/div[2]/div/div/p")).getText(), is("D-Points - are Points you get for downloading files"));
    }
}
