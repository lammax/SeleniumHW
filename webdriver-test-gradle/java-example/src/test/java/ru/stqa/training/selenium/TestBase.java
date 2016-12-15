package ru.stqa.training.selenium;


import org.assertj.core.api.SoftAssertions;
import org.junit.ClassRule;
import org.junit.rules.ExternalResource;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.Set;

public class TestBase {

    protected static WebDriver driver = null;
    protected static WebDriverWait wait = null;
    protected static SoftAssertions sftA = null;
    protected static Proxy proxy = null;

    @ClassRule
    public static ExternalResource driverRule = new ExternalResource() {
        @Override
        protected void before() throws Throwable {
            System.out.println("Starting Chrome browser");

            proxy = new Proxy();
            proxy.setHttpProxy("localhost:8090");

            DesiredCapabilities capabilities = new DesiredCapabilities();
            capabilities.setCapability(CapabilityType.PROXY, proxy);

            driver = new ChromeDriver(capabilities);
            wait = new WebDriverWait(driver, 10);
            sftA = new SoftAssertions();
        }

        @Override
        protected void after() {
            System.out.println("Stopping Chrome browser");
            if (driver != null) {
                driver.quit();
                driver = null;
                wait = null;
                proxy = null;
                sftA.assertAll();
            }
        }
    };


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

    public ExpectedCondition<String> anyWindowOtherThan(Set<String> oldWindows) {
        return input -> {
            Set<String> handles = driver.getWindowHandles();
            handles.removeAll(oldWindows);
            return handles.size() > 0 ? handles.iterator().next() : null;
        };
    }

}
