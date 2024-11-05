package stepdefinitions.todoStepDefinitions;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import org.json.JSONObject;
import stepdefinitions.HelperStepDefinition;

import static io.restassured.RestAssured.given;
import static org.junit.Assert.*;

public class MarkTodoAsDone extends HelperStepDefinition {

    private Response response;

    @Given("the following todos are registered in the system:")
    public void theFollowingTodosAreRegisteredInTheSystem(io.cucumber.datatable.DataTable dataTable) {
        dataTable.asMaps().forEach(row -> {
            String jsonBody = String.format(
                    "{ \"title\": \"%s\", \"doneStatus\": %b, \"description\": \"%s\" }",
                    row.get("title"), Boolean.parseBoolean(row.get("doneStatus")), row.get("description"));
            given()
                    .contentType("application/json")
                    .body(jsonBody)
                    .when()
                    .post("/todos");
        });
    }

    @Given("a todo with the title {string} exists in the system")
    public void aTodoWithTheTitleExistsInTheSystem(String title) {
        response = given().when().get("/todos");
        assertTrue(response.getBody().asString().contains(title));
    }

    @Given("the todo titled {string} is currently marked as not done")
    public void theTodoTitledIsCurrentlyMarkedAsNotDone(String title) {
        response = given().when().get("/todos");
        JSONObject todo = findTodoByName(title);
        assertEquals(false, todo.getBoolean("doneStatus"));
    }

    @When("the user marks the task titled {string} as done")
    public void theUserMarksTheTaskTitledAsDone(String title) {
        JSONObject todo = findTodoByName(title);
        int id = todo.getInt("id");
        String jsonBody = "{ \"doneStatus\": true }";
        response = given()
                .contentType("application/json")
                .body(jsonBody)
                .when()
                .patch("/todos/" + id);
    }

    @Then("the todo titled {string} should be updated to done status in the system")
    public void theTodoTitledShouldBeUpdatedToDoneStatusInTheSystem(String title) {
        JSONObject todo = findTodoByName(title);
        assertEquals(true, todo.getBoolean("doneStatus"));
    }

    @Then("the updated todo, marked as done, will be returned to the user")
    public void theUpdatedTodoMarkedAsDoneWillBeReturnedToTheUser() {
        response.then().statusCode(200);
        assertTrue(response.getBody().asString().contains("\"doneStatus\":true"));
    }

    @Given("the todo titled {string} is already marked as done")
    public void theTodoTitledIsAlreadyMarkedAsDone(String title) {
        JSONObject todo = findTodoByName(title);
        assertEquals(true, todo.getBoolean("doneStatus"));
    }

    @When("the user attempts to mark the task titled {string} as done again")
    public void theUserAttemptsToMarkTheTaskTitledAsDoneAgain(String title) {
        JSONObject todo = findTodoByName(title);
        int id = todo.getInt("id");
        String jsonBody = "{ \"doneStatus\": true }";
        response = given()
                .contentType("application/json")
                .body(jsonBody)
                .when()
                .patch("/todos/" + id);
    }

    @Then("no changes will be made to the todo in the system")
    public void noChangesWillBeMadeToTheTodoInTheSystem() {
        response.then().statusCode(200);
    }

    @Then("the todo will be returned to the user unchanged")
    public void theTodoWillBeReturnedToTheUserUnchanged() {
        response.then().statusCode(200);
    }

    @Given("no todo with the title {string} exists in the system")
    public void noTodoWithTheTitleExistsInTheSystem(String title) {
        response = given().when().get("/todos");
        assertFalse(response.getBody().asString().contains(title));
    }

    @When("the user tries to mark the non-existent task titled {string} as done")
    public void theUserTriesToMarkTheNonExistentTaskTitledAsDone(String title) {
        JSONObject todo = findTodoByName(title);
        if (todo != null) {
            int id = todo.getInt("id");
            String jsonBody = "{ \"doneStatus\": true }";
            response = given()
                    .contentType("application/json")
                    .body(jsonBody)
                    .when()
                    .patch("/todos/" + id);
        } else {
            response = given()
                    .contentType("application/json")
                    .when()
                    .patch("/todos/0");
        }
    }

    @Then("no todos in the system will be modified")
    public void noTodosInTheSystemWillBeModified() {
        response.then().statusCode(404);
    }

    @Then("the user will receive an error message indicating that the specified todo does not exist")
    public void theUserWillReceiveAnErrorMessageIndicatingThatTheSpecifiedTodoDoesNotExist() {
        response.then().statusCode(404);
        assertTrue(response.getBody().asString().contains("Could not find an instance with todos/0"));
    }
}