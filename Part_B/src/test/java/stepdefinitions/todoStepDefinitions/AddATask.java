package stepdefinitions.todoStepDefinitions;

import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertTrue;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import static io.restassured.RestAssured.given;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import stepdefinitions.HelperStepDefinition;

public class AddATask extends HelperStepDefinition {

    private Response response;
    private String baseUrl = "http://localhost:4567/todos";


    // Normal Flow: Creating a task successfully
    @When("a user adds the following tasks with descriptions:")
    public void aUserAddsTheFollowingTasksWithDescriptions(io.cucumber.datatable.DataTable dataTable) {
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

            String jsonBody = String.format(
                    "{ \"title\": \"%s\", \"doneStatus\": %b, \"description\": \"%s\" }",
                    title, doneStatus, description);

            response = given()
                    .contentType("application/json")
                    .body(jsonBody)
                    .when()
                    .post(baseUrl);
        }
    }

    @Then("the system should contain the following tasks with descriptions:")
    public void theSystemShouldContainTheFollowingTasksWithDescriptions(io.cucumber.datatable.DataTable dataTable) {
        for (var row : dataTable.asLists()) {
            String title = row.get(0);
            boolean doneStatus = Boolean.parseBoolean(row.get(1));
            String description = row.get(2);

            response = given().when().get(baseUrl);
            response.then().statusCode(200);
            String responseBody = response.body().asString();

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

    // Alternate Flow: Creating a task successfully with no description
    @When("a user adds the following tasks without descriptions:")
    public void aUserAddsTheFollowingTasksWithoutDescriptions(io.cucumber.datatable.DataTable dataTable) {
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

            String jsonBody = String.format(
                    "{ \"title\": \"%s\", \"doneStatus\": %b, \"description\": \"%s\" }",
                    title, doneStatus, description);

            response = given()
                    .contentType("application/json")
                    .body(jsonBody)
                    .when()
                    .post(baseUrl);
        }
    }

    @Then("the system should contain the following tasks without descriptions:")
    public void theSystemShouldContainTheFollowingTasksWithoutDescriptions(io.cucumber.datatable.DataTable dataTable) {
        for (var row : dataTable.asLists()) {
            String title = row.get(0);
            boolean doneStatus = Boolean.parseBoolean(row.get(1));
            String description = row.get(2);

            response = given().when().get(baseUrl);
            response.then().statusCode(200);
            String responseBody = response.body().asString();

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

    // Error Flow: Failing to create a task with an empty title
    @When("a user attempts to add the following tasks with empty titles:")
    public void aUserAttemptsToAddTheFollowingTasksWithEmptyTitles(io.cucumber.datatable.DataTable dataTable) {
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

            String jsonBody = String.format(
                    "{ \"title\": \"%s\", \"doneStatus\": %b, \"description\": \"%s\" }",
                    title, doneStatus, description);

            response = given()
                    .contentType("application/json")
                    .body(jsonBody)
                    .when()
                    .post(baseUrl);
        }
    }

    @Then("a task error message {string} should be returned")
    public void aTaskErrorMessageShouldBeReturned(String expectedErrorMessage) {
        response.then().statusCode(400);
        String responseBody = response.body().asString();
        assertTrue(responseBody.contains(expectedErrorMessage));
    }

}