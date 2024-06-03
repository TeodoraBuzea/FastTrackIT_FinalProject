import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Listeners;

import org.testng.annotations.AfterMethod;

@Listeners(ExtentTestNGITestListener.class)
public class CheckoutTests extends Hooks {

    public CheckoutPage checkoutPage;
    public CartPage cartPage;
    public WebDriverWait wait;
    public LoginPage loginPage;
    public LandingPage landingPage;
    public WishlistPage wishlistPage;


    @BeforeMethod
    public void SetupPageObject() {
        wait = new WebDriverWait(driver, 10);

        checkoutPage = new CheckoutPage(driver);
        loginPage = new LoginPage(driver);
        cartPage = new CartPage(driver);
        landingPage = new LandingPage(driver);
        wishlistPage = new WishlistPage(driver);


        loginPage.clickLoginIcon();
        loginPage.setUsername("dino");
        loginPage.setPassword("choochoo");
        loginPage.clickLoginButton();
    }

    @AfterMethod
    public void tearDown() {
        driver.quit();
    }

    @Test(description = "This test checks that an error message is displayed if the first name field is not filled in.")
    public void checkoutWithIncompleteInformation () throws InterruptedException {
        landingPage.clickProductLink("Awesome Granite Chips");
        landingPage.addProductToCart();
        landingPage.clickShoppingCartIcon();
        cartPage.proceedToCheckout();

        checkoutPage.continueCheckout();
        Thread.sleep(500);

        String checkoutHeader = checkoutPage.getCheckoutError();
        Assert.assertEquals(checkoutHeader, "First Name is required", "Error message should indicate the user is still on the checkout page due to incomplete information.");
    }

    @Test(description = "This test checks that an error message is displayed if the last name field is not filled in.")
    public void checkoutWithIncompleteInformation2() throws InterruptedException {
        landingPage.clickProductLink("Awesome Granite Chips");
        landingPage.addProductToCart();
        landingPage.clickShoppingCartIcon();
        cartPage.proceedToCheckout();

        checkoutPage.enterFirstName("dino");
        checkoutPage.continueCheckout();

        String checkoutHeader = checkoutPage.getCheckoutError();
        Assert.assertEquals(checkoutHeader, "Last Name is required", "Error message should indicate the user is still on the checkout page due to incomplete information.");
    }

    @Test(description = "This test checks that an error message is displayed if the address field is not filled in.")
    public void checkoutWithIncompleteInformation3() throws InterruptedException {
        landingPage.clickProductLink("Awesome Granite Chips");
        landingPage.addProductToCart();
        landingPage.clickShoppingCartIcon();
        cartPage.proceedToCheckout();

        checkoutPage.enterFirstName("dino");
        checkoutPage.enterLastName("dino");
        checkoutPage.continueCheckout();

        String checkoutHeader = checkoutPage.getCheckoutError();
        Assert.assertEquals(checkoutHeader, "Address is required", "Error message should indicate the user is still on the checkout page due to incomplete information.");
    }

    @Test(description = "This test verifies if the product price, tax price and total price are properly calculated on the summary page.")
    public void verifyOrderSummary() throws InterruptedException {
        landingPage.clickProductLink("Awesome Granite Chips");
        landingPage.addProductToCart();

        landingPage.clickShoppingCartIcon();
        cartPage.proceedToCheckout();

        checkoutPage.enterFirstName("dino");
        checkoutPage.enterLastName("dino");
        checkoutPage.enterAddress("123");
        Thread.sleep(1000);
        checkoutPage.continueCheckout();

        // Verify the information is displayed
        boolean isProductPriceDisplayed = checkoutPage.isProductPriceDisplayed();
        boolean isTaxPriceDisplayed = checkoutPage.isTaxPriceDisplayed();
        boolean isTotalPriceDisplayed = checkoutPage.isTotalPriceDisplayed();

        Assert.assertTrue(isProductPriceDisplayed, "Product price should be displayed.");
        Assert.assertTrue(isTaxPriceDisplayed, "Tax price should be displayed.");
        Assert.assertTrue(isTotalPriceDisplayed, "Total price should be displayed.");
    }

    @Test(description = "This test verifies if order confirmation message is displayed.")
    public void completeOrder() throws InterruptedException {
        landingPage.clickProductLink("Awesome Granite Chips");
        landingPage.addProductToCart();

        landingPage.clickShoppingCartIcon();
        cartPage.proceedToCheckout();

        checkoutPage.enterFirstName("John");
        checkoutPage.enterLastName("Doe");
        checkoutPage.enterAddress("123 Main St");
        Thread.sleep(500);

        checkoutPage.continueCheckout();
        Thread.sleep(500);

        checkoutPage.clickCompleteOrder();

        WebElement confirmationMessage = driver.findElement(By.xpath("//div[contains(@class, 'text-center')]"));
        Assert.assertTrue(confirmationMessage.isDisplayed(), "Order confirmation message should be displayed.");
    }


}
