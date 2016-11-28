package ru.stqa.training.selenium;

import org.junit.Test;
import org.openqa.selenium.By;

public class LitecartLogin extends TestBase {

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

}
