package ru.stqa.training.selenium.testtask19.app;


import org.junit.Assert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import ru.stqa.training.selenium.testtask19.pages.CartPage;
import ru.stqa.training.selenium.testtask19.pages.MainPage;
import ru.stqa.training.selenium.testtask19.pages.ProductPage;

public class Application {

    private WebDriver driver = null;
    private WebDriverWait wait = null;

    private CartPage cartPage = null;
    private MainPage mainPage = null;
    private ProductPage productPage = null;

    public Application(WebDriver driver, WebDriverWait wait) {

        this.driver = driver;
        this.wait = wait;

        cartPage = new CartPage(driver, wait);
        mainPage = new MainPage(driver);
        productPage = new ProductPage(driver, wait);

    }


    public void openMainPage() {
        //call main page with goods
        mainPage.open();
    }

    public void quitTest() {
        this.wait = null;
        this.driver.quit();
        this.driver = null;
    }

    public void addNGoodsToCart(int nGoodsToAdd) throws InterruptedException {

        for(int nGood = 0; nGood < nGoodsToAdd; nGood++) {

            if (mainPage.mostPopularGoods != null) {
                mainPage.chooseAnyMostPopularProduct();
                productPage.addGoodToCart(nGood);
            } else {
                Assert.assertTrue("There are no goods in list!", mainPage.mostPopularGoods != null);
            }

            mainPage.callMainPageLink.click();

        }

    }

    public void removeAllGoodsFromCart() throws InterruptedException {

        cartPage.open(mainPage.cartLink);
        cartPage.removeAllGoodsFromCart();
        mainPage.callMainPageLink.click();

        //Thread.sleep-s are added for better test visual control, could be removed without any worries
        Thread.sleep(2000);

    }

}
