import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.aventstack.extentreports.Status;

import org.testng.Assert;


@Listeners(ExtentTestNGITestListener.class)
public class LoginTest extends Hooks {

    public LoginPage loginPage;
    public WebDriverWait wait;
    public LandingPage landingPage;

    @BeforeMethod
    public void SetupPageObject() {
        loginPage = new LoginPage(driver);
        landingPage = new LandingPage(driver);

        wait = new WebDriverWait(driver, 10);
    }

    @AfterMethod
    public void tearDown() {
        // Quit the WebDriver instance
        if (driver != null) {
            driver.quit();
        }
    }

    @Test(description = "This test checks that the login process is successfully completed.")
    public void happyFlowLogin() throws InterruptedException {
        loginPage.clickLoginIcon();
        loginPage.setUsername("dino");
        loginPage.setPassword("choochoo");
        loginPage.clickLoginButton();
        Assert.assertTrue(loginPage.isLoggedIn(), "User should be logged in successfully.");
    }

    @Test(description = "This test verifies that the error message is displayed for locked out user.")
    public void lockedUserLogin() throws InterruptedException {
        loginPage.clickLoginIcon();
        loginPage.setUsername("locked");
        loginPage.setPassword("choochoo");
        loginPage.clickLoginButton();
        Assert.assertTrue(loginPage.isErrorMessageDisplayed("The user has been locked out."), "Error message should be displayed for locked user.");
    }

    @Test(description = "This test verifies that an error message is displayed to the user when the user inputs an incorrect username.")
    public void loginWithIncorrectUsername() throws InterruptedException {
        loginPage.clickLoginIcon();
        loginPage.setUsername("incorrectUsername");
        loginPage.setPassword("choochoo");
        loginPage.clickLoginButton();
        Assert.assertTrue(loginPage.isErrorMessageDisplayed("Incorrect username or password!"), "Error message should be displayed for incorrect username.");
    }

    @Test(description = "This test verifies that an error message is displayed to the user when the user inputs an incorrect password.")
    public void loginWithIncorrectPassword() throws InterruptedException {
        loginPage.clickLoginIcon();
        loginPage.setUsername("dino");
        loginPage.setPassword("incorrectPassword");
        loginPage.clickLoginButton();
        Assert.assertTrue(loginPage.isErrorMessageDisplayed("Incorrect username or password!"), "Error message should be displayed for incorrect password.");
    }

    @Test(description = "This test checks that a message is displayed to the user when the username is not filled in.")
    public void loginWithoutUsername() throws InterruptedException {
        loginPage.clickLoginIcon();
        loginPage.setPassword("anyPassword");
        loginPage.clickLoginButton();
        Assert.assertTrue(loginPage.isErrorMessageDisplayed("Please fill in the username!"), "Error message should be displayed for missing username.");
    }

    @Test(description = "This test checks that a message is displayed to the user when the password is not filled in.")
    public void loginWithoutPassword() throws InterruptedException {
        loginPage.clickLoginIcon();
        loginPage.setUsername("anyUser");
        loginPage.clickLoginButton();
        Assert.assertTrue(loginPage.isErrorMessageDisplayed("Please fill in the password!"), "Error message should be displayed for missing password.");
    }

    @Test(description = "This test verifies that Hello guest! message is displayed when the user is logged out.")
    public void logoutTest() throws InterruptedException {
        loginPage.clickLoginIcon();
        loginPage.setUsername("beetle");
        loginPage.setPassword("choochoo");
        loginPage.clickLoginButton();
        landingPage.clickSignOutButton();
        ExtentTestNGITestListener.getTest().log(Status.INFO, "Clicked on logout button.");
        Assert.assertTrue(driver.findElement(By.xpath("//span[text()='Hello guest! ']")).isDisplayed(), "Hello guest text should be displayed after logout.");
    }
}
