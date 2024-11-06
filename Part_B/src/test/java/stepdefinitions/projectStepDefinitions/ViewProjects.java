package stepdefinitions.projectStepDefinitions;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class ViewProjects {

    @Given("the following projects are recorded in the system:")
    public void the_following_projects_are_recorded_in_the_system(io.cucumber.datatable.DataTable dataTable) {
        // Write code here that turns the phrase above into concrete actions
        // For automatic transformation, change DataTable to one of
        // E, List<E>, List<List<E>>, List<Map<K,V>>, Map<K,V> or
        // Map<K, List<V>>. E,K,V must be a String, Integer, Float,
        // Double, Byte, Short, Long, BigInteger or BigDecimal.
        //
        // For other transformations you can register a DataTableType.
        
    }
    @When("a GET request is made to \\/projects")
    public void a_get_request_is_made_to_projects() {
        // Write code here that turns the phrase above into concrete actions
        
    }
    @Then("the response body should include the following details:")
    public void the_response_body_should_include_the_following_details(io.cucumber.datatable.DataTable dataTable) {
        // Write code here that turns the phrase above into concrete actions
        // For automatic transformation, change DataTable to one of
        // E, List<E>, List<List<E>>, List<Map<K,V>>, Map<K,V> or
        // Map<K, List<V>>. E,K,V must be a String, Integer, Float,
        // Double, Byte, Short, Long, BigInteger or BigDecimal.
        //
        // For other transformations you can register a DataTableType.
        
    }


    @Then("the response body should display only the default project:")
    public void the_response_body_should_display_only_the_default_project(io.cucumber.datatable.DataTable dataTable) {
        // Write code here that turns the phrase above into concrete actions
        // For automatic transformation, change DataTable to one of
        // E, List<E>, List<List<E>>, List<Map<K,V>>, Map<K,V> or
        // Map<K, List<V>>. E,K,V must be a String, Integer, Float,
        // Double, Byte, Short, Long, BigInteger or BigDecimal.
        //
        // For other transformations you can register a DataTableType.
        
    }


    @Then("the request to the endpoint \\/projects should return a {int} Not Found status code")
    public void the_request_to_the_endpoint_projects_should_return_a_not_found_status_code(Integer int1) {
        // Write code here that turns the phrase above into concrete actions
        
    }


}
