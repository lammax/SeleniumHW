package ru.stqa.training.selenium;

import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;

/**
 * Created by user on 28.11.2016.
 */
public class TestTask8 extends TestBase {
    @Test
    public void litecartLoginTest() throws InterruptedException {

        //open url
        driver.get("http://localhost/litecart");

        //get most popular items
        List<WebElement> prodList = driver.findElements(By.cssSelector("div#box-most-popular div.content ul[class $= products] li"));
        checkStickerNums(prodList);

        prodList =  driver.findElements(By.cssSelector("div#box-campaigns div.content ul[class $= products] li"));
        checkStickerNums(prodList);

        prodList =  driver.findElements(By.cssSelector("div#box-latest-products div.content ul[class $= products] li"));
        checkStickerNums(prodList);
    }

    private void checkStickerNums(List<WebElement> wbList) {
        for (WebElement el : wbList) {
            int stickerNums = el.findElements(By.cssSelector("div[class ^= sticker]")).size();
            Assert.assertTrue("Ошибка: количество стикеров = " + Integer.toString(stickerNums), stickerNums == 1);
        }
    }
}
