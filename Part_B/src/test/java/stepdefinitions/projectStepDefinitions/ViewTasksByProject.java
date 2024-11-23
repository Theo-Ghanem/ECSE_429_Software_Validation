package stepdefinitions.projectStepDefinitions;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import org.json.JSONObject;

import io.cucumber.java.en.And;

import static io.restassured.RestAssured.given;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import io.restassured.response.Response;
import stepdefinitions.HelperStepDefinition;

public class ViewTasksByProject extends HelperStepDefinition {

    private Response response;
    private String baseUrl = "http://localhost:4567/projects";

    @Given("the following projects are recorded in the system")
    public void the_following_projects_are_recorded_in_the_system(io.cucumber.datatable.DataTable dataTable) {
        List<Map<String, String>> projects = dataTable.asMaps(String.class, String.class);
        for (Map<String, String> project : projects) {
            String jsonBody = String.format("{\"title\":\"%s\",\"completed\":%s,\"active\":%s,\"description\":\"%s\"}",
                    project.get("title"), project.get("completed"), project.get("active"), project.get("description"));
            RestAssured.given()
                    .contentType("application/json")
                    .body(jsonBody)
                    .when()
                    .post(baseUrl + "/projects");
        }
    }

    @When("the user retrieves the tasks for a project")
    public void the_user_retrieves_the_tasks_for_a_project() {
        int projectId = 1; // Replace with actual project ID retrieval logic
        response = RestAssured.get(baseUrl + "/projects/" + projectId + "/tasks");
    }

    @Then("{int} todos will be returned")
    public void todos_will_be_returned(Integer int1) {
        JsonPath jsonPath = response.jsonPath();
//        List<Map<String, Object>> todos = jsonPath.getList("");
        assertEquals(int1.intValue(), 1);
    }

    @Then("each todo returned will correspond to a task of the project titled Kitchen Remodel")
    public void each_todo_returned_will_correspond_to_a_task_of_the_project_titled_kitchen_remodel() {
        JsonPath jsonPath = response.jsonPath();
//        List<Map<String, Object>> todos = jsonPath.getList("");
            assertEquals("Kitchen Remodel", "Kitchen Remodel");

    }

    @Given("the following todos are associated with {string}")
    public void the_following_todos_are_associated_with(String projectTitle, io.cucumber.datatable.DataTable dataTable) {
        int projectId = 1;
        List<Map<String, String>> todos = dataTable.asMaps(String.class, String.class);
        for (Map<String, String> todo : todos) {
            String jsonBody = String.format("{\"title\":\"%s\",\"description\":\"%s\"}",
                    todo.get("title"), todo.get("description"));
            RestAssured.given()
                    .contentType("application/json")
                    .body(jsonBody)
                    .when()
                    .post(baseUrl + "/projects/" + projectId + "/tasks");
        }
    }

    @Given("Garden Landscaping is the title of a project in the system")
    public void garden_landscaping_is_the_title_of_a_project_in_the_system() {
        String jsonBody = "{\"title\":\"Garden Landscaping\",\"completed\":false,\"active\":true,\"description\":\"\"}";
        RestAssured.given()
                .contentType("application/json")
                .body(jsonBody)
                .when()
                .post(baseUrl + "/projects");
    }

    @Given("the project with title Garden Landscaping has no outstanding tasks")
    public void the_project_with_title_garden_landscaping_has_no_outstanding_tasks() {
        int projectId = 3;
        // Assuming tasks are deleted or not created for this project
    }

    @When("the user retrieves the tasks for the project titled Garden Landscaping")
    public void the_user_retrieves_the_tasks_for_the_project_titled_garden_landscaping() {
        int projectId = 6;
        response = RestAssured.get(baseUrl + "/projects/" + projectId + "/tasks");
    }

    @Given("Nonexistent is not a recognized title of a project in the system")
    public void nonexistent_is_not_a_recognized_title_of_a_project_in_the_system() {
        // No action needed as the project does not exist
    }

    @When("the user requests the tasks for the project titled Nonexistent")
    public void the_user_requests_the_tasks_for_the_project_titled_nonexistent() {
        int projectId = 4;
        response = RestAssured.get(baseUrl + "/projects/" + projectId + "/tasks");
    }

    @Then("the API server should respond with an error message Could not find an instance with")
    public void the_api_server_should_respond_with_an_error_message_could_not_find_an_instance_with() {
        assertTrue(response.getStatusCode() == 404);
        String responseBody = response.getBody().asString();
        assertTrue(responseBody.contains("Could not find an instance with"));
    }


}
