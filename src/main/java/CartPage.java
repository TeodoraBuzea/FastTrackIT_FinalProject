import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class CartPage {
    WebDriver driver;

    public CartPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    // Cart Items Section
    @FindBy(xpath = "//h1/small[contains(text(), 'Your cart')]")
    WebElement cartHeader;

    // Single Cart Item Elements
    @FindBy(xpath = "//div[contains(@class, 'row') and contains(@style, 'margin-bottom: 10px')]")
    WebElement cartItemRow;

    @FindBy(xpath = "//button[@type='button' and contains(@class, 'btn-link') and .//*[name()='svg' and @data-icon='minus-circle']]")
    WebElement decrementButton;

    @FindBy(xpath = "//div[contains(@class, 'col-md-auto') and div[contains(@style, 'width: 130px;')]]/div")
    WebElement itemQuantity;

    @FindBy(xpath = "//button[@type='button' and contains(@class, 'btn-link') and .//*[name()='svg' and @data-icon='plus-circle']]")
    WebElement incrementButton;

    @FindBy(xpath = "//div[contains(@class, 'col-md-auto') and div[contains(@style, 'width: 100px;')]]")
    WebElement itemPrice;

    @FindBy(xpath = "//div[contains(@class, 'col-md-auto') and contains(@style, 'margin-top: 7px;') and div[contains(@style, 'width: 100px;')]]")
    WebElement itemTotalPrice;

    @FindBy(xpath = "//div[contains(@class, 'col') and contains(@style, 'margin-top: 7px;')]/a")
    WebElement itemNameLink;

    @FindBy(xpath = "//button[@type='button' and contains(@class, 'btn-link') and .//*[name()='svg' and @data-icon='trash']]")
    WebElement removeItemButton;

    // Summary Section
    @FindBy(xpath = "//td[text()='Items total:']/following-sibling::td")
    WebElement itemsTotal;

    @FindBy(xpath = "//td[text()='Tax:']/following-sibling::td")
    WebElement taxAmount;

    @FindBy(xpath = "//td[text()='Total:']/following-sibling::td")
    WebElement totalAmount;

    // Action Buttons
    @FindBy(xpath = "//a[contains(@href, '#/products') and contains(@class, 'btn-danger')]")
    WebElement continueShoppingButton;

    @FindBy(xpath = "//a[contains(@href, '#/checkout-info') and contains(@class, 'btn-success')]")
    WebElement checkoutButton;

    // Methods to interact with the elements
    public String getCartHeader() {
        return cartHeader.getText();
    }

    public void decrementItemQuantity() {
        decrementButton.click();
    }

    public String getItemQuantity() {
        return itemQuantity.getText();
    }

    public void incrementItemQuantity() {
        incrementButton.click();
    }

    public String getItemPrice() {
        return itemPrice.getText();
    }

    public String getItemTotalPrice() {
        return itemTotalPrice.getText();
    }

    public String getItemName() {
        return itemNameLink.getText();
    }

    public void removeItem() {
        removeItemButton.click();
    }

    public String getItemsTotal() {
        return itemsTotal.getText();
    }

    public String getTaxAmount() {
        return taxAmount.getText();
    }

    public String getTotalAmount() {
        return totalAmount.getText();
    }

    public void continueShopping() {
        continueShoppingButton.click();
    }

    public void proceedToCheckout() {
        checkoutButton.click();
    }

    public void removeItemByTitle(String itemTitle) {
        WebElement itemRow = driver.findElement(By.xpath("//a[text()='" + itemTitle + "']/ancestor::div[contains(@class, 'row')]"));
        WebElement removeButton = itemRow.findElement(By.cssSelector(".svg-inline--fa.fa-trash.fa-w-14"));
        removeButton.click();
    }
}
