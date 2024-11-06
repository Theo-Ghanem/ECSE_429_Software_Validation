@CreatingProjectWithFields
Feature: Creating Project with specific fields

    As a user, I want to create a project with custom fields, so I can better organize my projects

    # Normal flow
    Scenario Outline: Creating a project without linking it to any tasks
        Given the following todo exists:
            | title           | doneStatus | description |
            | scan paperwork  | false      | empty       |
        When the user creates a project without tasks
            | title             | completed         | active         | description  |
            | scan paperwork    | false             | false          | empty        |
        Then a new project with the specified fields should appear
            | title             | completed         | active         | description  |
            | scan paperwork    | false             | false          | empty        |

    # Alternate flow
    Scenario Outline: Creating a project linked to a task
        Given the following todo exists:
            | id | title          | doneStatus | description |
            | 1  | scan paperwork | false      |             |
        When the user creates a project with a linked tasks
            | title             | completed         | active         | description  | todo id   |
            | scan paperwork    | false             | false          | empty        | 1         |
        Then a new project with specified fields and linked tasks should be present under Projects
            | title             | completed    | active    | description | tasks     |
            | scan paperwork    | false        | false     | empty | {"id": 1} |

    # # Error flow
    # Scenario Outline: Attempting to create a project with a non-existing task
    #     Given that no todo with id 3 exists:
    #     | id    | title           | doneStatus | description |
    #     | 3     | scan paperwork  | false      |             |
    #     When the user attempts to create a project with a non-existing task titled <project_title3> with completion status <project_completed3>, activity status <project_active3>, description <project_description3>, and task <project_tasks3>
    #     Then the project <project_title3> with specified fields should not appear under Projects, and an error message should be displayed
    #     Examples:
    #         | project_title3 | project_completed3 | project_active3 | project_description3 | project_tasks3  |
    #         | Fun Project    | false             | false          | description          | [{"id":"3"}]    |