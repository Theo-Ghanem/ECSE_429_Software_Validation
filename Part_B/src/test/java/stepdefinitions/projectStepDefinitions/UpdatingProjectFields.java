package stepdefinitions.projectStepDefinitions;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class UpdatingProjectFields {


    @Given("the following project is present")
    public void the_following_project_is_present(io.cucumber.datatable.DataTable dataTable) {
        // Write code here that turns the phrase above into concrete actions
        // For automatic transformation, change DataTable to one of
        // E, List<E>, List<List<E>>, List<Map<K,V>>, Map<K,V> or
        // Map<K, List<V>>. E,K,V must be a String, Integer, Float,
        // Double, Byte, Short, Long, BigInteger or BigDecimal.
        //
        // For other transformations you can register a DataTableType.
        
    }

    @When("the user updates the project titled Fun Project with Boring Project true true description1")
    public void the_user_updates_the_project_titled_fun_project_with_boring_project_true_true_description1() {
        // Write code here that turns the phrase above into concrete actions
        
    }

    @Then("the project's non-task fields should reflect Boring Project true true description1")
    public void the_project_s_non_task_fields_should_reflect_boring_project_true_true_description1() {
        // Write code here that turns the phrase above into concrete actions
        
    }

    @When("the user attempts to set this project Fun Project with Fun Project false false description")
    public void the_user_attempts_to_set_this_project_fun_project_with_fun_project_false_false_description() {
        // Write code here that turns the phrase above into concrete actions
        
    }

    @Then("the project's non-task fields should remain as Fun Project false false description")
    public void the_project_s_non_task_fields_should_remain_as_fun_project_false_false_description() {
        // Write code here that turns the phrase above into concrete actions
        
    }

    @Given("the following project does not exist")
    public void the_following_project_does_not_exist(io.cucumber.datatable.DataTable dataTable) {
        // Write code here that turns the phrase above into concrete actions
        // For automatic transformation, change DataTable to one of
        // E, List<E>, List<List<E>>, List<Map<K,V>>, Map<K,V> or
        // Map<K, List<V>>. E,K,V must be a String, Integer, Float,
        // Double, Byte, Short, Long, BigInteger or BigDecimal.
        //
        // For other transformations you can register a DataTableType.
        
    }

    @When("the user tries to update the non-existing project with {int} using Boring Project true true description1")
    public void the_user_tries_to_update_the_non_existing_project_with_using_boring_project_true_true_description1(Integer int1) {
        // Write code here that turns the phrase above into concrete actions
        
    }

    @Then("the project with ID {int} should not exist in the Projects list, and an error message should be displayed")
    public void the_project_with_id_should_not_exist_in_the_projects_list_and_an_error_message_should_be_displayed(Integer int1) {
        // Write code here that turns the phrase above into concrete actions
        
    }
}
