package com.udacity.jwdnd.course1.cloudstorage.selenium.page_object;

import lombok.Getter;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

@Getter
public class SignUpPage implements PageObject {
    private final WebDriver driver;

    @FindBy(id = "inputFirstName")
    private WebElement inputFieldFirstName;

    @FindBy(id = "inputLastName")
    private WebElement inputFieldLastName;

    @FindBy(id = "inputUsername")
    private WebElement inputFieldUsername;

    @FindBy(id = "inputPassword")
    private WebElement inputFieldPassword;

    @FindBy(id = "buttonSignUp")
    private WebElement buttonSubmit;

    @FindBy(id = "success-msg")
    private WebElement divSuccessMessage;

    @FindBy(id = "error-msg")
    private WebElement divErrorMessage;

    public SignUpPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public void goToPage(String rootUrl) {
        driver.get(rootUrl + "/signup");
    }

    public void signUp(String firstName, String lastName, String username, String password) {
        inputFieldFirstName.sendKeys(firstName);
        inputFieldLastName.sendKeys(lastName);
        inputFieldUsername.sendKeys(username);
        inputFieldPassword.sendKeys(password);
        buttonSubmit.submit();
    }
}
