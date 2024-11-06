package stepdefinitions.todoStepDefinitions;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import stepdefinitions.HelperStepDefinition;

import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.junit.Assert.assertTrue;

public class ModifyTodoDescription extends HelperStepDefinition {

    private Response response;
    private String baseUrl = "http://localhost:4567/todos";

//    @Given("the following todo instances with descriptions are present in the system:")
//    public void theFollowingTodoInstancesWithDescriptionArePresentInTheSystem(io.cucumber.datatable.DataTable dataTable) {
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
//        }
//    }
//
//    // Normal flow
//    @When("a user modifies the description of a todo with the specified title:")
//    public void aUserModifiesTheDescriptionOfATodoWithTheSpecifiedTitle(io.cucumber.datatable.DataTable dataTable) {
//        List<Map<String, String>> todos = dataTable.asMaps(String.class, String.class);
//        for (Map<String, String> todo : todos) {
//            String title = todo.get("title");
//            String jsonBody = String.format(
//                    "{ \"description\": \"%s\" }",
//                    todo.get("description"));
//            int id = findIdFromTodoName(title);
//            response = given()
//                    .contentType("application/json")
//                    .body(jsonBody)
//                    .when()
//                    .put(baseUrl + "/" + id);
//        }
//    }
//
//    @Then("the following todos should be present in the system with updated descriptions:")
//    public void theFollowingTodosShouldBePresentInTheSystemWithUpdatedDescriptions(io.cucumber.datatable.DataTable dataTable) {
//        List<Map<String, String>> todos = dataTable.asMaps(String.class, String.class);
//        for (Map<String, String> todo : todos) {
//            String title = todo.get("title");
//            int id = findIdFromTodoName(title);
//            response = given()
//                    .when()
//                    .get(baseUrl + "/" + id);
//            String responseBody = response.getBody().asString();
//            String expectedDescription = todo.get("description");
//
//            // Print statements for debugging
//            System.out.println("Response Body: " + responseBody);
//            System.out.println("Expected Description: " + expectedDescription);
//
//            assertTrue("Expected description not found in response body. Expected: " + expectedDescription + ", but got: " + responseBody,
//                    responseBody.contains(expectedDescription));
//        }
//    }
//
//    // Error flow
//    @When("a user tries to modify the description of a todo with a title that doesn’t exist:")
//    public void aUserTriesToModifyTheDescriptionOfATodoWithATitleThatDoesntExist(io.cucumber.datatable.DataTable dataTable) {
//        List<Map<String, String>> todos = dataTable.asMaps(String.class, String.class);
//        for (Map<String, String> todo : todos) {
//            String jsonBody = String.format(
//                    "{ \"description\": \"%s\" }",
//                    todo.get("description"));
//            response = given()
//                    .contentType("application/json")
//                    .body(jsonBody)
//                    .when()
//                    .put(baseUrl + "/non-existing-id");
//        }
//    }
//
//    @Then("the server should raise an error for modifying a description:")
//    public void theServerShouldRaiseAnError(io.cucumber.datatable.DataTable dataTable) {
//        List<Map<String, String>> errors = dataTable.asMaps(String.class, String.class);
//        for (Map<String, String> error : errors) {
//            assertTrue(response.getBody().asString().contains(error.get("error")));
//        }
//    }


    @Given("the following todo instances with descriptions are present in the system:")
    public void the_following_todo_instances_with_descriptions_are_present_in_the_system(io.cucumber.datatable.DataTable dataTable) {
        // Write code here that turns the phrase above into concrete actions
        // For automatic transformation, change DataTable to one of
        // E, List<E>, List<List<E>>, List<Map<K,V>>, Map<K,V> or
        // Map<K, List<V>>. E,K,V must be a String, Integer, Float,
        // Double, Byte, Short, Long, BigInteger or BigDecimal.
        //
        // For other transformations you can register a DataTableType.
       
    }
    @When("a user modifies the description of a todo with the specified title:")
    public void a_user_modifies_the_description_of_a_todo_with_the_specified_title(io.cucumber.datatable.DataTable dataTable) {
        // Write code here that turns the phrase above into concrete actions
        // For automatic transformation, change DataTable to one of
        // E, List<E>, List<List<E>>, List<Map<K,V>>, Map<K,V> or
        // Map<K, List<V>>. E,K,V must be a String, Integer, Float,
        // Double, Byte, Short, Long, BigInteger or BigDecimal.
        //
        // For other transformations you can register a DataTableType.
       
    }
    @Then("the following todos should be present in the system with updated descriptions:")
    public void the_following_todos_should_be_present_in_the_system_with_updated_descriptions(io.cucumber.datatable.DataTable dataTable) {
        // Write code here that turns the phrase above into concrete actions
        // For automatic transformation, change DataTable to one of
        // E, List<E>, List<List<E>>, List<Map<K,V>>, Map<K,V> or
        // Map<K, List<V>>. E,K,V must be a String, Integer, Float,
        // Double, Byte, Short, Long, BigInteger or BigDecimal.
        //
        // For other transformations you can register a DataTableType.
       
    }

    @When("a user tries to modify the description of a todo with a title that doesn’t exist:")
    public void a_user_tries_to_modify_the_description_of_a_todo_with_a_title_that_doesn_t_exist(io.cucumber.datatable.DataTable dataTable) {
        // Write code here that turns the phrase above into concrete actions
        // For automatic transformation, change DataTable to one of
        // E, List<E>, List<List<E>>, List<Map<K,V>>, Map<K,V> or
        // Map<K, List<V>>. E,K,V must be a String, Integer, Float,
        // Double, Byte, Short, Long, BigInteger or BigDecimal.
        //
        // For other transformations you can register a DataTableType.
       
    }
    @Then("the server should raise an error for modifying a description:")
    public void the_server_should_raise_an_error_for_modifying_a_description(io.cucumber.datatable.DataTable dataTable) {
        // Write code here that turns the phrase above into concrete actions
        // For automatic transformation, change DataTable to one of
        // E, List<E>, List<List<E>>, List<Map<K,V>>, Map<K,V> or
        // Map<K, List<V>>. E,K,V must be a String, Integer, Float,
        // Double, Byte, Short, Long, BigInteger or BigDecimal.
        //
        // For other transformations you can register a DataTableType.
       
    }



}