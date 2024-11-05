package stepdefinitions.todoStepDefinitions;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import stepdefinitions.HelperStepDefinition;

import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.junit.Assert.assertTrue;

public class ModifyTodoDescription extends HelperStepDefinition {
    private Response response;
    private String baseUrl = "http://localhost:4567/todos";

    @Given("the following todo exists:")
    public void theFollowingTodoExists(io.cucumber.datatable.DataTable dataTable) {
        for (var row : dataTable.asLists()) {
            String title = row.get(0);
            boolean doneStatus = Boolean.parseBoolean(row.get(1));
            String description = row.get(2);

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

    // Normal Flow
    @When("the user modifies the description of the todo with title {string} to {string}")
    public void theUserModifiesTheDescriptionOfTheTodoWithTitleTo(String title, String newDescription) {
        String jsonBody = String.format("{ \"description\": \"%s\" }", newDescription);

        response = given()
                .contentType("application/json")
                .body(jsonBody)
                .when()
                .put(baseUrl + "/" + getTodoIdByTitle(title));
    }

    @Then("the system should contain the following todo with the modified descriptions:")
    public void theSystemShouldContainTheFollowingTodoWithTheModifiedDescriptions(io.cucumber.datatable.DataTable dataTable) {
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
                        todo.get("description").equals(description)) {
                    found = true;
                    break;
                }
            }

            assertTrue("Expected todo not found in response body.", found);
        }
    }

    // Alternate Flow
    @When("the user modifies the description of the todo with title {string} to an empty string")
    public void theUserModifiesTheDescriptionOfTheTodoWithTitleToAnEmptyString(String title) {
        String jsonBody = "{ \"description\": \"\" }";

        response = given()
                .contentType("application/json")
                .body(jsonBody)
                .when()
                .put(baseUrl + "/" + getTodoIdByTitle(title));
    }

    @Then("the system should contain the following todo:")
    public void theSystemShouldContainTheFollowingTodo(io.cucumber.datatable.DataTable dataTable) {
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
                        todo.get("description").equals(description)) {
                    found = true;
                    break;
                }
            }

            assertTrue("Expected todo not found in response body.", found);
        }
    }

    // Error Flow
    @When("the user attempts to modify the description of a non-existent todo with title {string} to {string}")
    public void theUserAttemptsToModifyTheDescriptionOfANonExistentTodoWithTitleTo(String title, String newDescription) {
        String jsonBody = String.format("{ \"description\": \"%s\" }", newDescription);

        response = given()
                .contentType("application/json")
                .body(jsonBody)
                .when()
                .put(baseUrl + "/non-existent-id");
    }

    @Then("a todo description error message {string} should be returned")
    public void aTodoDescriptionErrorMessageShouldBeReturned(String expectedErrorMessage) {
        response.then().statusCode(404);
        String responseBody = response.body().asString();
        assertTrue(responseBody.contains(expectedErrorMessage));
    }

    private String getTodoIdByTitle(String title) {
        response = given().when().get(baseUrl);
        response.then().statusCode(200);
        String responseBody = response.body().asString();

        JsonPath jsonPath = new JsonPath(responseBody);
        List<Map<String, Object>> todos = jsonPath.getList("todos");

        for (Map<String, Object> todo : todos) {
            if (todo.get("title").equals(title)) {
                return todo.get("id").toString();
            }
        }

        return null;
    }
}