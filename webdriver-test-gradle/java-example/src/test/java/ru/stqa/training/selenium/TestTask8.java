package ru.stqa.training.selenium;

import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

/**
 * Created by user on 28.11.2016.
 */
public class TestTask8 extends TestBase {
    @Test
    public void litecartLoginTest() throws InterruptedException {

        //open url
        driver.get("http://localhost/litecart");

        Collection<String> boxNames = Arrays.asList("box-most-popular", "box-campaigns", "box-latest-products");

        for(String boxName : boxNames) {
            checkStickerNums(boxName);
        }

    }

    private void checkStickerNums(String boxName) {
        System.out.println("Checking - " + boxName);
        List<WebElement> prodList = driver.findElements(By.cssSelector("div#" + boxName + " div.content ul[class $= products] li"));
        for (WebElement el : prodList) {
            int stickerNums = el.findElements(By.cssSelector("div[class ^= sticker]")).size();
            Assert.assertTrue("Ошибка: количество стикеров = " + Integer.toString(stickerNums), stickerNums == 1);
        }
    }
}
