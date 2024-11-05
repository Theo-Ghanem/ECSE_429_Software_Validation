package stepdefinitions;

import static io.restassured.RestAssured.given;
import static org.junit.Assert.assertTrue;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.restassured.response.Response;

public class BaseStepDefinitions {

    protected Response response;

    @Given("the API server is running")
    public void theApiServerIsRunning() {
        given().when().get("http://localhost:4567/docs").then().statusCode(200);
    }

}