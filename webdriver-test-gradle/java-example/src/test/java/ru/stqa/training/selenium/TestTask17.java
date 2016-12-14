package ru.stqa.training.selenium;

import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.logging.LogEntry;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;
import java.util.logging.Level;

public class TestTask17 extends TestBase {

    @Test
    public void bucketAddproductsTest() throws InterruptedException {

/*
       Сделайте сценарий, который проверяет, не появляются ли сообщения об ошибках при открытии страниц в учебном приложении, а именно -- страниц товаров в каталоге в административной панели.

        Сценарий должен состоять из следующих частей:

        1) зайти в админку
        2) открыть каталог, категорию, которая содержит товары (страница http://localhost/litecart/admin/?app=catalog&doc=catalog&category_id=1)
        3) последовательно открывать страницы товаров и проверять, не появляются ли в логе браузера сообщения об ошибках (любого уровня критичности)

*/

        System.out.println("Product page errors test started.");

//        1) зайти в админку
//        2) открыть каталог, категорию, которая содержит товары (страница http://localhost/litecart/admin/?app=catalog&doc=catalog&category_id=1)
        //open URL
        driver.get("http://localhost/litecart/admin/?app=catalog&doc=catalog&category_id=1");
        //login as admin
        adminLogin();

//        3) последовательно открывать страницы товаров и проверять, не появляются ли в логе браузера сообщения об ошибках (любого уровня критичности)
        checkProductPagesErrors();

        System.out.println("Product page errors test finished.");
    }

    private void checkProductPagesErrors() throws InterruptedException {
//        3) последовательно открывать страницы товаров и проверять, не появляются ли в логе браузера сообщения об ошибках (любого уровня критичности)

        //click Subcategory
        By productCategoriesLocator = By.cssSelector("form[name=catalog_form] tr.row");
        By linkLocator = By.cssSelector("td:nth-child(3) a");
        wait.until(ExpectedConditions.numberOfElementsToBeMoreThan(productCategoriesLocator, 3))
                .get(2)
                .findElement(linkLocator)
                .click();

        //get all needed products
        List<WebElement> allNeededProducts = wait.until(ExpectedConditions.numberOfElementsToBeMoreThan(productCategoriesLocator, 3));

        //now lets check all products except first 3
        for(int nprod = 3; nprod < allNeededProducts.size(); nprod++) {
            wait.until(ExpectedConditions.numberOfElementsToBeMoreThan(productCategoriesLocator, 3))
                    .get(nprod)
                    .findElement(linkLocator)
                    .click();

            //lets check log warnings
            driver.manage()
                    .logs()
                    .get("browser")
                    .filter(Level.WARNING)
                    .forEach(logEntry -> System.out.println(logEntry));


            driver.navigate().back();
        }

        System.out.println("checkProductPagesErrors");
    }

}
