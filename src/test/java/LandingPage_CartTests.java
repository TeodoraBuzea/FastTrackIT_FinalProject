import com.aventstack.extentreports.Status;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Listeners(ExtentTestNGITestListener.class)

public class LandingPage_CartTests extends Hooks {

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

    }

    @AfterMethod
    public void tearDown() {
        driver.quit();
    }

    
    @Test(description = "This test checks that a product can be added to favorites section.")
    public void addToFavorite() throws InterruptedException {
        String productName = "Awesome Metal Chair";
        landingPage.searchForProduct(productName);
        landingPage.addProductToFavorites(productName);
        Thread.sleep(500);

        Assert.assertTrue(landingPage.isProductInFavorites(productName), "Product should be added to favorites.");
    }

    @Test(description = "The test verifies that a product can be added to the cart.")
    public void addItemToCart() throws InterruptedException {
        checkoutPage.clickAddProductGraniteChipsToCartLink();
        checkoutPage.clickAddProductGraniteChipsToCartButton();
        checkoutPage.clickCartButton();
        Assert.assertTrue(driver.getPageSource().contains("Awesome Granite Chips"), "Item should be added to the cart.");
    }

    @Test(description = "The test verifies that a product can be added to the cart without a user being logged in.")
    public void logout() throws InterruptedException {
        checkoutPage.clickAddProductGraniteChipsToCartLink();
        checkoutPage.clickAddProductGraniteChipsToCartButton();
        checkoutPage.clickCartButton();
        Assert.assertTrue(driver.getPageSource().contains("Awesome Granite Chips"), "Item should be added to the cart.");
    }


    @Test(description = "The test checks that an item can be removed from the cart.")
    public void removeItemFromCart() throws InterruptedException {
        checkoutPage.clickAddProductGraniteChipsToCartLink();
        checkoutPage.clickAddProductGraniteChipsToCartButton();
        checkoutPage.clickCartButton();

        cartPage.removeItemByTitle("Awesome Granite Chips");
        Assert.assertFalse(driver.getPageSource().contains("Awesome Granite Chips"), "Item should be removed from the cart.");
    }

    @Test(description = "Changing quantity test. Asserting that the price is updated correctly.")
    public void quantityTest() throws InterruptedException {
        checkoutPage.clickAddProductGraniteChipsToCartLink();
        checkoutPage.clickAddProductGraniteChipsToCartButton();
        checkoutPage.clickCartButton();
        ExtentTestNGITestListener.getTest().log(Status.INFO, "The price of the product is : " + checkoutPage.productPrice());
        double expectedTotal = checkoutPage.productPrice() * 2;
        ExtentTestNGITestListener.getTest().log(Status.INFO, "The price of the product after updating should be: " + expectedTotal);
        checkoutPage.clickPlusQuantity();
        ExtentTestNGITestListener.getTest().log(Status.INFO, "The price of the product after update is : " + checkoutPage.productPrice());
        assertEquals(checkoutPage.productPrice(),expectedTotal);
        ExtentTestNGITestListener.getTest().log(Status.INFO, "The price of the product is matching the expected price");
    }

    @Test(description = "Calculating the price of a product with taxes.")
    public void totalPriceCalculationTest() throws InterruptedException {
        checkoutPage.clickAddProductGraniteChipsToCartLink();
        checkoutPage.clickAddProductGraniteChipsToCartButton();
        checkoutPage.clickCartButton();
        ExtentTestNGITestListener.getTest().log(Status.INFO, "The price of the product is : " + checkoutPage.productPrice());
        ExtentTestNGITestListener.getTest().log(Status.INFO, "The price of the product is : " + checkoutPage.taxPrice());
        double expectedTotal = checkoutPage.productPrice() + checkoutPage.taxPrice();
        ExtentTestNGITestListener.getTest().log(Status.INFO, "The expected total should be: " + expectedTotal);
        ExtentTestNGITestListener.getTest().log(Status.INFO, "The actual total is: " + checkoutPage.totalPrice());
        assertEquals(checkoutPage.totalPrice(), expectedTotal);
    }

    @Test(description = "This test verifies that the user can search an item with keywords.")
    public void partialSearch() throws InterruptedException {
        String partialSearchText = "Granite";
        landingPage.searchForProduct(partialSearchText);
        ExtentTestNGITestListener.getTest().log(Status.INFO, "Performed partial search for: " + partialSearchText);
        Assert.assertTrue(landingPage.isSearchResultDisplayed(partialSearchText), "Search result should contain the partial text.");
    }

    @Test(description = "The test verifies that the search result displays only the product specific title completed by the user.")
    public void fullItemNameSearch() throws InterruptedException {
        String fullSearchText = "Awesome Granite Chips";
        landingPage.searchForProduct(fullSearchText);
        ExtentTestNGITestListener.getTest().log(Status.INFO, "Performed full item name search for: " + fullSearchText);
        Assert.assertTrue(landingPage.isSearchResultDisplayed(fullSearchText), "Search result should contain the whole text.");
    }

    @Test(description = "This test checks that the quantity of a product is increased in the cart.")
    public void increaseQuantityInCart() throws InterruptedException {
        // Add product to cart
        checkoutPage.clickAddProductGraniteChipsToCartLink();
        checkoutPage.clickAddProductGraniteChipsToCartButton();
        checkoutPage.clickCartButton();

        // Increase quantity
        cartPage.incrementItemQuantity();

        // Verify quantity increased
        String quantity = cartPage.getItemQuantity();
        ExtentTestNGITestListener.getTest().log(Status.INFO, "The quantity should be increased to: " + quantity);
        Assert.assertEquals(quantity, "2", "The quantity should be increased to 2.");
    }

    @Test(description = "This test checks that the quantity of a product is decreased in the cart.")
    public void decreaseQuantityInCart() throws InterruptedException {
        // Add product to cart
        checkoutPage.clickAddProductGraniteChipsToCartLink();
        checkoutPage.clickAddProductGraniteChipsToCartButton();
        checkoutPage.clickCartButton();

        // Increase and then decrease quantity
        cartPage.incrementItemQuantity();
        cartPage.decrementItemQuantity();

        // Verify quantity decreased
        String quantity = cartPage.getItemQuantity();
        ExtentTestNGITestListener.getTest().log(Status.INFO, "The quantity should be decreased to: " + quantity);
        Assert.assertEquals(quantity, "1", "The quantity should be decreased to 1.");
    }

    @Test(description = "This test checks that an info message is displayed if the cart is empty.")
    public void checkMessageOnEmptyCart() throws InterruptedException {
        checkoutPage.clickCartButton();

        String emptyCartMessage = checkoutPage.getEmptyCartMessage();
        Assert.assertEquals(emptyCartMessage, "How about adding some products in your cart?", "The empty cart message should be displayed.");
    }

    @Test(description = "The test verifies that the price is displayed properly after increasing the quantity of products in cart.")
    public void checkPriceAfterIncreasingQuantity() throws InterruptedException {
        // Add product to cart
        checkoutPage.clickAddProductGraniteChipsToCartLink();
        checkoutPage.clickAddProductGraniteChipsToCartButton();
        checkoutPage.clickCartButton();

        // Get initial price
        double initialPrice = Double.parseDouble(cartPage.getItemPrice().replace("$", ""));

        // Increase quantity
        cartPage.incrementItemQuantity();

        // Get updated price
        double updatedPrice = Double.parseDouble(cartPage.getItemTotalPrice().replace("$", ""));

        // Verify price increased correctly
        Assert.assertEquals(updatedPrice, initialPrice * 2, "Price should be doubled after increasing the quantity.");
    }

    @Test(description = "The test checks that the homepage can be reached after interacting the continue shopping button.")
    public void continueShoppingButtonRedirectsToHomePage() throws InterruptedException {
        checkoutPage.clickAddProductGraniteChipsToCartLink();
        checkoutPage.clickAddProductGraniteChipsToCartButton();
        checkoutPage.clickCartButton();

        cartPage.continueShopping();

        Assert.assertTrue(driver.getCurrentUrl().contains("products"), "Should be redirected to the homepage.");
    }

    @Test(description = "The test verifies that a product can be removed from favorites.")
    public void removeFromFavorite() throws InterruptedException {
        String productName = "Awesome Metal Chair";
        landingPage.searchForProduct(productName);
        landingPage.addProductToFavorites(productName);
        wishlistPage.clickRemoveFromFavoritesButton();
        Assert.assertFalse(landingPage.isProductInFavorites(productName), "Product should be removed from favorites.");
    }

    @Test(description = "This test verifies that the product can be added to cart from the product details page.")
    public void openItemDetailScreenAddToCart() throws InterruptedException {
        String productName = "Awesome Metal Chair";
        landingPage.clickProductLink(productName);
        landingPage.addProductToCart();
        Assert.assertTrue(landingPage.isProductInCart(productName), "Product should be added to cart.");
    }

    @Test(description = "This test verifies that the product can be added to favorites from the product details page.")
    public void openItemDetailScreenAddToFavorites() throws InterruptedException {
        String productName = "Awesome Metal Chair";
        landingPage.clickProductLink(productName);
        landingPage.addProductToFavorites(productName);
        Assert.assertTrue(landingPage.isProductInFavorites(productName), "Product should be added to favorites.");
    }

    @Test(description = "The test checks that the product description is properly displayed.")
    public void openItemDetailScreenCheckDescription() throws InterruptedException {
        String productName = "Awesome Metal Chair";
        String expectedDescription = "Adipisci velit optio dolorem minima ex. Dolorem magni dignissimos nemo impedit. Aperiam consequatur nisi. Tempora consequatur sint soluta."; // Replace with the expected description

        landingPage.clickProductLink(productName);
        Thread.sleep(500);
        String actualDescription = landingPage.getProductDescription();
        Assert.assertEquals(actualDescription, expectedDescription, "Product description should match the expected value.");
    }

    @Test(description = "The test verifies that the help section is reached.")
    public void verifyHelpModalOpens() throws InterruptedException {

        landingPage.clickHelpButton();
        Thread.sleep(500); 

        Assert.assertTrue(landingPage.isHelpModalDisplayed(), "Help section should be displayed after clicking the help button.");
    }

    // Sort tests

    @Test(description = "The test checks that the sorting options works properly.")
    public void testSortByNameAToZ() {
        landingPage.selectSortOption("az");
        List<String> productNames = landingPage.getProductNames();

        List<String> sortedNames = productNames.stream().sorted().collect(Collectors.toList());
        Assert.assertEquals(productNames, sortedNames, "Products should be sorted by name A to Z.");
    }

    @Test(description = "The test checks that the sorting options works properly.")
    public void testSortByNameZToA() {
        landingPage.selectSortOption("za");
        List<String> productNames = landingPage.getProductNames();

        List<String> sortedNames = productNames.stream().sorted(Comparator.reverseOrder()).collect(Collectors.toList());
        Assert.assertEquals(productNames, sortedNames, "Products should be sorted by name Z to A.");
    }

    @Test(description = "The test checks that the sorting options works properly.")
    public void testSortByPriceLowToHigh() {
        landingPage.selectSortOption("lohi");
        List<Double> productPrices = landingPage.getProductPrices();

        List<Double> sortedPrices = productPrices.stream().sorted().collect(Collectors.toList());
        Assert.assertEquals(productPrices, sortedPrices, "Products should be sorted by price low to high.");
    }

    @Test(description = "The test checks that the sorting options works properly.")
    public void testSortByPriceHighToLow() {
        landingPage.selectSortOption("hilo");
        List<Double> productPrices = landingPage.getProductPrices();

        List<Double> sortedPrices = productPrices.stream().sorted(Comparator.reverseOrder()).collect(Collectors.toList());
        Assert.assertEquals(productPrices, sortedPrices, "Products should be sorted by price high to low.");
    }

}



