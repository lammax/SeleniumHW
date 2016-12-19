package ru.stqa.training.selenium.testtask19.tests;

import org.junit.Test;
import ru.stqa.training.selenium.TestBase;
import ru.stqa.training.selenium.testtask19.app.Application;

public class TestTask19 extends TestBase {

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

        Application app = new Application(driver, wait);

        //open URL
        app.openMainPage();

        //add 3 goods to the bucket
        int nGoodsToAdd = 3;
        app.addNGoodsToCart(nGoodsToAdd);

        //del all goods from bucket
        app.removeAllGoodsFromCart();

        //close browser
        app.quitTest();

        System.out.println("Bucket add products test finished.");
    }


}
