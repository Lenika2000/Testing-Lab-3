package util;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.FileInputStream;
import java.io.IOException;
import java.time.Duration;
import java.util.Properties;

public class Init {
    private final Properties property = new Properties();
    private FileInputStream fileInputStream;
    private WebDriver driver;

    public Init() {
        try {
            fileInputStream = new FileInputStream(".\\src\\test\\resources\\config.properties");
            property.load(fileInputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public WebDriver initWebDriver() {
        if (property.getProperty("driverType").equals("CHROME")) {
            System.setProperty("webdriver.chrome.driver", "C:\\Users\\1395353\\driversSelenium\\chromedriver.exe");
            driver = new ChromeDriver();
        } else if (property.getProperty("driverType").equals("FIREFOX")) {
            System.setProperty("webdriver.gecko.driver", "C:\\Users\\1395353\\driversSelenium\\geckodriver.exe");
            driver = new FirefoxDriver();
        }
        setRULang();
        return driver;
    }

    public void setRULang() {
        driver.get("https://dfiles.eu/");
        WebElement element = driver.findElement(By.xpath("//a[contains(@class, 'flag_ru')]"));
        JavascriptExecutor jse = (JavascriptExecutor) driver;
        // проматываем до подвала сайта
        jse.executeScript("arguments[0].scrollIntoView()", element);
        // установка русского языка
        element.click();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
        // ждем переключения языка
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//a[contains(@class, 'active') and contains(@class, 'flag_ru')]")));

        driver.manage().window().setSize(new Dimension(960, 1053));
    }

    public void login() {
        driver.get("https://dfiles.eu/");
        driver.manage().window().setSize(new Dimension(960, 1053));
        driver.findElement(By.xpath("//div[@id='main']/div/div/a/span")).click();
        driver.findElement(By.xpath("//input[@name='login']")).click();
        driver.findElement(By.xpath("//input[@name='login']")).sendKeys("Lenika2000");
        driver.findElement(By.xpath("//input[@name='password']")).click();
        driver.findElement(By.xpath("//input[@name='password']")).sendKeys("111111");
        driver.findElement(By.xpath("//input[@id='login_btn']")).click();
        // На случай ввода капчи
        {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(300));
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[contains(@href, '/gold/files_list.php')]")));
        }
    }

    public String getProperty(String name) {
        return property.getProperty(name);
    }
}
