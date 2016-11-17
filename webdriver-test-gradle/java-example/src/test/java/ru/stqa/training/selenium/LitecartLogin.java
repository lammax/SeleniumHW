package ru.stqa.training.selenium;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class LitecartLogin {

    private WebDriver driver;

    @Before
    public void start() {
        driver = new ChromeDriver();
    }

    @Test
    public void litecartLoginTest() throws InterruptedException {
        driver.get("http://localhost/litecart/");
        driver.findElement(By.name("email")).sendKeys("mickey@mouse.com");
        driver.findElement(By.name("password")).sendKeys("mickey@mouse.com");
        driver.findElement(By.name("remember_me")).click();
        driver.findElement(By.name("login")).click();
        System.out.println("Test complete. Waiting 10 seconds.");
        Thread.sleep(10 * 1000);
    }

    @After
    public void stop() {
        driver.quit();
        driver = null;
    }

}
