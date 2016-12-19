package ru.stqa.training.selenium.testtask19.pages;


import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

public class CartPage {

    private WebDriverWait wait = null;
    private int nGoodsToDel;

    public CartPage(WebDriver driver, WebDriverWait wait) {
        this.wait = wait;
        nGoodsToDel = 0;
        PageFactory.initElements(driver, this);
    }



    @FindBy(css = "div.viewport button[name=remove_cart_item]")
    public List<WebElement> removeButtonsList;

    public void open(WebElement cartLink) {
        cartLink.click();
    }

    private void deleteAnyProductFromCart(CartPage currCart) {

        if (removeButtonsList != null) {
            try {
                wait.until(ExpectedConditions.elementToBeClickable(removeButtonsList.get(0))).click();
                currCart.nGoodsToDel--;
                Thread.sleep(1 * 1000);
            } catch (Throwable e) {
                //seems to be no more elements here =)
            }
        } else {
            currCart.nGoodsToDel = 0;
        }

    }

    public void removeAllGoodsFromCart() {

        this.nGoodsToDel = this.removeButtonsList.size();

        while(this.nGoodsToDel > 0) {
            this.deleteAnyProductFromCart(this);
        }
    }


}