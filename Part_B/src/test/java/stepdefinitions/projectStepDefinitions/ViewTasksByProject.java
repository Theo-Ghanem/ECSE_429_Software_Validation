package stepdefinitions.projectStepDefinitions;

import java.util.ArrayList;
import java.util.List;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.json.JSONObject;

import io.cucumber.java.en.And;
import static io.restassured.RestAssured.given;
import io.restassured.response.Response;
import stepdefinitions.HelperStepDefinition;

public class ViewTasksByProject extends HelperStepDefinition {

    private Response response;
    private String baseUrl = "http://localhost:4567/projects";

//    @And("the following projects are recorded in the system")
//    public void i_create_a_project_with_title_and_description(io.cucumber.datatable.DataTable dataTable) {
//        for (var row : dataTable.asLists()) {
//            String title = row.get(0);
//            if (title == null) {
//                title = "";
//            }
//            boolean completed = Boolean.parseBoolean(row.get(1));
//            boolean active = Boolean.parseBoolean(row.get(2));
//            String description = row.get(3);
//            if (description == null) {
//                description = "";
//            }
//
//            String jsonBody = String.format(
//                    "{ \"title\": \"%s\", \"completed\": %b, \"active\": %b, \"description\": \"%s\" }",
//                    title, completed, active, description);
//
//            response = given()
//                    .contentType("application/json")
//                    .body(jsonBody)
//                    .when()
//                    .post(baseUrl);
//        }
//    }
//
//    @And("the following todos are associated with {string}")
//    public void theUserAddsTheFollowingTodos(String project, io.cucumber.datatable.DataTable dataTable) {
//        List<Integer> todoIds = new ArrayList<>();
//        for (var row : dataTable.asLists()) {
//            String title = row.get(0);
//            if (title == null) {
//                title = "";
//            }
//            boolean doneStatus = Boolean.parseBoolean(row.get(1));
//            String description = row.get(2);
//            if (description == null) {
//                description = "";
//            }
//
//            String jsonBody = String.format(
//                    "{ \"title\": \"%s\", \"doneStatus\": %b, \"description\": \"%s\" }",
//                    title, doneStatus, description);
//
//            response = given()
//                    .contentType("application/json")
//                    .body(jsonBody)
//                    .when()
//                    .post(baseUrl);
//
//            int todoId = response.jsonPath().getInt("id"); // Adjust if the ID field is different
//            todoIds.add(todoId);
//        }
//        JSONObject the_project = findProjectByName(project);
//
//        // Retrieve the ID from the JSONObject
//        int projectId = the_project.getInt("id");
//
//        for (int id : todoIds) {
//            theUserAddsTheFollowingTasks(id, projectId);
//        }
//
//    }
//
//    public void theUserAddsTheFollowingTasks(int todoID, int projectID) {
//
//        String tasksOfUrl = "http://localhost:4567/projects/";
//
//        String jsonBody = String.format("{ \"title\": \"titled task\"}");
//
//        response = given()
//                .contentType("application/json")
//                .body(jsonBody)
//                .when()
//                .post(tasksOfUrl + projectID + "/tasks");
//
//        int taskID = -1;
//        if (response.getStatusCode() == 201) {
//            // Parse the response body to retrieve the project ID
//            String responseBody = response.getBody().asString();
//            JSONObject jsonResponse = new JSONObject(responseBody);
//            taskID = jsonResponse.getInt("id");
//        }
//
//        tasksOfUrl = "http://localhost:4567/todos/";
//
//        jsonBody = String.format(
//                "{ \"tasks\": [ { \"id\": \"%d\" } ] }",
//                taskID);
//
//        response = given()
//                .contentType("application/json")
//                .body(jsonBody)
//                .when()
//                .post(tasksOfUrl + todoID + "/tasksof");
//    }



    @Given("the following projects are recorded in the system")
    public void the_following_projects_are_recorded_in_the_system(io.cucumber.datatable.DataTable dataTable) {
        // Write code here that turns the phrase above into concrete actions
        // For automatic transformation, change DataTable to one of
        // E, List<E>, List<List<E>>, List<Map<K,V>>, Map<K,V> or
        // Map<K, List<V>>. E,K,V must be a String, Integer, Float,
        // Double, Byte, Short, Long, BigInteger or BigDecimal.
        //
        // For other transformations you can register a DataTableType.
        
    }
    @When("the user retrieves the tasks for a project")
    public void the_user_retrieves_the_tasks_for_a_project() {
        // Write code here that turns the phrase above into concrete actions
        
    }
    @Then("{int} todos will be returned")
    public void todos_will_be_returned(Integer int1) {
        // Write code here that turns the phrase above into concrete actions
        
    }
    @Then("each todo returned will correspond to a task of the project titled Kitchen Remodel")
    public void each_todo_returned_will_correspond_to_a_task_of_the_project_titled_kitchen_remodel() {
        // Write code here that turns the phrase above into concrete actions
        
    }


    @Given("the following todos are associated with {string}")
    public void the_following_todos_are_associated_with(String string, io.cucumber.datatable.DataTable dataTable) {
        // Write code here that turns the phrase above into concrete actions
        // For automatic transformation, change DataTable to one of
        // E, List<E>, List<List<E>>, List<Map<K,V>>, Map<K,V> or
        // Map<K, List<V>>. E,K,V must be a String, Integer, Float,
        // Double, Byte, Short, Long, BigInteger or BigDecimal.
        //
        // For other transformations you can register a DataTableType.
        
    }
    @Given("Garden Landscaping is the title of a project in the system")
    public void garden_landscaping_is_the_title_of_a_project_in_the_system() {
        // Write code here that turns the phrase above into concrete actions
        
    }
    @Given("the project with title Garden Landscaping has no outstanding tasks")
    public void the_project_with_title_garden_landscaping_has_no_outstanding_tasks() {
        // Write code here that turns the phrase above into concrete actions
        
    }
    @When("the user retrieves the tasks for the project titled Garden Landscaping")
    public void the_user_retrieves_the_tasks_for_the_project_titled_garden_landscaping() {
        // Write code here that turns the phrase above into concrete actions
        
    }



    @Given("Nonexistent is not a recognized title of a project in the system")
    public void nonexistent_is_not_a_recognized_title_of_a_project_in_the_system() {
        // Write code here that turns the phrase above into concrete actions
        
    }
    @When("the user requests the tasks for the project titled Nonexistent")
    public void the_user_requests_the_tasks_for_the_project_titled_nonexistent() {
        // Write code here that turns the phrase above into concrete actions
        
    }
    @Then("the API server should respond with an error message Could not find an instance with")
    public void the_api_server_should_respond_with_an_error_message_could_not_find_an_instance_with() {
        // Write code here that turns the phrase above into concrete actions
        
    }


}
