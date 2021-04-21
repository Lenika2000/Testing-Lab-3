package authTests;

import org.junit.Test;
import org.junit.Before;
import org.junit.After;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.is;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.JavascriptExecutor;
import util.Init;

import java.util.concurrent.TimeUnit;

public class LoginTest {
    private WebDriver driver;
    private Init init = new Init();

    @Before
    public void setUp() {
        driver = init.initWebDriver();
    }

    public void fillLoginForm() {
        driver.findElement(By.xpath("//input[@name=\'login\']")).click();
        driver.findElement(By.xpath("//input[@name=\'login\']")).sendKeys("Lenika2000");
        driver.findElement(By.xpath("//input[@name=\'password\']")).click();
        driver.findElement(By.xpath("//input[@name=\'password\']")).sendKeys("111111");
    }

    @After
    public void tearDown() {
        driver.quit();
    }

    @Test
    public void login() {
        init.login();
        assertThat(driver.findElement(By.xpath("//div[@id=\'main\']/div/div[2]/a[2]/strong")).getText(), is("lena"));
        driver.findElement(By.xpath("//div[@id=\'main\']/div/div[2]/a[2]")).click();
        {
            WebElement element = driver.findElement(By.xpath("//a[contains(text(),\'Выход\')]"));
            JavascriptExecutor jse = (JavascriptExecutor) driver;
            jse.executeScript("arguments[0].click()", element);
        }
    }

    @Test
    public void wrongLoginOrPassword() {
        driver.get("https://dfiles.eu/");
        driver.manage().window().setSize(new Dimension(960, 1053));
        driver.findElement(By.xpath("//div[@id=\'main\']/div/div/a/span")).click();
        fillLoginForm();
        driver.findElement(By.xpath("//input[@name=\'password\']")).click();
        driver.findElement(By.xpath("//input[@name=\'password\']")).sendKeys("111111");
        driver.findElement(By.xpath("//input[@id=\'login_btn\']")).click();
        // на случай капчи
        if (driver.findElement(By.xpath("//form[@id=\"login_frm\"]/table/tbody/tr[3]/td/div")).getText().contains("Пожалуйста, поставьте флажок напротив \"Я человек\"")) {
            driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
        }
        driver.findElement(By.xpath("//input[@id=\'login_btn\']")).click();
        assertThat(driver.findElement(By.xpath("//form[@id=\"login_frm\"]/table/tbody/tr[3]/td/div")).getText(), is("Неверный логин или пароль"));
        driver.findElement(By.xpath("//div[@id=\'main\']/div/div[2]/a[2]")).click();
    }

}
