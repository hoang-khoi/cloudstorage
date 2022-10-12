package com.udacity.jwdnd.course1.cloudstorage.selenium.helper;

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
    public static String getTestUrl(int port) {
        return String.format("http://localhost:%d", port);
    }
}
