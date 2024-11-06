package stepdefinitions.projectStepDefinitions;

import java.util.List;
import java.util.Map;

import org.json.JSONObject;
import static org.junit.Assert.assertTrue;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import static io.restassured.RestAssured.given;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import stepdefinitions.HelperStepDefinition;

public class CreateProjectWithFields extends HelperStepDefinition {

    private Response response;
    private String baseUrl = "http://localhost:4567/projects";

    @When("the user creates a project without tasks")
    public void i_create_a_project_with_title_and_description(io.cucumber.datatable.DataTable dataTable) {
        for (var row : dataTable.asLists()) {
            String title = row.get(0);
            if (title == null) {
                title = "";
            }
            boolean completed = Boolean.parseBoolean(row.get(1));
            boolean active = Boolean.parseBoolean(row.get(2));
            String description = row.get(3);
            if (description == null) {
                description = "";
            }

            String jsonBody = String.format(
                    "{ \"title\": \"%s\", \"completed\": %b, \"active\": %b, \"description\": \"%s\" }",
                    title, completed, active, description);

            response = given()
                    .contentType("application/json")
                    .body(jsonBody)
                    .when()
                    .post(baseUrl);
        }
    }

    @Then("a new project with the specified fields should appear")
    public void aNewProjectShouldAppear(io.cucumber.datatable.DataTable dataTable) {
        for (var row : dataTable.asLists()) {
            String title = row.get(0);
            if (title == null) {
                title = "";
            }
            boolean completed = Boolean.parseBoolean(row.get(1));
            boolean active = Boolean.parseBoolean(row.get(2));
            String description = row.get(3);
            if (description == null) {
                description = "";
            }

            response = given().when().get(baseUrl);
            response.then().statusCode(200);
            String responseBody = response.body().asString();

            JsonPath jsonPath = new JsonPath(responseBody);
            List<Map<String, Object>> todos = jsonPath.getList("projects");

            boolean found = false;
            for (Map<String, Object> todo : todos) {
                if (todo.get("title").equals(title) &&
                        todo.get("completed").toString().equals(String.valueOf(completed)) &&
                        todo.get("active").toString().equals(String.valueOf(active)) &&
                        (todo.get("description").equals(description)
                                || (todo.get("description").equals("") && description == null))) {
                    found = true;
                    break;
                }
            }

            assertTrue("Expected title not found in response body.", found);
        }
    }

    @When("the user creates a project with a linked tasks")
    public void createAProjectWithLinkedTasks(io.cucumber.datatable.DataTable dataTable) {
        for (var row : dataTable.asLists()) {
            int header = 0;
            int toDoID = -1;
            String title = row.get(0);
            if (title == null) {
                title = "";
            }
            boolean completed = Boolean.parseBoolean(row.get(1));
            boolean active = Boolean.parseBoolean(row.get(2));
            String description = row.get(3);
            if (description == null) {
                description = "";
            }
            if (row.get(4).contains("todo id")) {
                header = 1;
            } else {
                toDoID = Integer.parseInt(row.get(4));
                header = 0;
            }

            String jsonBody = String.format(
                    "{ \"title\": \"%s\", \"completed\": %b, \"active\": %b, \"description\": \"%s\" }",
                    title, completed, active, description);

            response = given()
                    .contentType("application/json")
                    .body(jsonBody)
                    .when()
                    .post(baseUrl);

            int projectId = -1;

            if (header == 0 && response.getStatusCode() == 201) {
                // Parse the response body to retrieve the project ID
                String responseBody = response.getBody().asString();
                JSONObject jsonResponse = new JSONObject(responseBody);
                projectId = jsonResponse.getInt("id");
            }

            theUserAddsTheFollowingTasks(toDoID, projectId);
        }
    }

    @Then("a new project with specified fields and linked tasks should be present under Projects")
    public void aNewProjectShouldAppearWithTasksOf(io.cucumber.datatable.DataTable dataTable) {
        for (var row : dataTable.asLists()) {
            String title = row.get(0);
            if (title == null) {
                title = "";
            }
            boolean completed = Boolean.parseBoolean(row.get(1));
            boolean active = Boolean.parseBoolean(row.get(2));
            String description = row.get(3);
            if (description == null) {
                description = "";
            }

            response = given().when().get(baseUrl);
            response.then().statusCode(200);
            String responseBody = response.body().asString();

            JsonPath jsonPath = new JsonPath(responseBody);
            List<Map<String, Object>> todos = jsonPath.getList("projects");

            boolean found = false;
            for (Map<String, Object> todo : todos) {
                if (todo.get("title").equals(title) &&
                        todo.get("completed").toString().equals(String.valueOf(completed)) &&
                        todo.get("active").toString().equals(String.valueOf(active)) &&
                        (todo.get("description").equals(description)
                                || (todo.get("description").equals("") && description == null))) {
                    found = true;
                    break;
                }
            }

            assertTrue("Expected title not found in response body.", found);
        }
    }

    public void theUserAddsTheFollowingTasks(int todoID, int projectID) {

        String tasksOfUrl = "http://localhost:4567/projects/";

        String jsonBody = String.format("{ \"title\": \"titled task\"}");

        response = given()
                .contentType("application/json")
                .body(jsonBody)
                .when()
                .post(tasksOfUrl + projectID + "/tasks");

        int taskID = -1;
        if (response.getStatusCode() == 201) {
            // Parse the response body to retrieve the project ID
            String responseBody = response.getBody().asString();
            JSONObject jsonResponse = new JSONObject(responseBody);
            taskID = jsonResponse.getInt("id");
        }

        tasksOfUrl = "http://localhost:4567/todos/";

        jsonBody = String.format(
                "{ \"tasks\": [ { \"id\": \"%d\" } ] }",
                taskID);

        response = given()
                .contentType("application/json")
                .body(jsonBody)
                .when()
                .post(tasksOfUrl + todoID + "/tasksof");
    }
}