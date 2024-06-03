import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class CheckoutPage extends BasePage {

    public WebDriverWait wait;

    public CheckoutPage(WebDriver driver) {
        super(driver);
        wait = new WebDriverWait(driver, 30);
        PageFactory.initElements(driver, this);
    }

    // ADD Awesome Granite Chips Product To Cart Test
    @FindBy(linkText = "Awesome Granite Chips")
    private WebElement addProductGraniteChipsToCartLink;

    public void clickAddProductGraniteChipsToCartLink() {
        addProductGraniteChipsToCartLink.click();
    }

    @FindBy(css = ".svg-inline--fa.fa-cart-plus.fa-w-18.fa-3x")
    private WebElement addProductGraniteChipsToCartButton;

    public void clickAddProductGraniteChipsToCartButton() {
        addProductGraniteChipsToCartButton.click();
    }

    @FindBy(css = ".svg-inline--fa.fa-shopping-cart.fa-w-18")
    private WebElement cartButton;

    public void clickCartButton() {
        cartButton.click();
    }

    @FindBy(xpath = "(//td[@class='amount'])[1]")
    private WebElement itemPrice;

    @FindBy(xpath = "(//td[@class='amount'])[2]")
    private WebElement taxPrice;
    
    @FindBy(xpath = "(//td[@class='amount'])[3]")
    private WebElement totalPrice;

    public double productPrice() {
        String amountValue = itemPrice.getText();
        String cleanAmountValue = amountValue.replace("$","");
        return Double.parseDouble(cleanAmountValue);
    }

    @FindBy(css = ".svg-inline--fa.fa-plus-circle.fa-w-16")
    private WebElement plusQuantity;

    public void clickPlusQuantity() {
        plusQuantity.click();
    }

    public double taxPrice() {
        String taxValue = taxPrice.getText();
        String cleanTaxValue = taxValue.replace("$", "");
        return Double.parseDouble(cleanTaxValue);
    }

    public double totalPrice() {
        String totalValue = totalPrice.getText();
        String cleanTotalValue = totalValue.replace("$", "");
        return Double.parseDouble(cleanTotalValue);
    }

    @FindBy(xpath = "//*[@data-test='error']")
    WebElement checkoutErrorMessage;

    // Address Information
    @FindBy(id="first-name")
    WebElement firstNameInput;

    @FindBy(id="last-name")
    WebElement lastNameInput;

    @FindBy(id="address")
    WebElement addressInput;

    // Delivery Information
    @FindBy(xpath = "//label[contains(text(), 'Choo Choo delivery')]/preceding-sibling::input[@data-test='delivery-type']")
    WebElement deliveryTypeChooChoo;

    // Payment Information
    @FindBy(xpath = "//label[contains(text(), 'Cash on delivery')]/preceding-sibling::input[@data-test='payment-type']")
    WebElement paymentTypeCashOnDelivery;

    @FindBy(xpath = "//label[contains(text(), 'Credit card')]/preceding-sibling::input[@data-test='payment-type']")
    WebElement paymentTypeCreditCard;

    @FindBy(xpath = "//label[contains(text(), 'PayPal')]/preceding-sibling::input[@data-test='payment-type']")
    WebElement paymentTypePayPal;

    // Action Buttons
    @FindBy(xpath = "//a[contains(@href, '#/cart') and contains(@class, 'btn-danger')]")
    WebElement cancelButton;

    @FindBy(xpath = "//button[@type='submit' and contains(@class, 'btn-success')]")
    WebElement continueCheckoutButton;
    
    @FindBy(xpath = "//a[contains(@href, '#/checkout-complete') and contains(@class, 'btn btn-success')]")
    WebElement completeOrderButton;

    @FindBy(xpath = "//div[@class='text-center container' and contains(text(), 'How about adding some products in your cart?')]")
    private WebElement emptyCartMessage;
    
    
    // Methods to interact with the elements
    public String getCheckoutError() {
        return checkoutErrorMessage.getText();
    }

    public void enterFirstName(String firstName) {
        firstNameInput.clear();
        firstNameInput.sendKeys(firstName);
    }

    public void enterLastName(String lastName) {
        lastNameInput.clear();
        lastNameInput.sendKeys(lastName);
    }

    public void enterAddress(String address) {
        addressInput.clear();
        addressInput.sendKeys(address);
    }

    public void selectDeliveryTypeChooChoo() {
        if (!deliveryTypeChooChoo.isSelected()) {
            deliveryTypeChooChoo.click();
        }
    }

    public void selectPaymentTypeCashOnDelivery() {
        if (!paymentTypeCashOnDelivery.isSelected()) {
            paymentTypeCashOnDelivery.click();
        }
    }

    public void selectPaymentTypeCreditCard() {
        if (!paymentTypeCreditCard.isSelected()) {
            paymentTypeCreditCard.click();
        }
    }

    public void selectPaymentTypePayPal() {
        if (!paymentTypePayPal.isSelected()) {
            paymentTypePayPal.click();
        }
    }

    public void cancelCheckout() {
        cancelButton.click();
    }

    public void continueCheckout() {
        continueCheckoutButton.click();
    }

    public boolean isProductPriceDisplayed() {
        return itemPrice.isDisplayed();
    }

    public boolean isTaxPriceDisplayed() {
        return taxPrice.isDisplayed();
    }

    public boolean isTotalPriceDisplayed() {
        return totalPrice.isDisplayed();
    }

    public void clickCompleteOrder() {
        completeOrderButton.click(); 
    }

    public String getEmptyCartMessage() {
        wait.until(ExpectedConditions.visibilityOf(emptyCartMessage));
        return emptyCartMessage.getText();
    }
}
