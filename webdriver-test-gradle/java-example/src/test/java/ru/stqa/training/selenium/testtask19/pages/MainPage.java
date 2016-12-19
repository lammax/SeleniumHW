package ru.stqa.training.selenium.testtask19.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;


public class MainPage {

    private WebDriver driver;

    public MainPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public void open() {
//        while( this.driver == null) {}
        this.driver.get("http://localhost/litecart/");
    }

    @FindBy(css = "div#box-most-popular > div.content li")
    public List<WebElement> mostPopularGoods;

    @FindBy(css = "div#cart a.link")
    public WebElement cartLink;

    @FindBy(css = "header#header div#logotype-wrapper a")
    public WebElement callMainPageLink;


    public void chooseAnyMostPopularProduct() {

        mostPopularGoods
                .get((int)(Math.round(Math.random() * (mostPopularGoods.size() - 1))))
                .findElement(By.cssSelector("a.link"))
                .click();

    }

}
