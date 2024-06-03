import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.By;

public class LoginPage extends BasePage {

    public WebDriverWait wait;

    public LoginPage(WebDriver driver) {
        super(driver);
        wait = new WebDriverWait(driver, 30);
    }

    @FindBy(css = "button.btn.btn-link")
    private WebElement loginIcon;

    @FindBy(id = "user-name")
    private WebElement username;

    @FindBy(id = "password")
    private WebElement password;

    @FindBy(css = "button.btn.btn-primary")
    private WebElement loginButton;

    @FindBy(css = "p.error[data-test='error']")
    private WebElement errorMessage;

    public void clickLoginIcon() {
        loginIcon.click();
    }

    public void setUsername(String user) {
        username.sendKeys(user);
    }

    public void setPassword(String pass) {
        password.sendKeys(pass);
    }

    public void clickLoginButton() {
        loginButton.click();
    }

    public boolean isErrorMessageDisplayed(String message) {
        return errorMessage.getText().contains(message);
    }

    public boolean isLoggedIn() {
        // Check for the presence of the span element that contains the text "Hi"
        try {
            WebElement hiSpan = driver.findElement(By.xpath("//span[contains(text(),'Hi')]"));
            return hiSpan.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
}
