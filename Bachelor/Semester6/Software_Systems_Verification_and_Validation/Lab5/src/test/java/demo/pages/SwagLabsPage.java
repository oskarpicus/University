package demo.pages;

import net.serenitybdd.core.pages.WebElementFacade;
import net.thucydides.core.annotations.DefaultUrl;
import net.thucydides.core.pages.PageObject;
import org.openqa.selenium.support.FindBy;

@DefaultUrl("https://www.saucedemo.com/")
public class SwagLabsPage extends PageObject {

    @FindBy(name = "firstName")
    private WebElementFacade firstNameField;

    @FindBy(name = "lastName")
    private WebElementFacade lastNameField;

    @FindBy(name = "postalCode")
    private WebElementFacade postalCodeField;

    @FindBy(name = "user-name")
    private WebElementFacade usernameField;

    @FindBy(name = "password")
    private WebElementFacade passwordField;

    @FindBy(name = "login-button")
    private WebElementFacade loginButton;

    @FindBy(name = "continue")
    private WebElementFacade continueButton;

    @FindBy(name = "add-to-cart-sauce-labs-backpack")
    private WebElementFacade addToCartButton;

    @FindBy(className = "shopping_cart_link")
    private WebElementFacade goToCartButton;

    @FindBy(name = "checkout")
    private WebElementFacade checkoutButton;

    @FindBy(name = "finish")
    private WebElementFacade finishButton;

    @FindBy(css = ".error-message-container.error")
    private WebElementFacade loginErrorMessage;


    public void login(String username, String password) {
        usernameField.type(username);
        passwordField.type(password);
        loginButton.click();
    }

    public void completeCheckout(String firstName, String lastName, String postalCode) {
        firstNameField.type(firstName);
        lastNameField.type(lastName);
        postalCodeField.type(postalCode);
    }

    public void addToCart() {
        addToCartButton.click();
    }

    public void goToCart() {
        goToCartButton.click();
    }

    public void finishCheckout() {
        finishButton.click();
    }

    public void checkout() {
        checkoutButton.click();
    }

    public void continueCheckout() {
        continueButton.click();
    }

    public String returnErrorMessage(){
        return  loginErrorMessage.getText();
    }
}
