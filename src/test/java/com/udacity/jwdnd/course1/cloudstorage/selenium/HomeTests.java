package com.udacity.jwdnd.course1.cloudstorage.selenium;

import com.udacity.jwdnd.course1.cloudstorage.selenium.helper.Helper;
import com.udacity.jwdnd.course1.cloudstorage.selenium.page_object.HomePage;
import com.udacity.jwdnd.course1.cloudstorage.selenium.page_object.LoginPage;
import com.udacity.jwdnd.course1.cloudstorage.selenium.page_object.SignUpPage;
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
public class HomeTests {
    private static final String FIRST_NAME = "Hoang";
    private static final String LAST_NAME = "Khoi";
    private static final String USERNAME = "khoi";
    private static final String PASSWORD = "12345678";

    @LocalServerPort
    private int port;

    private WebDriver driver;

    private HomePage homePage;

    private LoginPage loginPage;

    @BeforeAll
    static void beforeAll() {
        WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
    void setUp() {
        driver = new ChromeDriver();
        loginPage = new LoginPage(driver);
        homePage = new HomePage(driver);
        SignUpPage signUpPage = new SignUpPage(driver);

        signUpPage.goToPage(Helper.getTestUrl(port));
        signUpPage.signUp(FIRST_NAME, LAST_NAME, USERNAME, PASSWORD);

        loginPage.goToPage(Helper.getTestUrl(port));
        loginPage.login(USERNAME, PASSWORD);
    }

    @AfterEach
    void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Test
    void testLogout_ReturnedToLoginWithMessageBanner() {
        homePage.goToPage(Helper.getTestUrl(port));
        homePage.getButtonLogout().submit();

        assertTrue(Helper.webElementExists(loginPage.getDivLoggedOutMessage()));
        assertFalse(Helper.webElementExists(loginPage.getDivErrorMessage()));

        assertEquals("You have been logged out", loginPage.getDivLoggedOutMessage().getText());
    }
}
