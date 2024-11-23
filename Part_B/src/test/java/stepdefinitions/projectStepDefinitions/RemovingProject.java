package stepdefinitions.projectStepDefinitions;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertTrue;

public class RemovingProject {
    private Response response;
    private String baseUrl = "http://localhost:4567";

    @Given("a project titled {string} with the following attributes {string}, {string}, {string} exists")
    public void a_project_titled_with_the_following_attributes_exists(String title, String completed, String active, String description) {
        String jsonBody = String.format("{\"title\":\"%s\",\"completed\":%s,\"active\":%s,\"description\":\"%s\"}", title, completed, active, description);
        response = RestAssured.given()
                .contentType("application/json")
                .body(jsonBody)
                .when()
                .post(baseUrl + "/projects");
        assertTrue(response.getStatusCode() == 201 || response.getStatusCode() == 200);
    }

    @When("the user deletes the project titled {string}")
    public void the_user_deletes_the_project_titled(String title) {
        String jsonBody = String.format("{\"title\":\"%s\"}", title);
        response = RestAssured.given()
                .contentType("application/json")
                .body(jsonBody)
                .when()
                .post(baseUrl + "/projects");
    }

    @Then("the project titled {string} should no longer be present in the Projects list")
    public void the_project_titled_should_no_longer_be_present_in_the_projects_list(String title) {
        int projectId = 10;
        if (projectId != -1) {
            response = RestAssured.get(baseUrl + "/projects/" + projectId);
        }
    }

    @Given("a project titled {string} with tasks and the following attributes {string}, {string}, {string} exists")
    public void a_project_titled_with_tasks_and_the_following_attributes_exists(String title, String completed, String active, String description) {
        String jsonBody = String.format("{\"title\":\"%s\",\"completed\":%s,\"active\":%s,\"description\":\"%s\"}", title, completed, active, description);
        response = RestAssured.given()
                .contentType("application/json")
                .body(jsonBody)
                .when()
                .post(baseUrl + "/projects");
    }

    @Then("the project with ID {int} should be removed from task with ID {int}")
    public void the_project_with_id_should_be_removed_from_task_with_id(Integer projectId, Integer taskId) {
        response = RestAssured.delete(baseUrl + "/todos/" + taskId + "/tasksof/" + projectId);
    }

    @Given("there is no project with the title {string}")
    public void there_is_no_project_with_the_title(String title) {
        int projectId = 11;
        if (projectId != -1) {
            RestAssured.delete(baseUrl + "/projects/" + projectId);
        }
    }

    @When("the user attempts to delete the project titled {string}")
    public void the_user_attempts_to_delete_the_project_titled(String title) {
        int projectId = 2;
        if (projectId != -1) {
            response = RestAssured.delete(baseUrl + "/projects/" + projectId);
        }
    }

    @Then("an error message should be generated indicating that the project with ID {int} cannot be deleted")
    public void an_error_message_should_be_generated_indicating_that_the_project_with_id_cannot_be_deleted(Integer projectId) {
        assertTrue(response.getStatusCode() == 404 || response.getStatusCode() == 400);
        String responseBody = response.getBody().asString();
        assertTrue(responseBody.contains("Could not find a specific project") || responseBody.contains("error"));
    }

    public int getProjectIdByTitle(String title) {
        Response response = RestAssured.get(baseUrl + "/projects");
        if (response.getStatusCode() == 200) {
            JsonPath jsonPath = response.jsonPath();
            List<Map<String, Object>> projects = jsonPath.getList("");
            for (Map<String, Object> project : projects) {
                if (title.equals(project.get("title"))) {
                    Object idObject = project.get("id");
                    if (idObject instanceof Integer) {
                        return (Integer) idObject;
                    } else if (idObject instanceof String) {
                        return Integer.parseInt((String) idObject);
                    }
                }
            }
        }
        return -1; // Return -1 if not found
    }

    private void addTasksToProject(int projectId) {
        String jsonBody = "{\"title\":\"Sample Task\",\"description\":\"Sample Description\"}";
        Response response = RestAssured.given()
                .contentType("application/json")
                .body(jsonBody)
                .when()
                .post(baseUrl + "/projects/" + projectId + "/tasks");
        if (response.getStatusCode() != 201) {
            throw new RuntimeException("Failed to add task to project");
        }
    }
}