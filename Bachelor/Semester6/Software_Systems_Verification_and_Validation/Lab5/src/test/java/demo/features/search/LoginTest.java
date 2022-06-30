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
public class LoginTest {
    @Managed(uniqueSession = true, driver="firefox")
    public WebDriver webdriver;

    @Steps
    public SwagLabsSteps steps;

    private String username;
    private String password;

    @Qualifier
    public String getQualifier() {
        return username + " " + password;
    }

    @TestData(columnNames = "USERNAME, PASSWORD")
    @Test
    public void login_successful() {
        steps.isHomePage();
        steps.login(username, password);
        steps.isProductPage();
    }
    @Test
    public void loginUnsuccessful(){
        steps.isHomePage();
        steps.login("bunnyRabbit","chosenOne");
        steps.verifyInvalidCredentialsError();
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
