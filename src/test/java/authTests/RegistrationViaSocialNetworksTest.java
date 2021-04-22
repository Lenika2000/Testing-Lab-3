package authTests;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import util.Init;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import static org.hamcrest.CoreMatchers.anyOf;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

public class RegistrationViaSocialNetworksTest {
    private WebDriver driver;
    private final Map<String, Object> vars = new HashMap<>();

    @Before
    public void setUp() {
        driver = (new Init()).initWebDriver();
        driver.findElement(By.xpath("//a[contains(text(),'Зарегистрируйтесь сейчас!')]")).click();
        driver.findElement(By.xpath("//input[@id='i_undertake_conditions']")).click();
    }

    @After
    public void tearDown() {
        driver.quit();
    }

    public void changeWindow(int timeout) {
        try {
            Thread.sleep(timeout);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Set<String> whNow = driver.getWindowHandles();
        Set<String> whThen = (Set<String>) vars.get("window_handles");
        if (whNow.size() > whThen.size()) {
            whNow.removeAll(whThen);
        }
        String currentWindow = whNow.iterator().next();
        driver.switchTo().window(currentWindow);
    }

    @Test
    public void testFb() {
        // получаем уникальный идентификаторы открытых окон
        vars.put("window_handles", driver.getWindowHandles());
        WebElement facebook = driver.findElement(By.xpath("//a[contains(@class, 'button-facebook')]"));
        ((JavascriptExecutor)driver).executeScript("arguments[0].click();", facebook);
        changeWindow(2000);
        assertThat(driver.findElement(By.xpath("//div[@id='header_block']/span/div")).getText(), anyOf(is("Вход на Facebook"), is("Log in to Facebook")) );
    }

    @Test
    public void testTwitter() {
        // получаем уникальный идентификаторы открытых окон
        vars.put("window_handles", driver.getWindowHandles());
        WebElement twitter = driver.findElement(By.xpath("//a[contains(@class, 'button-twitter')]"));
        ((JavascriptExecutor)driver).executeScript("arguments[0].click();", twitter);
        changeWindow(2000);
        assertThat(driver.findElement(By.xpath("//div[@id='bd']/div/h2")).getText(), anyOf(is("Authorize Depositfiles to access your account?"), is("Разрешить приложению Depositfiles доступ к вашей учетной записи?")) );
    }

    @Test
    public void testVk() {
        // получаем уникальный идентификаторы открытых окон
        vars.put("window_handles", driver.getWindowHandles());
        WebElement vk = driver.findElement(By.xpath("//a[contains(@class, 'button-vkontakte')]"));
        ((JavascriptExecutor)driver).executeScript("arguments[0].click();", vk);
        changeWindow(2000);
        assertEquals("ВКонтакте", driver.findElement(By.xpath("//*[@id=\"oauth_wrap_content\"]/div[2]/div/b")).getText() );
    }

    @Test
    public void testGoogle() {
        // получаем уникальный идентификаторы открытых окон
        vars.put("window_handles", driver.getWindowHandles());
        WebElement google = driver.findElement(By.xpath("//a[contains(@class, 'button-google')]"));
        ((JavascriptExecutor)driver).executeScript("arguments[0].click();", google);
        changeWindow(2000);
        assertThat(driver.getTitle(), is("Вход – Google Аккаунты"));
    }
}
