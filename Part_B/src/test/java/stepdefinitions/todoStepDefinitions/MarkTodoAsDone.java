package stepdefinitions.todoStepDefinitions;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import org.json.JSONArray;
import org.json.JSONObject;
import stepdefinitions.HelperStepDefinition;

import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;

public class MarkTodoAsDone extends HelperStepDefinition {

    private Response response;

    @When("a user marks the following todos as done:")
    public void aUserMarksTheFollowingTodosAsDone(io.cucumber.datatable.DataTable dataTable) {
        List<Map<String, String>> todos = dataTable.asMaps(String.class, String.class);
        for (Map<String, String> todo : todos) {
            String jsonBody = String.format(
                    "{ \"title\": \"%s\", \"doneStatus\": true, \"description\": \"%s\" }",
                    todo.get("title"), todo.get("description"));
            response = given()
                    .contentType("application/json")
                    .body(jsonBody)
                    .when()
                    .put("/todos/" + todo.get("id"));
        }
    }

    @Then("the following todos should be marked as done:")
    public void theFollowingTodosShouldBeMarkedAsDone(io.cucumber.datatable.DataTable dataTable) {
        List<Map<String, String>> todos = dataTable.asMaps(String.class, String.class);
        for (Map<String, String> todo : todos) {
            response = given()
                    .when()
                    .get("/todos/" + todo.get("id"));

            JSONObject jsonResponse = new JSONObject(response.getBody().asString());
            JSONArray todosArray = jsonResponse.getJSONArray("todos");

            for (int i = 0; i < todosArray.length(); i++) {
                JSONObject todoItem = todosArray.getJSONObject(i);
                assertEquals("Todo should be marked as done", "true", todoItem.getString("doneStatus"));
                assertEquals("Title should match", todo.get("title"), todoItem.getString("title"));
                assertEquals("Description should match", todo.get("description"), todoItem.getString("description"));
            }
        }
    }

    @When("a user marks the following todo as done:")
    public void aUserMarksTheFollowingTodoAsDone(io.cucumber.datatable.DataTable dataTable) {
        aUserMarksTheFollowingTodosAsDone(dataTable);
    }

    @When("the user marks the same todo as done again:")
    public void theUserMarksTheSameTodoAsDoneAgain(io.cucumber.datatable.DataTable dataTable) {
        aUserMarksTheFollowingTodosAsDone(dataTable);
    }

    @Then("the todo should remain marked as done:")
    public void theTodoShouldRemainMarkedAsDone(io.cucumber.datatable.DataTable dataTable) {
        theFollowingTodosShouldBeMarkedAsDone(dataTable);
    }

    @When("a user tries to mark a non-existing todo as done:")
    public void aUserTriesToMarkANonExistingTodoAsDone(io.cucumber.datatable.DataTable dataTable) {
        List<Map<String, String>> todos = dataTable.asMaps(String.class, String.class);
        for (Map<String, String> todo : todos) {
            String jsonBody = "{ \"doneStatus\": true }";
            response = given()
                    .contentType("application/json")
                    .body(jsonBody)
                    .when()
                    .put("/todos/" + todo.get("id"));
        }
    }

    @Then("the server should return a not found error:")
public void theServerShouldReturnANotFoundError(io.cucumber.datatable.DataTable dataTable) {
    List<Map<String, String>> todos = dataTable.asMaps(String.class, String.class);
    for (Map<String, String> todo : todos) {
        String expectedError = todo.get("error");
        String responseBody = response.getBody().asString();
        System.out.println("Response body: " + responseBody);

        JSONObject jsonResponse = new JSONObject(responseBody);
        JSONArray errorMessages = jsonResponse.getJSONArray("errorMessages");
        System.out.println("Error messages: " + errorMessages.toString());

        boolean errorFound = false;
        for (int i = 0; i < errorMessages.length(); i++) {
            if (errorMessages.length()>0 ) {
                errorFound = true;
                break;
            }
        }
        assertTrue("Expected error message not found in response", errorFound);
    }
}
}