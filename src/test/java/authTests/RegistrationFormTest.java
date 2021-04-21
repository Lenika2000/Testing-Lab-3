package authTests;

import org.junit.*;

import static org.junit.Assert.*;


import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import util.Init;

import java.util.concurrent.TimeUnit;

public class RegistrationFormTest {
    private static WebDriver driver;

    @Before
    public void setUp() {
        driver = (new Init()).initWebDriver();
        driver.findElement(By.xpath("//a[contains(text(),\'Зарегистрируйтесь сейчас!\')]")).click();
        // Даю согласие на обработку персональных данных
        driver.findElement(By.xpath("//input[@id=\'i_undertake_conditions\']")).click();
    }

    public void fillDefaultForm() {
        driver.findElement(By.xpath("//input[@id=\'signup_login\']")).click();
        driver.findElement(By.xpath("//input[@id=\'signup_login\']")).sendKeys("lena123");
        driver.findElement(By.xpath("//input[@id=\'password\']")).click();
        driver.findElement(By.xpath("//input[@id=\'password\']")).sendKeys("123456");
        driver.findElement(By.xpath("//input[@id=\'re_password\']")).click();
        driver.findElement(By.xpath("//input[@id=\'re_password\']")).sendKeys("123456");
        driver.findElement(By.xpath("//input[@id=\'signup_email\']")).click();
        driver.findElement(By.xpath("//input[@id=\'signup_email\']")).sendKeys("Lenika10000@mail.ru");
        driver.findElement(By.xpath("//input[@id=\'signup_nickname\']")).click();
        driver.findElement(By.xpath("//input[@id=\'signup_nickname\']")).sendKeys("lena");
    }

    @After
    public void tearDown() {
        driver.quit();
    }

    @Test
    public void testRequiredFields() {
        driver.findElement(By.id("regin_btn")).click();
        assertEquals("err_message", driver.findElement(By.xpath("//input[@id=\'signup_login\']")).getAttribute("class"));
        assertEquals("err_message", driver.findElement(By.xpath("//input[@id=\'password\']")).getAttribute("class"));
        assertEquals("err_message", driver.findElement(By.xpath("//input[@id=\'re_password\']")).getAttribute("class"));
        assertEquals("err_message", driver.findElement(By.xpath("//input[@id=\'signup_email\']")).getAttribute("class"));
        // При всех пустых полях сообщение о незаполненности поля отображаемое имя не выводится
//        assertEquals(driver.findElement(By.xpath("//input[@id=\'signup_nickname\']")).getAttribute("class"), "err_message");
    }

    // не проходит из-за капчи
    @Ignore
    @Test
    public void testEnteredLoginAlreadyInUse() {
        fillDefaultForm();
        // введен уже использующийся логин lena123
        driver.findElement(By.id("regin_btn")).click();
//        driver.manage().timeouts().implicitlyWait(100, TimeUnit.SECONDS);
        String errMsg = driver.findElement(By.xpath("//div[contains(@class, 'error_message')][2]")).getText();
        assertEquals("The entered login ('lena123') is already in use. Please, choose another login.", errMsg);
    }

    @Test
    public void testWrongReenteredPassword() {
        fillDefaultForm();
        // дописываем к паролю еще символы
        driver.findElement(By.xpath("//input[@id=\'password\']")).click();
        driver.findElement(By.xpath("//input[@id=\'password\']")).sendKeys("123456");
        driver.findElement(By.xpath("//input[@id=\'re_password\']")).click();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        String errMsg = driver.findElement(By.xpath("//label[contains(@class, 'err_message')]")).getText();
        assertEquals("Ваш пароль и повторно введенный пароль должны быть одинаковыми", errMsg);
    }

    @Test
    public void testIncorrectEmail() {
        driver.findElement(By.xpath("//input[@id=\'signup_email\']")).click();
        driver.findElement(By.xpath("//input[@id=\'signup_email\']")).sendKeys("Lenika10000mail.ru");
        driver.findElement(By.xpath("//input[@id=\'signup_nickname\']")).click();

        String errMsg = driver.findElement(By.xpath("//label[contains(@class, 'err_message')]")).getText();
        assertEquals("Вероятно, введен неправильный адрес электронной почты", errMsg);
    }

}
