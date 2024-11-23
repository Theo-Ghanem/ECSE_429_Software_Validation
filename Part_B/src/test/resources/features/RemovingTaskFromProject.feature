@RemovingTaskFromProject
Feature: Removing a specific task from a specific project

    As a user, I want to be able to remove a todo from a project, so I can remove the task when it is completed

# Normal flow
Scenario Outline: Removing a specific task from a project that is solely associated with that task
    Given a project titled <project_title1> exists and is linked to <project_task1>
    When the user removes <project_task1> from the project titled <project_title1>
    Then the task <project_task1> should no longer be listed in the project_tasks field of the project <project_title1>
    Examples:
        | project_title1 | project_completed1 | project_active1 | project_description1 | project_task1 |
        | Fun Project        | false              | false           |                      | 1             |

# Alternate flow
Scenario Outline: Removing a specific task from a project that is linked to multiple tasks
    Given a project titled <project_title2> exists and is associated with <project_task21> and <project_task22>
    When the user removes <project_task21> from the project titled <project_title2>, which is linked to another task
    Examples:
        | project_title2 | project_completed2 | project_active2 | project_description2 | project_task21  | project_task22  |
        | Homework    | false              | false           |                      | 1                | 2                |

# Error flow
Scenario Outline: Attempting to remove a task from a project that is not associated with it
    Given a project titled <project_title3> exists and is not linked to <project_task3>
    Then the task <project_task3> should still be absent from the project_tasks field of the project <project_title3>, and an error message should be generated
    Examples:
        | project_title3 | project_completed3 | project_active3 | project_description3 | project_task3  |
        | Fun Project       | false              | false           |                      | 3              |
