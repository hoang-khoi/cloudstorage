package com.udacity.jwdnd.course1.cloudstorage.selenium;

import com.udacity.jwdnd.course1.cloudstorage.selenium.page_object.LoginPage;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class LoginTests {
    private WebDriver driver;
    private LoginPage loginPage;

    @LocalServerPort
    private int port;

    @BeforeAll
    static void beforeAll() {
        WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
    void setUp() {
        driver = new ChromeDriver();
        loginPage = new LoginPage(driver);
    }

    @AfterEach
    void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Test
    void testDisplaySignupPage() {
        loginPage.goToPage(getTestUrl());

        assertFalse(Helper.webElementExists(loginPage.getDivErrorMessage()));
        assertFalse(Helper.webElementExists(loginPage.getDivSuccessMessage()));
    }

    @Test
    void testSubmitSignup_Success() {
        loginPage.goToPage(getTestUrl());
        loginPage.signUp("Johny", "Bravo", "johny_bravo", "12345678");

        assertFalse(Helper.webElementExists(loginPage.getDivErrorMessage()));
        assertTrue(Helper.webElementExists(loginPage.getDivSuccessMessage()));
        assertEquals("You successfully signed up! Please continue to the login page.", loginPage.getDivSuccessMessage().getText());
    }

    @Test
    void testSubmitSignup_WeakPassword_ErrorBannerDisplayed() {
        loginPage.goToPage(getTestUrl());
        loginPage.signUp("Johny", "Bravo", "johny_bravo", "1234567");

        assertTrue(Helper.webElementExists(loginPage.getDivErrorMessage()));
        assertFalse(Helper.webElementExists(loginPage.getDivSuccessMessage()));
        assertEquals("Weak password.", loginPage.getDivErrorMessage().getText());
    }

    private String getTestUrl() {
        return String.format("http://localhost:%d", port);
    }
}
