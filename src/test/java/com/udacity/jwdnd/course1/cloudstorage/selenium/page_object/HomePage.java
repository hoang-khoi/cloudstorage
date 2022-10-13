package com.udacity.jwdnd.course1.cloudstorage.selenium.page_object;

import lombok.Getter;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

@Getter
public class HomePage implements PageObject {
    private final WebDriver driver;

    @FindBy(id = "button-logout")
    private WebElement buttonLogout;

    public HomePage(WebDriver webDriver) {
        this.driver = webDriver;
        PageFactory.initElements(driver, this);
    }

    @Override
    public void goToPage(String rootUrl) {
        driver.get(rootUrl);
    }
}
