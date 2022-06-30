package demo.features.search;

import demo.steps.serenity.SwagLabsSteps;
import net.serenitybdd.junit.runners.SerenityParameterizedRunner;
import net.serenitybdd.junit.runners.SerenityRunner;
import net.thucydides.core.annotations.Managed;
import net.thucydides.core.annotations.Steps;
import net.thucydides.junit.annotations.Qualifier;
import net.thucydides.junit.annotations.TestData;
import net.thucydides.junit.annotations.UseTestDataFrom;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.WebDriver;

@RunWith(SerenityParameterizedRunner.class)
@UseTestDataFrom(value = "src/test/resources/features/login/data.csv")
public class CheckoutTest {
    @Managed(uniqueSession = true, driver="firefox")
    public WebDriver webdriver;

    @Steps
    public SwagLabsSteps steps;

    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private String postalCode;

    @Qualifier
    public String getQualifier() {
        return username + " " + password + " " + firstName + " " + lastName + " " +postalCode;
    }
    @TestData(columnNames = "USERNAME, PASSWORD, FIRSTNAME, LASTNAME, POSTALCODE")
    @Test
    public void add_to_cart() {
        steps.isHomePage();
        steps.login(username, password);
        steps.isProductPage();
        steps.addToCart();
        steps.goToCart();
        steps.isCartPage();
        steps.checkout();
        steps.isCheckoutPage();
        steps.completeCheckout(firstName, lastName, postalCode);
        steps.continueCheckout();
        steps.isCheckoutOverviewPage();
        steps.finishCheckout();
        steps.isFinalPage();
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
