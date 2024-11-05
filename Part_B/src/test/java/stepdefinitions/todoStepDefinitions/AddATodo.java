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

public class AddATodo extends HelperStepDefinition {

    private Response response;
    private String baseUrl = "http://localhost:4567/todos";


    // Normal Flow: Adding a new todo successfully
    @When("the user adds the following todos:")
    public void theUserAddsTheFollowingTodos(io.cucumber.datatable.DataTable dataTable) {
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

    @Then("the system should contain the following todos:")
    public void theSystemShouldContainTheFollowingTodos(io.cucumber.datatable.DataTable dataTable) {
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

    // Alternate Flow: Adding a new todo without a description
    @When("the user adds the following todos without descriptions:")
    public void theUserAddsTheFollowingTodosWithoutDescriptions(io.cucumber.datatable.DataTable dataTable) {
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

    @Then("the system should contain the following todos without descriptions:")
    public void theSystemShouldContainTheFollowingTodosWithoutDescriptions(io.cucumber.datatable.DataTable dataTable) {
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

    // Error Flow: Adding a new todo with missing title
    @When("the user attempts to add the following todos with missing titles:")
    public void theUserAttemptsToAddTheFollowingTodosWithMissingTitles(io.cucumber.datatable.DataTable dataTable) {
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

    @Then("a todo error message {string} should be returned")
    public void aTodoErrorMessageShouldBeReturned(String expectedErrorMessage) {
        response.then().statusCode(400);
        String responseBody = response.body().asString();
        assertTrue(responseBody.contains(expectedErrorMessage));
    }

}