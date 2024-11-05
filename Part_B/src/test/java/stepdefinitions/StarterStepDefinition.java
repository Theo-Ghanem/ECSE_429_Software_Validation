package stepdefinitions;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.restassured.RestAssured;

public class StarterStepDefinition extends HelperStepDefinition {

    @Before
    public void initialize() {
        RestAssured.baseURI = BASE_URL;
        startServer();
        statusCode = 0;
        errorMessage = "";
    }

    @After
    public void after() {
        stopServer();
    }

    @Given("^the API server is running$")
    public void theAPIServerIsRunning() {
        waitUntilOnline();
    }
}