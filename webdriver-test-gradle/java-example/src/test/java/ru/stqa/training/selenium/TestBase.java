package ru.stqa.training.selenium;


import org.junit.After;
import org.junit.Before;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

public class TestBase {

    public static ThreadLocal<WebDriver> tlDriver = new ThreadLocal<>();
    public WebDriver driver;
    public WebDriverWait wait;

    public boolean isElementPresent (By locator) {
        try {
            wait.until((WebDriver d) -> d.findElement(locator));
            return true;
        } catch (TimeoutException ex) {
            return false;
        }
    }

    public void adminLogin() {
        driver.findElement(By.cssSelector("div.content input[name=username]")).sendKeys("admin");
        driver.findElement(By.cssSelector("div.content input[name=password]")).sendKeys("admin");
        driver.findElement(By.cssSelector("form[name=login_form] button[name=login]")).click();
    }

    public void userLogin(String email, String password, boolean isRememberMe) {
        driver.findElement(By.name("email")).sendKeys(email);
        driver.findElement(By.name("password")).sendKeys(password);
        if (isRememberMe) driver.findElement(By.name("remember_me")).click();
        driver.findElement(By.name("login")).click();
    }

    public void userLogout() {
        driver.findElement(By.cssSelector("div#box-account a[href $= logout]")).click();
    }

    public void unhideElement(WebDriver driver, WebElement element) {
        String script = "arguments[0].style.opacity=1;"
                + "arguments[0].style['transform']='translate(0px, 0px) scale(1)';"
                + "arguments[0].style['MozTransform']='translate(0px, 0px) scale(1)';"
                + "arguments[0].style['WebkitTransform']='translate(0px, 0px) scale(1)';"
                + "arguments[0].style['msTransform']='translate(0px, 0px) scale(1)';"
                + "arguments[0].style['OTransform']='translate(0px, 0px) scale(1)';"
                + "return true;";
        ((JavascriptExecutor) driver).executeScript(script, element);
    }

    @Before
    public void start() {
        if (tlDriver.get() != null) {
            driver = tlDriver.get();
            wait = new WebDriverWait(driver, 10);
            return;
        }
        driver = new ChromeDriver();
//        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        tlDriver.set(driver);
//        System.out.println(((HasCapabilities) driver).getCapabilities());
        wait = new WebDriverWait(driver, 10);

        Runtime.getRuntime().addShutdownHook(
                new Thread(() -> { driver.quit(); driver = null; }));
    }

    @After
    public void stop() {
        //driver.quit();
        //driver = null;
    }

}
