package ru.stqa.training.selenium.testtask19.pages;


import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;


public class ProductPage {

    private WebDriverWait wait = null;

    public ProductPage(WebDriver driver, WebDriverWait wait) {
        this.wait = wait;
        PageFactory.initElements(driver, this);
    }

    @FindBy(css = "form[name=buy_now_form] select[required=required]")
    public List<WebElement> requiredFields;

    @FindBy(css = "form[name=buy_now_form] button[type=submit]")
    public WebElement submitButton;

    @FindBy(css = "div#cart a.content span.quantity")
    public WebElement cartGoodsCounter;


    private void checkRequiredFields() {
        if (requiredFields.size() > 0) {
            for(WebElement oneSelect : requiredFields){
                oneSelect.sendKeys(Keys.ENTER);
                oneSelect.sendKeys(Keys.ARROW_DOWN);
                oneSelect.sendKeys(Keys.ENTER);
            }

        }
    }

    public void addGoodToCart(int nGood) throws InterruptedException {
        checkRequiredFields();
        wait.until(ExpectedConditions.elementToBeClickable(submitButton)).click();
        wait.until(ExpectedConditions.attributeToBe(cartGoodsCounter, "textContent", Integer.toString(nGood + 1)));
        Thread.sleep(1000);
    }


}
