package stepdefinitions;

import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertTrue;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import static io.restassured.RestAssured.given;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class TodoStepDefinitions {

    private Response response;
    private String baseUrl = "http://localhost:4567/todos";

    // Background step: Ensure the API server is running
    @Given("the API server is running")
    public void theApiServerIsRunning() {
        // Optionally check if the server is running (ping the server or check the docs)
        given().when().get("http://localhost:4567/docs").then().statusCode(200);
    }

    // Normal Flow: Adding a new todo successfully
    @When("the user adds a todo with the following details:")
    public void theUserAddsATodoWithTheFollowingDetails(io.cucumber.datatable.DataTable dataTable) {
        // Convert the data table to a list of todos
        for (var row : dataTable.asLists()) {
            String title = row.get(0);
            if (title == null) {
                title = "";
            }
            boolean doneStatus = Boolean.parseBoolean(row.get(1));
            String description = row.get(2);
            if (description == null) {
                description = "";
            }

            // Construct the request body for the new todo
            String jsonBody = String.format(
                    "{ \"title\": \"%s\", \"doneStatus\": %b, \"description\": \"%s\" }",
                    title, doneStatus, description);

            // Send the POST request to add the todo
            response = given()
                    .contentType("application/json")
                    .body(jsonBody)
                    .when()
                    .post(baseUrl);

        }
    }

    @Then("the following todos should be present in the system:")
    public void theFollowingTodosShouldBePresentInTheSystem(io.cucumber.datatable.DataTable dataTable) {
        for (var row : dataTable.asLists()) {
            String title = row.get(0);
            boolean doneStatus = Boolean.parseBoolean(row.get(1));
            String description = row.get(2);

            // Fetch the list of todos and assert that the new todo is present
            response = given().when().get(baseUrl);
            response.then().statusCode(200);
            String responseBody = response.body().asString();

            // Log the response body for debugging
            System.out.println("Response Body: " + responseBody);
            System.out.println("Expected Title: " + title);

            // Use JsonPath to parse and verify todos
            JsonPath jsonPath = new JsonPath(responseBody);
            List<Map<String, Object>> todos = jsonPath.getList("todos");

            boolean found = false;
            for (Map<String, Object> todo : todos) {
                if (todo.get("title").equals(title) &&
                        todo.get("doneStatus").toString().equals(String.valueOf(doneStatus)) &&
                        (todo.get("description").equals(description)
                                || (todo.get("description").equals("") && description == null))) {
                    found = true;
                    break;
                }
            }

            assertTrue("Expected title not found in response body.", found);
        }
    }

    // Error Flow: Adding a new todo with missing title
    // @When("the user attempts to add a todo with the following details:")
    // public void theUserAttemptsToAddATodoWithTheFollowingDetailsMissingTitle(
    // io.cucumber.datatable.DataTable dataTable) {
    // for (var row : dataTable.asLists()) {
    // String title = row.get(0);
    // boolean doneStatus = Boolean.parseBoolean(row.get(1));
    // String description = row.get(2);

    // // Construct the request body for the new todo (with missing title)
    // String jsonBody = "{ \"title\": \"" + title + "\", \"doneStatus\": \"" +
    // doneStatus
    // + "\", \"description\": \"" + description + "\" }";

    // // Send the POST request to add the todo
    // response = given()
    // .contentType("application/json")
    // .body(jsonBody)
    // .when()
    // .post(baseUrl);
    // }
    // }

    @Then("an error message {string} should be returned")
    public void anErrorMessageShouldBeReturned(String expectedErrorMessage) {
        response.then().statusCode(400); // Assuming 400 Bad Request for missing title
        String responseBody = response.body().asString();

        // Check if the error message contains the expected message
        assertTrue(responseBody.contains(expectedErrorMessage));
    }
}