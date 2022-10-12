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

    @FindBy(id = "inputUsername")
    private WebElement inputUsername;

    @FindBy(id = "inputPassword")
    private WebElement inputPassword;

    @FindBy(id = "login-button")
    private WebElement buttonSubmit;

    public LoginPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public void login(String username, String password) {
        inputUsername.sendKeys(username);
        inputPassword.sendKeys(password);
        buttonSubmit.submit();
    }

    @Override
    public void goToPage(String rootUrl) {
        driver.get(rootUrl + "/login");
    }
}
