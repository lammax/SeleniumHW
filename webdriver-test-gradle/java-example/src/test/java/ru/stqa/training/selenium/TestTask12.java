package ru.stqa.training.selenium;

import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class TestTask12 extends TestBase {

    @Test
    public void newUserRegTest() throws InterruptedException {

/*
        Сделайте сценарий для добавления нового товара (продукта) в учебном приложении litecart (в админке).

        Для добавления товара нужно открыть меню Catalog, в правом верхнем углу нажать кнопку "Add New Product", заполнить поля с информацией о товаре и сохранить.

        Достаточно заполнить только информацию на вкладках General, Information и Prices. Скидки (Campains) на вкладке Prices можно не добавлять.

        После сохранения товара нужно убедиться, что он появился в каталоге (в админке). Клиентскую часть магазина можно не проверять.
*/

        System.out.println("New Product Registration Test started.");

        //open url
        driver.get("http://localhost/litecart/admin/?app=catalog&doc=catalog");

        // login as admin
        adminLogin();

        //go to Add new product page
        driver.findElement(By.cssSelector("td#content a[href$='doc=edit_product']")).click();

        //add new product
        String productName = "product113";
        addNewProduct(productName);

        System.out.println("New Product Registration Test finished.");
    }

    private void addNewProduct(String productName) {

        //General tab
        addGeneralTabData(productName);

        //Information tab
        addInformationTabData();

        //Prices tab
        addPricesTabData();

//        Save product
        driver.findElement(By.cssSelector("td#content button[type=submit][name=save]")).click();

//        Check new product presence
        checkProductPresence(productName);


    }

    private void addGeneralTabData(String productName) {

        driver.findElement(By.cssSelector("td#content ul.index a[href='#tab-general'] ")).click();
        List<WebElement> tabGeneral = driver.findElements(By.cssSelector("td#content div.content div#tab-general > table > tbody > tr"));

        //set Status enabled
        tabGeneral.get(0).findElements(By.cssSelector("input")).get(0).click();

        //set product name
        tabGeneral.get(1).findElements(By.cssSelector("input")).get(0).sendKeys(productName);

        //set product code
        tabGeneral.get(2).findElements(By.cssSelector("input")).get(0).sendKeys(productName + "-123");

        //Categories could be missed, will be some general category

        //set product gender = Unisex
        tabGeneral.get(6).findElements(By.cssSelector("div.input-wrapper tr")).get(3).findElement(By.cssSelector("input")).click();;

        //set product quantity
        tabGeneral.get(7).findElements(By.cssSelector("input")).get(0).sendKeys("1");

        //set product picture
        WebElement input = tabGeneral.get(8).findElements(By.cssSelector("input")).get(0);
        String script = "arguments[0].setAttribute('value', 'C:\\fakepath\\car.jpg')";
//        forgot how to enter this =(
//        .\java-example\src\img\car.jpg
        ((JavascriptExecutor) driver).executeScript(script, input);

        LocalDate ldate = LocalDate.now();
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd.MM.YYYY");

        String dateFrom = dateTimeFormatter.format(ldate);
        String dateTo = dateTimeFormatter.format(ldate.plusDays(10));

        //set product date valid from
        tabGeneral.get(9).findElements(By.cssSelector("input")).get(0).sendKeys(dateFrom);

        //set product date valid to
        tabGeneral.get(10).findElements(By.cssSelector("input")).get(0).sendKeys(dateTo);
    }

    private void addInformationTabData() {

        String script = "";

        driver.findElement(By.cssSelector("td#content ul.index a[href='#tab-information'] ")).click();
        List<WebElement> tabInformation = driver.findElements(By.cssSelector("td#content div.content div#tab-information > table > tbody > tr"));

        //set product Manufacturer
        WebElement manufacturerSelect = tabInformation.get(0).findElement(By.cssSelector("select"));
        script = "arguments[0].selectedIndex = 1";
        ((JavascriptExecutor) driver).executeScript(script, manufacturerSelect);

        //set product Supplier
        WebElement supplierSelect = tabInformation.get(1).findElement(By.cssSelector("select"));
        script = "arguments[0].selectedIndex = 0";
        ((JavascriptExecutor) driver).executeScript(script, manufacturerSelect);

        //set product Keywords
        tabInformation.get(2).findElements(By.cssSelector("input")).get(0).sendKeys("car");

        //set product Short description
        tabInformation.get(3).findElements(By.cssSelector("input")).get(0).sendKeys("some short description for car");

        //set product Long description
        tabInformation.get(4).findElements(By.cssSelector("div.trumbowyg-editor")).get(0).sendKeys("some long description for car");

        //set product Title
        tabInformation.get(5).findElements(By.cssSelector("input")).get(0).sendKeys("Title for car");

        //set product Meta description
        tabInformation.get(6).findElements(By.cssSelector("input")).get(0).sendKeys("meta description for car");

    }

    private void addPricesTabData() {

        String script = "";

        driver.findElement(By.cssSelector("td#content ul.index a[href='#tab-prices'] ")).click();
        List<WebElement> tabPrices = driver.findElements(By.cssSelector("td#content div.content div#tab-prices > table"));

        //set product Purchase Price
        tabPrices.get(0).findElement(By.cssSelector("input")).sendKeys("1");
        WebElement currencySelect = tabPrices.get(0).findElement(By.cssSelector("select"));
        script = "arguments[0].selectedIndex = 1";
        ((JavascriptExecutor) driver).executeScript(script, currencySelect);

        //set product Tax class
        WebElement taxSelect = tabPrices.get(1).findElement(By.cssSelector("select"));
        script = "arguments[0].selectedIndex = 0";
        ((JavascriptExecutor) driver).executeScript(script, taxSelect);

        List<WebElement> priceTRs = tabPrices.get(2).findElements(By.cssSelector("tr"));
        priceTRs.get(1).findElement(By.cssSelector("input[data-type = currency]")).sendKeys("1");
        priceTRs.get(2).findElement(By.cssSelector("input[data-type = currency]")).sendKeys("1");

    }

    private void checkProductPresence(String productName) {

        List<WebElement> catalogItems = driver.findElements(By.cssSelector("form[name=catalog_form] tr.row"));
        int nItems = catalogItems.size();

        WebElement ourProduct = catalogItems.get(nItems - 1);

        boolean isNewProductInItemsList = ourProduct.findElements(By.cssSelector("td")).get(2).findElement(By.cssSelector("a")).getAttribute("textContent").equals(productName);
        Assert.assertTrue("there is no our new product in the products items list!", isNewProductInItemsList);

    }

}
