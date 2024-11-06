package stepdefinitions.todoStepDefinitions;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import org.json.JSONArray;
import org.json.JSONObject;
import stepdefinitions.HelperStepDefinition;

import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class DeleteTodo extends HelperStepDefinition {

    private Response response;

    @Given("the following todo instances are present in the system:")
    public void theFollowingTodoInstancesArePresentInTheSystem(io.cucumber.datatable.DataTable dataTable) {
        List<Map<String, String>> todos = dataTable.asMaps(String.class, String.class);
        for (Map<String, String> todo : todos) {
            String jsonBody = String.format(
                    "{ \"title\": \"%s\", \"doneStatus\": %b, \"description\": \"%s\" }",
                    todo.get("title"), Boolean.parseBoolean(todo.get("doneStatus")), todo.get("description"));
            given()
                    .contentType("application/json")
                    .body(jsonBody)
                    .when()
                    .post("/todos");
        }
    }

    // Normal flow
    @When("a user deletes a todo with the specified id:")
    public void aUserDeletesATodoWithTheSpecifiedId(io.cucumber.datatable.DataTable dataTable) {
        List<Map<String, String>> todos = dataTable.asMaps(String.class, String.class);
        for (Map<String, String> todo : todos) {
            response = given()
                    .when()
                    .delete("/todos/" + todo.get("id"));
        }
    }

    @Then("the following todos should no longer be present in the system:")
    public void theFollowingTodosShouldNoLongerBePresentInTheSystem(io.cucumber.datatable.DataTable dataTable) {
        List<Map<String, String>> todos = dataTable.asMaps(String.class, String.class);
        for (Map<String, String> todo : todos) {
            response = given()
                    .when()
                    .get("/todos/" + todo.get("id"));
            assertFalse(response.getBody().asString().contains(todo.get("title")));
        }
    }

    // Alternate flow
    @When("a user deletes an initial todo with the specified id:")
    public void aUserDeletesAnInitialTodoWithTheSpecifiedId(io.cucumber.datatable.DataTable dataTable) {
        List<Map<String, String>> todos = dataTable.asMaps(String.class, String.class);
        for (Map<String, String> todo : todos) {
            response = given()
                    .when()
                    .delete("/todos/" + todo.get("id"));
        }
    }

    @Then("the following initial todos should no longer be present in the system:")
    public void theFollowingInitialTodosShouldNoLongerBePresentInTheSystem(io.cucumber.datatable.DataTable dataTable) {
        List<Map<String, String>> todos = dataTable.asMaps(String.class, String.class);
        for (Map<String, String> todo : todos) {
            response = given()
                    .when()
                    .get("/todos/" + todo.get("id"));
            assertFalse(response.getBody().asString().contains(todo.get("title")));
        }
    }

    // Error flow
    @When("a user tries to delete a todo with an id that doesn’t exist:")
    public void aUserTriesToDeleteATodoWithAnIdThatDoesntExist(io.cucumber.datatable.DataTable dataTable) {
        List<Map<String, String>> todos = dataTable.asMaps(String.class, String.class);
        for (Map<String, String> todo : todos) {
            response = given()
                    .when()
                    .delete("/todos/" + todo.get("id"));
        }
    }

    @Then("the following todos should not be found in the system:")
    public void theFollowingTodosShouldNotBeFoundInTheSystem(io.cucumber.datatable.DataTable dataTable) {
        List<Map<String, String>> todos = dataTable.asMaps(String.class, String.class);
        for (Map<String, String> todo : todos) {
            String id = todo.get("id");
            String expectedError = todo.get("error");
            System.out.println("Checking if todo with id " + id + " is not found in the system.");
            response = given()
                    .when()
                    .get("/todos/" + id);
            String responseBody = response.getBody().asString();
            System.out.println("Response body: " + responseBody);
            JSONObject jsonResponse = new JSONObject(responseBody);
            JSONArray errorMessages = jsonResponse.getJSONArray("errorMessages");
            boolean errorFound = false;
            for (int i = 0; i < errorMessages.length(); i++) {
                if (errorMessages.getString(i).equals(expectedError)) {
                    errorFound = true;
                    break;
                }
            }
            assertTrue("Expected error message not found in response body for id " + id, errorFound);
        }
    }

    @Then("the server should raise an error:")
    public void theServerShouldRaiseAnError(io.cucumber.datatable.DataTable dataTable) {
        List<Map<String, String>> todos = dataTable.asMaps(String.class, String.class);
        for (Map<String, String> todo : todos) {
            assertTrue(response.getBody().asString().contains(todo.get("error")));
        }
    }
}