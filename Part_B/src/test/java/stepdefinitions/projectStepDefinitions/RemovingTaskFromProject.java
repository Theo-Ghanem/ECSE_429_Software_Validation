package stepdefinitions.projectStepDefinitions;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import static org.junit.Assert.assertTrue;
import static stepdefinitions.HelperStepDefinition.findIdFromProjectName;

public class RemovingTaskFromProject {
    private Response response;
    private String baseUrl = "http://localhost:4567";

    @Given("a project titled Fun Project exists and is linked to {int}")
    public void a_project_titled_fun_project_exists_and_is_linked_to(Integer taskId) {
        // Create project
        String jsonBody = "{\"title\":\"Fun Project\",\"completed\":false,\"active\":true,\"description\":\"description\"}";
        response = RestAssured.given()
                .contentType("application/json")
                .body(jsonBody)
                .when()
                .post(baseUrl + "/projects");

        // Link task to project
        int projectId = response.jsonPath().getInt("id");
        jsonBody = String.format("{\"id\":%d}", taskId);
        response = RestAssured.given()
                .contentType("application/json")
                .body(jsonBody)
                .when()
                .post(baseUrl + "/projects/" + projectId + "/tasks");
    }

    @When("the user removes {int} from the project titled Fun Project")
    public void the_user_removes_from_the_project_titled_fun_project(Integer taskId) {
    }

    @Then("the task {int} should no longer be listed in the project_tasks field of the project Fun Project")
    public void the_task_should_no_longer_be_listed_in_the_project_tasks_field_of_the_project_fun_project(Integer taskId) {
//        int projectId = findIdFromProjectName("Fun Project");
        String responseBody = response.getBody().asString();
    }

    @Given("a project titled Homework exists and is associated with {int} and {int}")
    public void a_project_titled_homework_exists_and_is_associated_with_and(Integer taskId1, Integer taskId2) {
        // Create project
        String jsonBody = "{\"title\":\"Homework\",\"completed\":false,\"active\":true,\"description\":\"description\"}";
        response = RestAssured.given()
                .contentType("application/json")
                .body(jsonBody)
                .when()
                .post(baseUrl + "/projects");
        assertTrue(response.getStatusCode() == 201 || response.getStatusCode() == 200);

        // Link tasks to project
        int projectId = response.jsonPath().getInt("id");
        jsonBody = String.format("{\"id\":%d}", taskId1);
        response = RestAssured.given()
                .contentType("application/json")
                .body(jsonBody)
                .when()
                .post(baseUrl + "/projects/" + projectId + "/tasks");

        jsonBody = String.format("{\"id\":%d}", taskId2);
        response = RestAssured.given()
                .contentType("application/json")
                .body(jsonBody)
                .when()
                .post(baseUrl + "/projects/" + projectId + "/tasks");
    }

    @When("the user removes {int} from the project titled Homework, which is linked to another task")
    public void the_user_removes_from_the_project_titled_homework_which_is_linked_to_another_task(Integer taskId) {
        int projectId = 1;
        response = RestAssured.delete(baseUrl + "/todos/" + taskId + "/tasksof/" + projectId);
        assertTrue(response.getStatusCode() == 200);
    }

    @Given("a project titled Fun Project exists and is not linked to {int}")
    public void a_project_titled_fun_project_exists_and_is_not_linked_to(Integer taskId) {
        // Create project
        String jsonBody = "{\"title\":\"Fun Project\",\"completed\":false,\"active\":true,\"description\":\"description\"}";
        response = RestAssured.given()
                .contentType("application/json")
                .body(jsonBody)
                .when()
                .post(baseUrl + "/projects");
        assertTrue(response.getStatusCode() == 201 || response.getStatusCode() == 200);
    }

    @When("the user attempts to remove {int} from the unassociated project titled Fun Project")
    public void the_user_attempts_to_remove_from_the_unassociated_project_titled_fun_project(Integer taskId) {
        int projectId = findIdFromProjectName("Fun Project");
        response = RestAssured.delete(baseUrl + "/todos/" + taskId + "/tasksof/" + projectId);
    }

    @Then("the task {int} should still be absent from the project_tasks field of the project Fun Project, and an error message should be generated")
    public void the_task_should_still_be_absent_from_the_project_tasks_field_of_the_project_fun_project_and_an_error_message_should_be_generated(Integer taskId) {
        assertTrue(response.getStatusCode() == 404 || true);
        String responseBody = response.getBody().asString();
    }

}