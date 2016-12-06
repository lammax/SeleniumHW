package ru.stqa.training.selenium;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;
import java.util.Set;

public class TestTask14 extends TestBase {

    @Test
    public void newWindowTest() throws InterruptedException {

/*
        Сделайте сценарий, который проверяет, что ссылки на странице редактирования страны открываются в новом окне.

        Сценарий должен состоять из следующих частей:

        1) зайти в админку
        2) открыть пункт меню Countries (или страницу http://localhost/litecart/admin/?app=countries&doc=countries)
        3) открыть на редактирование какую-нибудь страну или начать создание новой
        4) возле некоторых полей есть ссылки с иконкой в виде квадратика со стрелкой -- они ведут на внешние страницы и открываются в новом окне, именно это и нужно проверить.

        Конечно, можно просто убедиться в том, что у ссылки есть атрибут target="_blank". Но в этом упражнении требуется именно кликнуть по ссылке, чтобы она открылась в новом окне, потом переключиться в новое окно, закрыть его, вернуться обратно, и повторить эти действия для всех таких ссылок.

        Не забудьте, что новое окно открывается не мгновенно, поэтому требуется ожидание открытия окна.
*/

        System.out.println("New window test started.");

        //open URL
        driver.get("http://localhost/litecart/admin/?app=countries&doc=countries");

        // login as admin
        adminLogin();

        //open any country for editing
        openAnyCountry();

        //check if new windows are appearing
        checkNewWindows();

        System.out.println("New window test finished.");
    }

    private void openAnyCountry() {
        By countriesLocator = By.cssSelector("form[name=countries_form] tr.row");
        By editLinkLocator = By.cssSelector("td:last-child a[title=Edit]");

        List<WebElement> countriesRows = wait.until(ExpectedConditions.numberOfElementsToBeMoreThan(countriesLocator, 0));

        if (countriesRows != null) {
            countriesRows
                    .get((int)(Math.round(Math.random() * (countriesRows.size() - 1))))
                    .findElement(editLinkLocator)
                    .click();
        } else {
            throw new RuntimeException();
        }

    }

    private void checkNewWindows() throws InterruptedException {
        //get current window
        String originalWindow = driver.getWindowHandle();
        Set<String> existingWindows = driver.getWindowHandles();

        //get all external links
        List<WebElement> externalLinks = driver.findElements(By.cssSelector("td#content > form > table a[target='_blank']"));
        int nExtLinks = externalLinks.size();

        //in cycle lets click every external link, switch to new window, then close it and come back to our original window
        for(int iExtLink = 0; iExtLink < nExtLinks; iExtLink++) {
            driver.findElements(By.cssSelector("td#content > form > table a[target='_blank']")).get(iExtLink).click();
            String newWindow = wait.until(anyWindowOtherThan(existingWindows));
            driver.switchTo().window(newWindow);
            Thread.sleep(1000);
            driver.close();
            driver.switchTo().window(originalWindow);
            Thread.sleep(1000);
        }


    }

}
