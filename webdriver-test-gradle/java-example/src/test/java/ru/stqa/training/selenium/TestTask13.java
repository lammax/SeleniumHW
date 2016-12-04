package ru.stqa.training.selenium;

import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;

public class TestTask13 extends TestBase {

    @Test
    public void bucketAddproductsTest() throws InterruptedException {

/*
        Сценарий должен состоять из следующих частей:

        1) открыть страницу какого-нибудь товара
        2) добавить его в корзину
        3) подождать, пока счётчик товаров в корзине обновится
        4) вернуться на главную страницу, повторить предыдущие шаги ещё два раза, чтобы в общей сложности в корзине было 3 единицы товара

        5) открыть корзину (в правом верхнем углу кликнуть по ссылке Checkout)
        6) удалить все товары из корзины один за другим, после каждого удаления подождать, пока внизу обновится таблица
*/

        System.out.println("Bucket add products test started.");

        //open URL
        driver.get("http://localhost/litecart/");

        //add 3 goods to the bucket
        int nGoodsToAdd = 3;
        addNGoodsTotheBucket(nGoodsToAdd);

        //del all goods from bucket
        delAllGoodsFromBucket();

        System.out.println("Bucket add products test finished.");
    }

    private void addNGoodsTotheBucket(int nGoodsToAdd) {
/*
        1) открыть страницу какого-нибудь товара
        2) добавить его в корзину
        3) подождать, пока счётчик товаров в корзине обновится
        4) вернуться на главную страницу, повторить предыдущие шаги ещё два раза, чтобы в общей сложности в корзине было 3 единицы товара
*/

        for(int nGood = 0; nGood < nGoodsToAdd; nGood++) {

            //wail till more than nGoodsToAdd appear on page
            By mostPopularGoodsLocator = By.cssSelector("div#box-most-popular > div.content li");
            List<WebElement> mostPopularGoods = wait.until(ExpectedConditions.numberOfElementsToBeMoreThan(mostPopularGoodsLocator, nGoodsToAdd));

            if (mostPopularGoods != null) {

                //select random one of available goods and click
                mostPopularGoods
                        .get((int)(Math.round(Math.random() * (mostPopularGoods.size() - 1))))
                        .findElement(By.cssSelector("a.link"))
                        .click();

                //add to cart
                //check for required fields, by default lets choose first item
                By requiredFieldsLocator = By.cssSelector("form[name=buy_now_form] select[required=required]");
                List<WebElement> requiredFields = driver.findElements(requiredFieldsLocator);
                if (requiredFields.size() > 0) {
                    for(WebElement oneSelect : requiredFields){
                        oneSelect.sendKeys(Keys.ENTER);
                        oneSelect.sendKeys(Keys.ARROW_DOWN);
                        oneSelect.sendKeys(Keys.ENTER);
                    }

                }

                By cartSubmitButtonLocator = By.cssSelector("form[name=buy_now_form] button[type=submit]");
                wait.until(ExpectedConditions.elementToBeClickable(cartSubmitButtonLocator)).click();

                //wait till cart goods number changed
                By cartNGoodsLocator = By.cssSelector("div#cart a.content span.quantity");
                wait.until(ExpectedConditions.attributeToBe(cartNGoodsLocator, "textContent", Integer.toString(nGood + 1)));
             } else {
                Assert.assertTrue("There are no goods in list!", mostPopularGoods != null);
            }

            //call page with goods
            driver.findElement(By.cssSelector("header#header div#logotype-wrapper a")).click();
        }

    }

    private void delAllGoodsFromBucket() throws InterruptedException {
/*
        5) открыть корзину (в правом верхнем углу кликнуть по ссылке Checkout)
        6) удалить все товары из корзины один за другим, после каждого удаления подождать, пока внизу обновится таблица
*/

        // click on cart
        By cartLocator = By.cssSelector("div#cart a.link");
        wait.until(ExpectedConditions.elementToBeClickable(cartLocator)).click();

        By removeButtonsLocator = By.cssSelector("div.viewport button[name=remove_cart_item]");
        List<WebElement> removeButtonsList = wait.until(ExpectedConditions.numberOfElementsToBeMoreThan(removeButtonsLocator, 0));
        int nGoodsToDel = removeButtonsList.size();

        while(nGoodsToDel > 0) {
            removeButtonsList = wait.until(ExpectedConditions.numberOfElementsToBe(removeButtonsLocator, nGoodsToDel));
            if (removeButtonsList != null) {
                try {
                    wait.until(ExpectedConditions.elementToBeClickable(removeButtonsLocator)).click();
                    nGoodsToDel--;
                } catch (Throwable e) {
                    //seems to be no more elements here =)
                }
            } else {
                nGoodsToDel = 0;
            }
        }

        //call initial page with goods
        driver.get("http://localhost/litecart/");
    }

}
