@CreatingProjectWithFields
Feature: Creating Project with specific fields

    As a user, I want to create a project with custom fields, so I can better organize my projects

    # Normal flow
    Scenario Outline: Creating a project without linking it to any tasks
        Given the following todo exists:
        | id    | title           | doneStatus | description |
        | 1     | scan paperwork  | false      |             |
        When the user creates a project without tasks titled <project_title1> with completion status <project_completed1>, activity status <project_active1>, and description <project_description1>
        Then a new project with the specified fields <project_title1> <project_completed1> <project_active1> <project_description1> should be present under Projects
        Examples:
            | project_title1 | project_completed1 | project_active1 | project_description1 | project_tasks1 |
            | Fun Project    | false             | false          | description          | []             |

    # Alternate flow
    Scenario Outline: Creating a project linked to a task
        Given the following todo exists:
        | id | title          | doneStatus | description |
        | 1  | scan paperwork | false      |             |
        When the user creates a project with a linked task titled <project_title2> with completion status <project_completed2>, activity status <project_active2>, description <project_description2>, and task <project_tasks2>
        Then a new project with specified fields and linked tasks <project_title2> <project_completed2> <project_active2> <project_description2> <project_tasks2> should be present under Projects
        Examples:
            | project_title2 | project_completed2 | project_active2 | project_description2 | project_tasks2 |
            | Fun Project    | false             | false          | description          | [{"id":"1"}]   |

    # Error flow
    Scenario Outline: Attempting to create a project with a non-existing task
        Given that no todo with id 3 exists:
        | id    | title           | doneStatus | description |
        | 3     | scan paperwork  | false      |             |
        When the user attempts to create a project with a non-existing task titled <project_title3> with completion status <project_completed3>, activity status <project_active3>, description <project_description3>, and task <project_tasks3>
        Then the project <project_title3> with specified fields should not appear under Projects, and an error message should be displayed
        Examples:
            | project_title3 | project_completed3 | project_active3 | project_description3 | project_tasks3  |
            | Fun Project    | false             | false          | description          | [{"id":"3"}]    |
