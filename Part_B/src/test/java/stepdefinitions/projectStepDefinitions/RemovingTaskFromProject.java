package stepdefinitions.projectStepDefinitions;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class RemovingTaskFromProject {

    @Given("a project titled Fun Project exists and is linked to {int}")
    public void a_project_titled_fun_project_exists_and_is_linked_to(Integer int1) {
        // Write code here that turns the phrase above into concrete actions
    }
    @When("the user removes {int} from the project titled Fun Project")
    public void the_user_removes_from_the_project_titled_fun_project(Integer int1) {
        // Write code here that turns the phrase above into concrete actions
    }
    @Then("the task {int} should no longer be listed in the project_tasks field of the project Fun Project")
    public void the_task_should_no_longer_be_listed_in_the_project_tasks_field_of_the_project_fun_project(Integer int1) {
        // Write code here that turns the phrase above into concrete actions
    }

    @Given("a project titled Homework exists and is associated with {int} and {int}")
    public void a_project_titled_homework_exists_and_is_associated_with_and(Integer int1, Integer int2) {
        // Write code here that turns the phrase above into concrete actions
    }
    @When("the user removes {int} from the project titled Homework, which is linked to another task")
    public void the_user_removes_from_the_project_titled_homework_which_is_linked_to_another_task(Integer int1) {
        // Write code here that turns the phrase above into concrete actions
    }
    @Then("only the task {int} should remain in the project_tasks field of the project Homework")
    public void only_the_task_should_remain_in_the_project_tasks_field_of_the_project_homework(Integer int1) {
        // Write code here that turns the phrase above into concrete actions
    }


    @Given("a project titled Fun Project exists and is not linked to {int}")
    public void a_project_titled_fun_project_exists_and_is_not_linked_to(Integer int1) {
        // Write code here that turns the phrase above into concrete actions
    }
    @When("the user attempts to remove {int} from the unassociated project titled Fun Project")
    public void the_user_attempts_to_remove_from_the_unassociated_project_titled_fun_project(Integer int1) {
        // Write code here that turns the phrase above into concrete actions
    }
    @Then("the task {int} should still be absent from the project_tasks field of the project Fun Project, and an error message should be generated")
    public void the_task_should_still_be_absent_from_the_project_tasks_field_of_the_project_fun_project_and_an_error_message_should_be_generated(Integer int1) {
        // Write code here that turns the phrase above into concrete actions
    }

}