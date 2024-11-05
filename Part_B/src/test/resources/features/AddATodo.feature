@AddATodo
Feature: Add a new todo
    As a user, I want to be able to create a new todo, so I can add it to my tasks

    Background:
        Given the API server is running

    # Normal Flow
    Scenario: Adding a new todo successfully
        When the user create a todo with a title, a doneStatus and a description:
            | title           | doneStatus | description           |
            | submit report   | false      | project documents     |
            | organize files  | false      | alphabetical order    |
        Then the following todos should be present in the system:
            | title           | doneStatus | description           |
            | submit report   | false      | project documents     |
            | organize files  | false      | alphabetical order    |

    # Alternate Flow
    Scenario: Adding a new todo without a description
        When the user adds a todo with the following details:
            | title         | doneStatus | description |
            | sweep floor   | false      |             |
            | wash dishes   | false      |             |
            | tidy bed      | false      |             |
        Then the following todos should still be present in the system:
            | title         | doneStatus | description |
            | sweep floor   | false      |             |
            | wash dishes   | false      |             |
            | tidy bed      | false      |             |

    # Error Flow
    Scenario: Adding a new todo with missing title
        When the user attempts to add a todo with the following details:
            | title | doneStatus | description           |
            |       | false      | missing title example |
        Then an error message "Title is required" should be returned