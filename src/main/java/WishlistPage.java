import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class WishlistPage extends BasePage {

    public WishlistPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    // Elements in the Wishlist page
    @FindBy(xpath = "//div[@class='row row-cols-xl-4 row-cols-lg-3 row-cols-md-2 row-cols-sm-2 row-cols-1']")
    private WebElement wishlistItemsContainer;

    @FindBy(xpath = "//div[@class='card']//button[@type='button' and contains(@class, 'btn-link') and .//*[name()='svg' and @data-icon='cart-plus']]")
    private WebElement addToCartButton;

    @FindBy(xpath = "//div[@class='card']//button[@type='button' and contains(@class, 'btn-link') and .//*[name()='svg' and @data-icon='heart-broken']]")
    private WebElement removeFromFavoritesButton;

    @FindBy(xpath = "//div[@class='text-center card-body']//a[contains(@class, 'card-link')]")
    private WebElement productNameLink;

    public void clickAddToCartButton() {
        addToCartButton.click();
    }

    public void clickRemoveFromFavoritesButton() {
        removeFromFavoritesButton.click();
    }

    public String getProductName() {
        return productNameLink.getText();
    }
}
