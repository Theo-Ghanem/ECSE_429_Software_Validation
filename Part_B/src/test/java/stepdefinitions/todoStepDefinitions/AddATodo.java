package stepdefinitions.todoStepDefinitions;

import io.cucumber.java.en.*;
import io.cucumber.datatable.DataTable;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.json.JSONObject;
import stepdefinitions.HelperMethods;
import org.json.JSONObject;

import java.util.List;

import static io.restassured.RestAssured.*;
import static org.junit.Assert.*;

public class AddATodo extends HelperMethods {

//    private Response response;

    @Given("the API server is running")
    public void theApiServerIsRunning() {
        given().when().get("http://localhost:4567/docs").then().statusCode(200);
    }

    @When("the user create a todo with a title, a doneStatus and a description:")
    public void the_following_todos_are_created(DataTable table) {
        List<List<String>> rows = table.asLists(String.class); // instances of todo
        boolean isFirstRow = true; // Variable to ignore the title row

        for (List<String> columns : rows) {
            // Ignore the title row
            if (isFirstRow) {
                isFirstRow = false;
                continue;
            }

            boolean doneStatus = Boolean.parseBoolean(columns.get(1)); // Parse doneStatus
            String title = columns.get(0); // Title of the todo
            String description = columns.get(2); // Description of the todo

            // Create a JSONObject for the body
            JSONObject todoJson = new JSONObject();
            todoJson.put("doneStatus", doneStatus);
            todoJson.put("description", description);  // No need to manually escape quotes
            todoJson.put("title", title);  // Same here

            // Send the POST request with the JSON body
            Response response =  given()
                    .contentType("application/json")
                    .body(todoJson.toString())  // Convert JSONObject to string and send
                    .post("/todos");
            JsonPath jsonPath = response.jsonPath();
        }
    }


    @Then("the following todos should be present in the system:")
    public void the_following_todos_shall_exist_in_the_system(DataTable table) {
        List<List<String>> rows = table.asLists(String.class); // instances of todo
        boolean title = true;
        for (List<String> columns : rows) {
            // ignore title row
            if (!title) {
                JsonPath todo = getTodoByTitle(columns.get(0));
                assertNotNull(todo);
                assertEquals(columns.get(0), todo.getString("title"));
                assertEquals(columns.get(1), todo.getString("doneStatus"));
                assertEquals(columns.get(2), todo.getString("description"));
            }
            title = false;
        }
    }


    @When("the user adds a todo with the following details:")
    public void a_user_create_a_todo_with_a_title_a_doneStatus_an_empty_description(DataTable table) {
        List<List<String>> rows = table.asLists(String.class); // instances of todo
        boolean isFirstRow = true; // Variable to ignore the title row

        for (List<String> columns : rows) {
            // Ignore the title row
            if (isFirstRow) {
                isFirstRow = false;
                continue;
            }

            boolean doneStatus = Boolean.parseBoolean(columns.get(1)); // Parse doneStatus
            String title = columns.get(0);
            String description = columns.get(2);

            // Create JSON object for the body
            JSONObject todoJson = new JSONObject();
            todoJson.put("doneStatus", doneStatus);
            todoJson.put("description", description);  // No need to manually escape quotes
            todoJson.put("title", title);  // Same here

            // Send the POST request with the JSON body
            Response response = given()
                    .contentType("application/json")
                    .body(todoJson.toString())  // Convert the JSONObject to a string
                    .post("/todos");

            JsonPath jsonPath = response.jsonPath();
        }
    }



    @Then("the following todos should still be present in the system:")
    public void the_following_todos_shall_still_exist_in_the_system(DataTable table) {
        List<List<String>> rows = table.asLists(String.class); // instances of todo
        boolean title = true;
        for (List<String> columns : rows) {
            // ignore title row
            if (!title) {
                JsonPath todo = getTodoByTitle(columns.get(0));
                assertNotNull(todo);
                assertEquals(columns.get(0), todo.getString("title"));
                assertEquals(columns.get(1), todo.getString("doneStatus"));
                assertEquals(String.valueOf(columns.get(2)), todo.getString("description"));
            }
            title = false;
        }
    }

    @When("the user attempts to add a todo with the following details:")
    public void a_user_create_a_todo_with_an_empty_title(DataTable table) {
        List<List<String>> rows = table.asLists(String.class); // instances of todo
        boolean title = true;
        for (List<String> columns : rows) {
            // ignore title row
            if (!title) {
                given()
                        .contentType("application/json")
                        .body("{\n\"doneStatus\":\"" + Boolean.parseBoolean(columns.get(1)) + "\",\n  \"description\":\""
                                + columns.get(2) + "\",\n  \"title\":\"" + columns.get(0) + "\"\n}")
                        .post("/todos");
            }
            title = false;
        }
    }

    @Then(" an error message \"Title is required\" should be returned")
    public void the_following_todos_shall_not_exist(DataTable table) {
        List<List<String>> rows = table.asLists(String.class); // instances of todo
        boolean title = true;
        for (List<String> columns : rows) {
            // ignore title row
            if (!title) {
                JsonPath todo = getTodoByTitle(columns.get(0));
                assertNull(todo);
            }
            title = false;
        }
    }

    @Then("the server will raise an error")
    public void the_server_will_raise_an_error(DataTable table) {
        List<List<String>> rows = table.asLists(String.class); // instances of todo
        boolean title = true;
        for (List<String> columns : rows) {
            // ignore title row
            if (!title) {
                assertEquals("Failed Validation: title : can not be empty", columns.get(3));
            }
            title = false;
        }
    }
}