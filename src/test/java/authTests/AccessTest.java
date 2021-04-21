package authTests;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.*;
import util.Init;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class AccessTest {
    private WebDriver driver;

    @Before
    public void setUp() {
        driver = (new Init()).initWebDriver();
        driver.get("https://dfiles.eu/");
        driver.manage().window().setSize(new Dimension(960, 1053));
    }

    @After
    public void tearDown() {
        driver.quit();
    }

    @Test
    public void checkFTPDownloadPage() {
        driver.findElement(By.xpath("//*[@id=\"member_menu\"]/div[1]/div/a[2]")).click();
        assertThat(driver.findElement(By.xpath("//*[@id=\"login_frm\"]/table/tbody/tr[1]/th")).getText(), is("Войти через:"));
    }

    @Test
    public void checkRemoteDownloadPage() {
        driver.findElement(By.xpath("//*[@id=\"member_menu\"]/div[1]/div/a[3]")).click();
        assertThat(driver.findElement(By.xpath("//*[@id=\"login_frm\"]/table/tbody/tr[1]/th")).getText(), is("Войти через:"));
    }
}
