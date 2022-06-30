package demo.steps.serenity;

import demo.pages.SwagLabsPage;
import net.serenitybdd.core.pages.WebElementFacade;
import net.thucydides.core.annotations.Step;
import org.junit.Assert;
import org.openqa.selenium.By;

import static junit.framework.TestCase.assertTrue;

public class SwagLabsSteps {
    private SwagLabsPage swagLabsPage;

    @Step
    public void isHomePage() {
        swagLabsPage.open();
    }

    @Step
    public void login(String username, String password) {
        swagLabsPage.login(username, password);
    }

    @Step
    public void addToCart() {
        swagLabsPage.addToCart();
    }

    @Step
    public void goToCart() {
        swagLabsPage.goToCart();
    }

    @Step
    public void continueCheckout() {
        swagLabsPage.continueCheckout();
    }

    @Step
    public void checkout() {
        swagLabsPage.checkout();
    }

    @Step
    public void isInvalidLogin() {
        assertTrue(swagLabsPage.containsText("Epic sadface: Username and password do not match any user in this service"));
    }

    @Step
    public void isProductPage() {
        WebElementFacade title = swagLabsPage.$(By.className("title"));
        assertTrue(title.containsText("PRODUCTS"));
        WebElementFacade container = swagLabsPage.$(By.id("inventory_container"));
        assertTrue(container.containsElements(By.className("inventory_item")));
        WebElementFacade list = swagLabsPage.$(By.className("inventory_item"));
        assertTrue(swagLabsPage.containsElements(By.className("inventory_item")));
    }

    @Step
    public void isCartPage() {
        WebElementFacade title = swagLabsPage.$(By.className("title"));
        assertTrue(title.containsText("YOUR CART"));
    }

    @Step
    public void isCheckoutPage() {
        WebElementFacade title = swagLabsPage.$(By.className("title"));
        assertTrue(title.containsText("CHECKOUT: YOUR INFORMATION"));
    }
    @Step
    public void isFinalPage() {
        WebElementFacade title = swagLabsPage.$(By.className("title"));
        assertTrue(title.containsText("CHECKOUT: COMPLETE!"));
    }

    @Step
    public void isCheckoutOverviewPage() {
        WebElementFacade title = swagLabsPage.$(By.className("title"));
        assertTrue(title.containsText("CHECKOUT: OVERVIEW"));
    }

    public void completeCheckout(String firstName, String lastName, String postalCode) {
        swagLabsPage.completeCheckout(firstName, lastName, postalCode);
    }

    public void finishCheckout() {
        swagLabsPage.finishCheckout();
    }

    @Step
    public void verifyInvalidCredentialsError(){
        Assert.assertTrue("Invalid credentials error message does not match with the expected message.", swagLabsPage.returnErrorMessage().equals("Epic sadface: Username and password do not match any user in this service"));
    }
}
