@RemovingProject
Feature: Deleting a specific project

    As a user, I want to be able to delete a project so that I can remove it when it is no longer needed.

# Normal flow
Scenario: Deleting a specific project that has no tasks
    Given a project titled "Fun Project" with the following attributes "false", "false", "description" exists
    When the user deletes the project titled "Fun Project"
    Then the project titled "Fun Project" should no longer be present in the Projects list

# Alternate flow
Scenario: Deleting a specific project that has tasks
    Given a project titled "Fun_Project" with tasks and the following attributes "false", "false", "description" exists
    When the user deletes the project titled "Fun Project"
    Then the project titled "Fun Project" should no longer be present in the Projects list
    And the project with ID 3 should be removed from task with ID 1

# Error flow
Scenario: Attempting to delete a project that does not exist
    Given there is no project with the title "Fun_Project"
    When the user attempts to delete the project titled "Fun Project"
    Then an error message should be generated indicating that the project with ID 3 cannot be deleted