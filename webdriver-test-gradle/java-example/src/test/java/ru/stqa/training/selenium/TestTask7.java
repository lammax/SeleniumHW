package ru.stqa.training.selenium;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;

public class TestTask7 extends TestBase {
    @Test
    public void litecartLoginTest() throws InterruptedException {

        //open url
        driver.get("http://localhost/litecart/admin");

        //login
        driver.findElement(By.cssSelector("div.content input[name=username]")).sendKeys("admin");
        driver.findElement(By.cssSelector("div.content input[name=password]")).sendKeys("admin");
        driver.findElement(By.cssSelector("form[name=login_form] button[name=login]")).click();

        //get list items
        int listSize = driver.findElements(By.cssSelector("ul#box-apps-menu li")).size();

        for(int elIndex = 0; elIndex <= listSize - 1; elIndex++) {
            WebElement el = driver.findElements(By.xpath("*//ul[@id='box-apps-menu']/li")).get(elIndex);
            String elName = el.getText();
            el.click();

            //get sub-list items
            int subListSize = driver.findElements(By.cssSelector("ul#box-apps-menu li.selected ul.docs li")).size();
            if (subListSize > 0) {
                for(int subElIndex = 0; subElIndex <= subListSize - 1; subElIndex++) {
                    WebElement subEl = driver.findElements(By.cssSelector("ul#box-apps-menu li.selected ul.docs li")).get(subElIndex);
                    String subElName = subEl.getText();
                    subEl.click();
                    System.out.println("Проверяем наличие заголовка у пункта меню " + subElName + ": " + Boolean.toString(isElementPresent(By.cssSelector("h1"))));
                }
            } else {
                System.out.println("У пункта списка " + elName + " под-элементов не найдено.");
            }
        }

    }
}
