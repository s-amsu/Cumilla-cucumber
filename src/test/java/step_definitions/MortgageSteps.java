package step_definitions;

import command_providers.ActOn;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import page_objects.Home;
import page_objects.RealApr;
import utilities.ReadConfigFiles;
import java.util.List;
import java.util.Map;

public class MortgageSteps {
    private static final Logger LOGGER = LogManager.getLogger(MortgageSteps.class);
    WebDriver driver = Hooks.driver;

    @Given("^user is in mortgage calculator home page$")
    public void navigateToMortgageCalculatorHomePage() {
        ActOn.browser(driver).openBrowser(ReadConfigFiles.getPropertyValues("MortgageAppURL"));//MortgageAppURL
        LOGGER.info("Landed on the mortgage calculator home page");
    }
    @And("^user navigate to Real Apr page$")
    public void navigateToRealAprPage() {
        new Home(driver)
                .mouseHoverToRates()
                .navigateToRealApr()
                .waitForPageToLoad();
        LOGGER.info("Navigate to real apr rate");
    }
    @When("^user clicks on the calculate button upon entering data$")
    public  void clickOnCalculatorButtonUponEnteringTheData(DataTable table) {
        List<Map<String, String>> data = table.asMaps(String.class, String.class);
        for (Map<String, String> cells : data) {
            new RealApr(driver)
                    .typeHomePrice(cells.get("HomePrice"))
                    .typeDownPayment(cells.get("DownPayment"))
                    .typeInterestRate(cells.get("InterestRate"))
                    .clickOnCalculateButton();
        }
        LOGGER.info("Real APR Rate is Calculator upon entering the data");
    }
    @Then("the real apr rate is \"(.+?)\"$")
    public void validateRealAprRate(String realApr) {
        new RealApr(driver)
                .validateRealAprRate(realApr);
        LOGGER.info(String.format("Real Apr rate: %s is validated", realApr));
    }
}
