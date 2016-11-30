package ru.stqa.training.selenium;

import com.google.common.base.Strings;
import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;
import java.util.stream.Collectors;

public class TestTask10 extends TestBase {
    //http://localhost/litecart
    @Test
    public void correctProductPageTest() throws InterruptedException {

        System.out.println("Correct Product Page Test started.");

//        1) Открыть главную страницу
        //open url
        driver.get("http://localhost/litecart");
        WebElement firstProduct = driver.findElement(By.cssSelector("div#box-campaigns li:first-child"));
        String productTitle = firstProduct.findElement(By.cssSelector("a:nth-child(1)")).getAttribute("title");
        String regularPrice = firstProduct.findElement(By.cssSelector("a:nth-child(1) div.price-wrapper s")).getAttribute("textContent");
        String campaignPrice = firstProduct.findElement(By.cssSelector("a:nth-child(1) div.price-wrapper strong")).getAttribute("textContent");

//        Кроме того, проверить стили цены на главной странице и на странице товара -- первая цена серая, зачёркнутая, маленькая, вторая цена красная жирная, крупная.
//        Проверка стилей главной страницы
        // зачёркнутая маленькая
        String regularPriceStyle = firstProduct.findElement(By.cssSelector("a:nth-child(1) div.price-wrapper s")).getAttribute("className");
        String regularPriceStyleCorrect = "regular-price";
        Assert.assertTrue("Warning: wrong regular price style! Style = '" + regularPriceStyle + "' but should be = '" + regularPriceStyleCorrect + "'!", regularPriceStyle.equals(regularPriceStyleCorrect));
        //красная жирная крупная
        String campaignPriceStyle = firstProduct.findElement(By.cssSelector("a:nth-child(1) div.price-wrapper strong")).getAttribute("className");
        String campaignPriceStyleCorrect = "campaign-price";
        Assert.assertTrue("Warning: wrong campaign price style! Style = '" + campaignPriceStyle + "' but should be = '" + campaignPriceStyleCorrect + "'!", campaignPriceStyle.equals(campaignPriceStyleCorrect));

//        2) Кликнуть по первому товару в категории Campaigns
        firstProduct.click();

//        3) Проверить, что открывается страница правильного товара
//        Более точно, проверить, что
//        а) совпадает текст названия товара
//        б) совпадает цена (обе цены)
        String productPageTitle = driver.findElement(By.cssSelector("div#box-product h1.title")).getAttribute("textContent");
        Assert.assertTrue("Warning: wrong title! Title = '" + productPageTitle + "' but should be = '" + productTitle + "'!", productPageTitle.equals(productTitle));

        String regularPagePrice = driver.findElement(By.cssSelector("div#box-product div.price-wrapper s")).getAttribute("textContent");
        Assert.assertTrue("Warning: wrong regular price! Price = '" + regularPagePrice + "' but should be = '" + regularPrice + "'!", regularPagePrice.equals(regularPrice));

        String campaignPagePrice = driver.findElement(By.cssSelector("div#box-product div.price-wrapper strong")).getAttribute("textContent");
        Assert.assertTrue("Warning: wrong campaign price! Price = '" + campaignPagePrice + "' but should be = '" + campaignPrice + "'!", campaignPagePrice.equals(campaignPrice));

//        Кроме того, проверить стили цены на главной странице и на странице товара -- первая цена серая, зачёркнутая, маленькая, вторая цена красная жирная, крупная.
//        Проверка стилей страницы продукта
        // зачёркнутая маленькая
        String regularPricePageStyle = driver.findElement(By.cssSelector("div#box-product div.price-wrapper s")).getAttribute("className");
        String regularPricePageStyleCorrect = "regular-price";
        Assert.assertTrue("Warning: wrong regular price product page style! Style = '" + regularPriceStyle + "' but should be = '" + regularPriceStyleCorrect + "'!", regularPriceStyle.equals(regularPriceStyleCorrect));
        //красная жирная крупная
        String campaignPricePageStyle = driver.findElement(By.cssSelector("div#box-product div.price-wrapper strong")).getAttribute("className");
        String campaignPricePageStyleCorrect = "campaign-price";
        Assert.assertTrue("Warning: wrong campaign price product page style! Style = '" + campaignPriceStyle + "' but should be = '" + campaignPriceStyleCorrect + "'!", campaignPriceStyle.equals(campaignPriceStyleCorrect));

        System.out.println("Correct Product Page Test finished.");

    }

}
