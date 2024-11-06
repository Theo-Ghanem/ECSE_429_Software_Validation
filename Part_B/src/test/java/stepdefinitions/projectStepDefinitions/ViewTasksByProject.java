package stepdefinitions.projectStepDefinitions;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

import io.cucumber.java.en.And;
import static io.restassured.RestAssured.given;
import io.restassured.response.Response;
import stepdefinitions.HelperStepDefinition;

public class ViewTasksByProject extends HelperStepDefinition {

    private Response response;
    private String baseUrl = "http://localhost:4567/projects";

    @And("the following projects are recorded in the system")
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

    @And("the following todos are associated with {string}")
    public void theUserAddsTheFollowingTodos(String project, io.cucumber.datatable.DataTable dataTable) {
        List<Integer> todoIds = new ArrayList<>();
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

            int todoId = response.jsonPath().getInt("id"); // Adjust if the ID field is different
            todoIds.add(todoId);
        }
        JSONObject the_project = findProjectByName(project);

        // Retrieve the ID from the JSONObject
        int projectId = the_project.getInt("id");

        for (int id : todoIds) {
            theUserAddsTheFollowingTasks(id, projectId);
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
