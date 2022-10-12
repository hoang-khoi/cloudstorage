package com.udacity.jwdnd.course1.cloudstorage.selenium;

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
public class SignUpTests {
    private WebDriver driver;
    private SignUpPage signUpPage;

    @LocalServerPort
    private int port;

    @BeforeAll
    static void beforeAll() {
        WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
    void setUp() {
        driver = new ChromeDriver();
        signUpPage = new SignUpPage(driver);
    }

    @AfterEach
    void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Test
    void testDisplaySignupPage() {
        signUpPage.goToPage(Helper.getTestUrl(port));

        assertFalse(Helper.webElementExists(signUpPage.getDivErrorMessage()));
        assertFalse(Helper.webElementExists(signUpPage.getDivSuccessMessage()));
    }

    @Test
    void testSubmitSignup_Success() {
        signUpPage.goToPage(Helper.getTestUrl(port));
        signUpPage.signUp("Johny", "Bravo", "johny_bravo", "12345678");

        assertFalse(Helper.webElementExists(signUpPage.getDivErrorMessage()));
        assertTrue(Helper.webElementExists(signUpPage.getDivSuccessMessage()));
        assertEquals("You successfully signed up! Please continue to the login page.", signUpPage.getDivSuccessMessage().getText());
    }

    @Test
    void testSubmitSignup_WeakPassword_ErrorBannerDisplayed() {
        signUpPage.goToPage(Helper.getTestUrl(port));
        signUpPage.signUp("Johny", "Bravo", "johny_bravo", "1234567");

        assertTrue(Helper.webElementExists(signUpPage.getDivErrorMessage()));
        assertFalse(Helper.webElementExists(signUpPage.getDivSuccessMessage()));
        assertEquals("Weak password.", signUpPage.getDivErrorMessage().getText());
    }
}
