@RemovingProject
Feature: Deleting a specific project

    As a user, I want to be able to delete a project so that I can remove it when it is no longer needed.

# Normal flow
Scenario Outline: Deleting a specific project that has no tasks
    Given a project titled <project_title1> with the following attributes <project_completed1>, <project_active1>, <project_description1> exists
    When the user deletes the project titled <project_title1>
    Then the project titled <project_title1> should no longer be present in the Projects list
    Examples:
        | project_title1 | project_completed1 | project_active1 | project_description1 | project_tasks1  |
        | Fun Project        | false              | false           | description          | []              |

# Alternate flow
Scenario Outline: Deleting a specific project that has tasks
    Given a project titled <project_title2> with tasks and the following attributes <project_completed2>, <project_active2>, <project_description2> exists
    When the user deletes the project titled <project_title2>
    Then the project titled <project_title2> should no longer be present in the Projects list
    And the project with ID <project_id> should be removed from task with ID <project_tasks2_id>
    Examples:
        | project_title2 | project_completed2 | project_active2 | project_description2 | project_tasks2  | project_tasks2_id | project_id |
        | Fun Project        | false              | false           | description          | [{"id":"1"}]    | 1                 | 3          |

# Error flow
Scenario Outline: Attempting to delete a project that does not exist
    Given there is no project with the title <project_title3>
    When the user attempts to delete the project titled <project_title3>
    Then an error message should be generated indicating that the project with ID <project_id> cannot be deleted
    Examples:
        | project_title3 | project_completed3 | project_active3 | project_description3 | project_tasks3  | project_id |
        | Fun Project       | false              | false           | description          | []              | 3          |
