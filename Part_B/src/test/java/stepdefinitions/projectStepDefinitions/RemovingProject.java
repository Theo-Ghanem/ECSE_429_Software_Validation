package stepdefinitions.projectStepDefinitions;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class RemovingProject {

    @Given("a project titled Fun Project with the following attributes false, false, description exists")
    public void a_project_titled_fun_project_with_the_following_attributes_false_false_description_exists() {
        // Write code here that turns the phrase above into concrete actions
        
    }
    @When("the user deletes the project titled Fun Project")
    public void the_user_deletes_the_project_titled_fun_project() {
        // Write code here that turns the phrase above into concrete actions
        
    }
    @Then("the project titled Fun Project should no longer be present in the Projects list")
    public void the_project_titled_fun_project_should_no_longer_be_present_in_the_projects_list() {
        // Write code here that turns the phrase above into concrete actions
        
    }

    @Given("a project titled Fun Project with tasks and the following attributes false, false, description exists")
    public void a_project_titled_fun_project_with_tasks_and_the_following_attributes_false_false_description_exists() {
        // Write code here that turns the phrase above into concrete actions
        
    }
    @Then("the project with ID {int} should be removed from task with ID {int}")
    public void the_project_with_id_should_be_removed_from_task_with_id(Integer int1, Integer int2) {
        // Write code here that turns the phrase above into concrete actions
        
    }

    @Given("there is no project with the title Fun Project")
    public void there_is_no_project_with_the_title_fun_project() {
        // Write code here that turns the phrase above into concrete actions
        
    }
    @When("the user attempts to delete the project titled Fun Project")
    public void the_user_attempts_to_delete_the_project_titled_fun_project() {
        // Write code here that turns the phrase above into concrete actions
        
    }
    @Then("an error message should be generated indicating that the project with ID {int} cannot be deleted")
    public void an_error_message_should_be_generated_indicating_that_the_project_with_id_cannot_be_deleted(Integer int1) {
        // Write code here that turns the phrase above into concrete actions
        
    }
}
