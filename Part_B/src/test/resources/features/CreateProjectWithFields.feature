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

    # Error flow
    Scenario Outline: Attempting to create a project with a non-existing task
        Given that no todo with id 3 exists
        When the user attempts to create a project with a non-existing task
        | title               | completed    | active    | description | task ID    |
        | leave the office    | false        | false     | empty       | 3          |
        Then a project error message "Invalid relationships: Failed Validation: cannot find tasks to relate to with id 3" should be returned
