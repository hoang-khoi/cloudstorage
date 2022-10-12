package com.udacity.jwdnd.course1.cloudstorage.selenium;

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;

public class Helper {
    public static boolean webElementExists(WebElement element) {
        try {
            element.getText();
        } catch (NoSuchElementException _e) {
            return false;
        }

        return true;
    }
}
