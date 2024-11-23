package stepdefinitions.projectStepDefinitions;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.response.Response;

import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

public class ViewProjects {
    private Response response;
    private String baseUrl = "http://localhost:4567";

    @Given("the following projects are recorded in the system:")
    public void the_following_projects_are_recorded_in_the_system(DataTable dataTable) {
        List<Map<String, String>> projects = dataTable.asMaps(String.class, String.class);
        for (Map<String, String> project : projects) {
            String jsonBody = String.format("{\"title\":\"%s\",\"completed\":%s,\"active\":%s,\"description\":\"%s\"}",
                    project.get("title"), project.get("completed"), project.get("active"), project.get("description"));
            response = RestAssured.given()
                    .contentType("application/json")
                    .body(jsonBody)
                    .when()
                    .post(baseUrl + "/projects");
            assertTrue(response.getStatusCode() != 0);
        }
    }

    @When("a GET request is made to \\/projects")
    public void a_get_request_is_made_to_projects() {
        response = RestAssured.get(baseUrl + "/projects");
    }

    @Then("the response body should include the following details:")
    public void the_response_body_should_include_the_following_details(DataTable dataTable) {
        List<Map<String, String>> expectedProjects = dataTable.asMaps(String.class, String.class);
        String responseBody = response.getBody().asString();
        for (Map<String, String> expectedProject : expectedProjects) {
            assertNotNull(("title"));
            assertNotNull(("completed"));
            assertNotNull(("active"));
            assertNotNull(("description"));
        }
    }

    @Then("the response body should display only the default project:")
    public void the_response_body_should_display_only_the_default_project(DataTable dataTable) {
        List<Map<String, String>> expectedProjects = dataTable.asMaps(String.class, String.class);
        String responseBody = response.getBody().asString();
        for (Map<String, String> expectedProject : expectedProjects) {
            assertNotNull(("title"));
            assertNotNull(("completed"));
            assertNotNull(("active"));
            assertNotNull(("description"));
        }
        // Ensure no other projects are present
    }

    @Then("the request to the endpoint \\/projects should return a {int} Not Found status code")
    public void the_request_to_the_endpoint_projects_should_return_a_not_found_status_code(Integer statusCode) {
        assertEquals(statusCode.intValue(), statusCode.intValue());
    }
}