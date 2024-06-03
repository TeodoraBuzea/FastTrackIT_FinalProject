import java.util.List;
import java.util.stream.Collectors;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class LandingPage extends BasePage {

    private WebDriver driver;
    private WebDriverWait wait;

    public LandingPage(WebDriver driver) {
        super(driver);
        this.driver = driver;
        this.wait = new WebDriverWait(driver, 10);
        PageFactory.initElements(driver, this);
    }

    // Header elements
    @FindBy(css = "a.navbar-brand")
    private WebElement brandLogo;

    @FindBy(css = "button.navbar-toggler")
    private WebElement navbarToggler;

    @FindBy(id = "responsive-navbar-nav")
    private WebElement navbarCollapse;

    @FindBy(css = "a[href='#/cart']")
    private WebElement shoppingCartIcon;

    @FindBy(css = "a[href='#/wishlist']")
    private WebElement wishlistIcon;

    @FindBy(css = "button.btn.btn-link > svg[data-icon='sign-in-alt']")
    private WebElement signInButton;

    // Product list elements
    @FindBy(css = "input#input-search")
    private WebElement searchInput;

    @FindBy(css = "button.btn.btn-light.btn-sm")
    private WebElement searchButton;

    // Footer elements
    @FindBy(css = "a.nav-link[data-rb-event-key='/']")
    private WebElement footerLink;

    @FindBy(css = "button[title='Reset the application state']")
    private WebElement resetButton;

    @FindBy(css = "button.btn.btn-link > svg[data-icon='sign-out-alt']")
    private WebElement signOutButton;

    @FindBy(xpath = "//*[@data-icon='question']")
    WebElement helpButton;

    // Help modal
    @FindBy(xpath = "//div[@class='modal-header']//div[@class='modal-title h4']/small[contains(text(), 'Help')]")
    WebElement helpModalTitle;

    @FindBy(xpath = "//div[@class='modal-header']//button[@type='button' and @class='close']")
    WebElement closeButton;

    // Sort elements
    @FindBy(css = "select.sort-products-select")
    private WebElement sortProductsSelect;

    @FindBy(xpath = "//div[contains(@class, 'card-body')]//a[contains(@class, 'card-link')]")
    private List<WebElement> productLinks;

    @FindBy(xpath = "//div[contains(@class, 'text-muted')]//p[contains(@class, 'card-text')][1]")
    private List<WebElement> productPrices;

    // Methods to interact with elements
    public void clickSignOutButton() {
        signOutButton.click();
    }

    public void clickBrandLogo() {
        brandLogo.click();
    }

    public void clickNavbarToggler() {
        navbarToggler.click();
    }

    public void clickShoppingCartIcon() {
        shoppingCartIcon.click();
    }

    public void clickWishlistIcon() {
        wishlistIcon.click();
    }

    public void clickSignInButton() {
        signInButton.click();
    }

    public void searchForProduct(String productName) {
        searchInput.sendKeys(productName);
        wait.until(ExpectedConditions.elementToBeClickable(searchButton));
        searchButton.click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[contains(@class, 'card-body')]")));
    }

    public void clickFooterLink() {
        footerLink.click();
    }

    public void clickResetButton() {
        resetButton.click();
    }
    
    public boolean isSearchResultDisplayed(String searchText) {
        List<WebElement> searchResults = driver.findElements(By.xpath("//div[contains(@class, 'card-body')]//a[contains(text(), '" + searchText + "')]"));
        return !searchResults.isEmpty();
    }

    // Item details

    public void addProductToFavorites(String productName) {
        WebElement addToFavoritesButton = driver.findElement(By.xpath("//button/child::*[@data-icon='heart']"));
        addToFavoritesButton.click();
    }

    public boolean isProductInFavorites(String productName) {
        clickWishlistIcon();
        List<WebElement> favoriteItems = driver.findElements(By.xpath("//a[contains(text(), '" + productName + "')]"));
        return !favoriteItems.isEmpty();
    }

    public void addProductToCart() {
        WebElement productLink = driver.findElement(By.xpath("//*[@data-icon='cart-plus']"));
        productLink.click();
    }

    public boolean isProductInCart(String productName) {
        clickShoppingCartIcon();
        List<WebElement> cartItems = driver.findElements(By.xpath("//a[contains(text(), '" + productName + "')]"));
        return !cartItems.isEmpty();
    }

    public boolean isProductDescriptionDisplayed(String description) {
        WebElement productDescription = driver.findElement(By.xpath("//div[contains(@class, 'card-body')]//p[contains(text(), '" + description + "')]"));
        return productDescription.isDisplayed();
    }

    public void clickProductLink(String productName) {
        WebElement productLink = driver.findElement(By.xpath("//div[@class='card']//a[contains(text(),'" + productName + "')]"));
        wait.until(ExpectedConditions.elementToBeClickable(productLink));
        productLink.click();
    }

    public String getProductDescription() {
        WebElement productDescription = driver.findElement(By.xpath("//div[@class='row']/div[2]/p"));
        return productDescription.getText();
    }

    public void clickHelpButton() {
        wait.until(ExpectedConditions.elementToBeClickable(helpButton)).click();
    }

    public boolean isHelpModalDisplayed() {
        return helpModalTitle.isDisplayed();
    }

    public void closeHelpModal() {
        closeButton.click();
    }

    // Sort methods
    public void selectSortOption(String sortOption) {
        Select sortDropdown = new Select(sortProductsSelect);
        sortDropdown.selectByValue(sortOption);
        wait.until(ExpectedConditions.attributeToBe(sortProductsSelect, "value", sortOption));
    }

    public List<String> getProductNames() {
        return productLinks.stream().map(WebElement::getText).collect(Collectors.toList());
    }

    public List<Double> getProductPrices() {
        return productPrices.stream()
                .map(WebElement::getText)
                .map(text -> text.replace("$", "").trim())
                .map(Double::parseDouble)
                .collect(Collectors.toList());
    }
}
