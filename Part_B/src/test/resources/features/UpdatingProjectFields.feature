@ChangingProjectFields
Feature: Changing the non-task fields of a specific project

    As a user, I want to be able to modify the fields of a project, so I can update the relevant information of the project

# Normal flow
Scenario Outline: Updating the non-task fields of the project to new values
    Given the following project is present
    | title   | completed | active | description |
    | Fun Project | false     | false  | description |
    When the user updates the project titled <old_title1> with <project_title1> <project_completed1> <project_active1> <project_description1>
    Then the project's non-task fields should reflect <project_title1> <project_completed1> <project_active1> <project_description1>
    Examples:
        | project_title1 | project_completed1 | project_active1 | project_description1  | old_title1 |
        | Boring Project        | true               | true            | description1          | Fun Project    |

# Alternate flow
Scenario Outline: Reverting the non-task fields of the project to the original values
    Given the following project is present
    | title   | completed | active | description |
    | Fun Project | false     | false  | description |
    When the user attempts to set this project <old_title2> with <project_title2> <project_completed2> <project_active2> <project_description2>
    Then the project's non-task fields should remain as <project_title2> <project_completed2> <project_active2> <project_description2>
    Examples:
        | project_title2 | project_completed2 | project_active2 | project_description2  | old_title2 |
        | Fun Project        | false              | false           | description           | Fun Project    |

# Error flow
Scenario Outline: Attempting to set the non-task fields of a non-existing project
    Given the following project does not exist
    | title   | completed | active | description |
    | Fun Project | false     | false  | description |
    When the user tries to update the non-existing project with <id> using <project_title3> <project_completed3> <project_active3> <project_description3>
    Then the project with ID <id> should not exist in the Projects list, and an error message should be displayed
    Examples:
        | project_title3 | project_completed3 | project_active3 | project_description3  | id     |
        | Boring Project        | true               | true            | description1          | 2      |
