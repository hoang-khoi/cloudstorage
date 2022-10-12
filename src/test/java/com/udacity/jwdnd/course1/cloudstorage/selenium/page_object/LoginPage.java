package com.udacity.jwdnd.course1.cloudstorage.selenium.page_object;

import lombok.Getter;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

@Getter
public class LoginPage implements PageObject {
    private final WebDriver driver;

    @FindBy(id = "error-msg")
    private WebElement divErrorMessage;

    @FindBy(id = "error-msg")
    private WebElement divLoggedOutMessage;

    public LoginPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    @Override
    public void goToPage(String rootUrl) {
        driver.get(rootUrl + "/login");
    }
}
