package ru.stqa.training.selenium;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;
import java.util.stream.Collectors;

import com.google.common.base.Strings;

public class TestTask9 extends TestBase {
    @Test
    public void countriesAlphabetTest() throws InterruptedException {

        System.out.println("Countries Alphabet Test started.");

        //open url
        driver.get("http://localhost/litecart/admin/?app=countries&doc=countries");

        // login as admin
//        adminLogin();

        List<WebElement> countriesList = driver.findElements(By.cssSelector("form[name = countries_form] tr.row"));
        int nCountries = countriesList.size();

        List<String> countryNamesList = countriesList.stream()
                .map(elt -> getCountryName(elt, 4))
                .collect(Collectors.toList());

        //print all countries names
//        countryNamesList.stream().map(elt -> System.out.println(elt));

        //check if coutries names list sorted
        System.out.println(
                "Is countries list sorted: " +
                        Boolean.toString(isListSorted(countryNamesList))
        );

        // check for each coutry if appropriate zones are sorted
        for (int countryIndex = 0; countryIndex < nCountries; countryIndex++) {

            driver.findElement(By.cssSelector("ul#box-apps-menu a[href*='?app=countries&doc=countries']")).click();
            driver.findElements(By.cssSelector("form[name = countries_form] tr.row")).get(countryIndex)
                    .findElement(By.cssSelector("td:nth-child(5) a"))
                    .click();
            List<String> zonesNamesList = driver
                    .findElements(By.cssSelector("table#table-zones tr:not(.header)"))
                    .stream()
                    .map(elt -> getCountryZoneName(elt))
                    .filter(elt -> !Strings.isNullOrEmpty(elt))
                    .collect(Collectors.toList());

            //check if zones names list sorted
            if (zonesNamesList.size() > 0) {
                System.out.println(zonesNamesList);
                System.out.println(
                        countryNamesList.get(countryIndex)
                                + ": is zones list sorted: "
                                + Boolean.toString(isListSorted(zonesNamesList))
                );
            } else {
//                System.out.println(countryNamesList.get(countryIndex) + " has no zones.");
            }

        }

        System.out.println("Countries Alphabet Test finished.");

    }

    @Test
    public void geoZonesAlphabetTest() throws InterruptedException {

        System.out.println("GeoZones Alphabet Test started.");

        //open url
        driver.get("http://localhost/litecart/admin/?app=geo_zones&doc=geo_zones");

        // login as admin
        adminLogin();

        List<WebElement> countriesList = driver.findElements(By.cssSelector("form[name = geo_zones_form] tr.row"));
        int nCountries = countriesList.size();

        List<String> countryNamesList = countriesList.stream()
                .map(elt -> getCountryName(elt, 2))
                .collect(Collectors.toList());

        for (int countryIndex = 0; countryIndex < nCountries; countryIndex++) {
            //click GeoZones
            driver.findElement(By.cssSelector("ul#box-apps-menu a[href $= 'doc=geo_zones']")).click();
            //click country
            driver.findElements(By.cssSelector("form[name = geo_zones_form] tr.row")).get(countryIndex)
                    .findElement(By.cssSelector("td:nth-child(3) a"))
                    .click();

            List<String> zonesNamesList = driver
                    .findElements(By.cssSelector("table#table-zones tr:not(.header):not(.colspan)"))
                    .stream()
                    .map(elt -> getGeoZoneName(elt))
                    .filter(elt -> !Strings.isNullOrEmpty(elt))
                    .collect(Collectors.toList());

            //check if zones names list sorted
            if (zonesNamesList.size() > 0) {
                System.out.println(zonesNamesList);
                System.out.println(
                        countryNamesList.get(countryIndex)
                                + ": is zones list sorted: "
                                + Boolean.toString(isListSorted(zonesNamesList))
                );
            } else {
                System.out.println(countryNamesList.get(countryIndex) + " has no zones.");
            }
        }

        System.out.println("GeoZones Alphabet Test finished.");

    }

    private String getCountryName(WebElement el, int tdN) {
        return el
                .findElements(By.cssSelector("td"))
                .get(tdN)
                .findElement(By.cssSelector("a"))
                .getAttribute("textContent");
    }

    private String getZoneNameCustom(WebElement el, String selector, String attribute) {
        List<WebElement> tdList = el.findElements(By.cssSelector("td"));
        if (tdList.size() >= 3) {
            return tdList
                    .get(2)
                    .findElement(By.cssSelector(selector))
                    .getAttribute(attribute);
        } else {
            return null;
        }
    }

    private String getCountryZoneName(WebElement el) {
        return getZoneNameCustom(el, "input", "value");
    }

    private String getGeoZoneName(WebElement el) {
        return getZoneNameCustom(el, "option[selected=selected]", "textContent");

    }

    private boolean isListSorted(List<String> list) {
        return list
                .stream()
                .sorted()
                .collect(Collectors.toList())
                .equals(list);
    }

}
