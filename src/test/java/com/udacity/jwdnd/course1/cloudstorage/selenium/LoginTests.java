package com.udacity.jwdnd.course1.cloudstorage.selenium;

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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class LoginTests {
    private static final String FIRST_NAME = "Hoang";
    private static final String LAST_NAME = "Khoi";
    private static final String USERNAME = "khoi";
    private static final String PASSWORD = "12345678";
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
        SignUpPage signUpPage = new SignUpPage(driver);

        signUpPage.goToPage(Helper.getTestUrl(port));
        signUpPage.signUp(FIRST_NAME, LAST_NAME, USERNAME, PASSWORD);
    }

    @AfterEach
    void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Test
    void testLoginPage() {
        loginPage.goToPage(Helper.getTestUrl(port));

        assertFalse(Helper.webElementExists(loginPage.getDivErrorMessage()));
        assertFalse(Helper.webElementExists(loginPage.getDivLoggedOutMessage()));
    }

    @Test
    void testLoginPage_Login_Success() {
        loginPage.goToPage(Helper.getTestUrl(port));
        loginPage.login(USERNAME, PASSWORD);

        assertEquals(Helper.getTestUrl(port) + "/", driver.getCurrentUrl());
        assertEquals("Home", driver.getTitle());
    }
}
