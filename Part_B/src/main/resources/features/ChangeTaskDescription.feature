@ModifyTodosDescription
Feature: Modify a todo's description field
   As a user, I want to be able to edit the description of a todo to correct any details as needed

Background:
    Given the API server is running
    And the following todos with descriptions exist:
        | title | doneStatus | description |
        | todo1 | false      | first       |
        | todo2 | true       | second      |
        | todo3 | false      | third       |

# Normal and Alternate Flow
Scenario: Successfully modify a task's description
    When a user modifies the description of the following todos:
        | title | doneStatus | description |
        # Normal Flow
        | todo1 | false      | deleted     |
        | todo2 | true       | updated     |
        # Alternate Flow
        | todo3 | false      |             |
    Then the todo descriptions should be updated as follows:
        | title | doneStatus | description |
        # Normal Flow
        | todo1 | false      | deleted     |
        | todo2 | true       | updated     |
        # Alternate Flow
        | todo3 | false      |             |

# Error Flow
Scenario: Fail to modify a task's description due to incorrect URL
    When a user attempts to modify the description using an incorrect URL, /todos/1descriptions
    Then the todos' descriptions should remain unchanged:
        | title | doneStatus | description |
        # Normal Flow
        | todo1 | false      | first       |
        | todo2 | true       | second      |
        # Alternate Flow
        | todo3 | false      | third       |
    And the user should see an error message: "No such todo entity instance with GUID or ID 1descriptions found"

        