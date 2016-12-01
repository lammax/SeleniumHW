package ru.stqa.training.selenium;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;

import java.util.List;
import java.util.stream.Collectors;

public class TestTask11 extends TestBase {

    private static final String captchaPattern = "CAPTCHA";

    @Test
    public void newUserRegTest() throws InterruptedException {

        System.out.println("New User Registration Test started.");

        //open url
        driver.get("http://localhost/litecart/admin/?app=settings&doc=security");

        // login as admin
        adminLogin();

        //Turn off CAPTCHA
        turnOffCAPTCHA();

        //go to home page
        driver.findElement(By.cssSelector("td#sidebar div.header a[title = Catalog]")).click();

        //add new user
        String loginName = "userLmx";
        String loginEmail = "emailbox@emailserver.com";
        String loginPassword = "userLmxPswd";
        addNewUser(loginEmail, loginName, loginPassword);

        //logout 1
        userLogout();

        //login with new user
        userLogin(loginEmail, loginPassword, false);

        //logout 2
        userLogout();

        System.out.println("New User Registration Test finished.");
    }

    private void turnOffCAPTCHA() {
        //click on pencil
        driver.findElements(By.cssSelector("form[name = settings_form] tr.row"))
                .stream()
                .filter(elt -> isCaptcha(elt))
                .collect(Collectors.toList())
                .get(0)
                .findElement(By.cssSelector("td a[title = Edit]"))
                .click();

        //choose False & click Save
        WebElement captchaRow = driver.findElements(By.cssSelector("form[name = settings_form] tr.row"))
                .stream()
                .filter(elt -> isCaptchaEdit(elt))
                .collect(Collectors.toList())
                .get(0);
        captchaRow.findElements(By.cssSelector("td")).get(1).findElements(By.cssSelector("label")).get(1).click();
        captchaRow.findElement(By.cssSelector("td button[type = submit]")).click();

    }

    private boolean isCaptcha(WebElement el) {
        return el.findElements(By.cssSelector("td")).get(0).getAttribute("textContent").equals(captchaPattern);
    }

    private boolean isCaptchaEdit(WebElement el) {
        return el.findElements(By.cssSelector("td")).get(0).getAttribute("textContent").startsWith(captchaPattern);
    }

    private void addNewUser(String email, String username, String password) {
        driver.findElement(By.cssSelector("form[name = login_form] a[href $= 'create_account']")).click();
        List<WebElement> userInfDataRows = driver.findElements(By.cssSelector("form[name = customer_form] tr"));

        //fill TaxID & Company
        userInfDataRows.get(0).findElement(By.cssSelector("td input[name='tax_id']")).sendKeys("123456789");
        userInfDataRows.get(0).findElement(By.cssSelector("td input[name='company']")).sendKeys("C@C0");

        //fill first & last names
        userInfDataRows.get(1).findElement(By.cssSelector("td input[name='firstname']")).sendKeys(username);
        userInfDataRows.get(1).findElement(By.cssSelector("td input[name='lastname']")).sendKeys("User Last Name");

        //fill addresses 1 & 2
        userInfDataRows.get(2).findElement(By.cssSelector("td input[name='address1']")).sendKeys("Address 1");
        userInfDataRows.get(2).findElement(By.cssSelector("td input[name='address2']")).sendKeys("Address 2");

        //fill postcode & city
        userInfDataRows.get(3).findElement(By.cssSelector("td input[name='postcode']")).sendKeys("655000");
        userInfDataRows.get(3).findElement(By.cssSelector("td input[name='city']")).sendKeys("Abakan");

        //fill country - Russian Federation
        userInfDataRows.get(4).findElement(By.cssSelector("td span.select2-selection__arrow")).click();
        WebElement searchCountryField = driver.findElement(By.cssSelector("input.select2-search__field"));
        searchCountryField.sendKeys("russia");
        searchCountryField.sendKeys(Keys.ENTER);

        //fill email & phone
        userInfDataRows.get(5).findElement(By.cssSelector("td input[name='email']")).sendKeys(email);
        userInfDataRows.get(5).findElement(By.cssSelector("td input[name='phone']")).sendKeys("+79137777777");

        //skip Subscribe newsletter

        //fill desiredpassword & confirmpassword
        userInfDataRows.get(7).findElement(By.cssSelector("td input[name='password']")).sendKeys(password);
        userInfDataRows.get(7).findElement(By.cssSelector("td input[name='confirmed_password']")).sendKeys(password);

        //click Create Account
        userInfDataRows.get(8).findElement(By.cssSelector("button[type='submit']")).click();

    }

}