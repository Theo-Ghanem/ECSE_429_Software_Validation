@AddATodo
Feature: Add a new todo
    As a user, I want to be able to create a new todo, so I can add it to my tasks

    Background:
        Given the API server is running

    # Normal Flow
    Scenario: Adding a new todo successfully
        When the user adds the following todos:
            | title          | doneStatus | description           |
            | submit report  | false      | project documents     |
            | organize files | false      | alphabetical order    |
        Then the system should contain the following todos:
            | title          | doneStatus | description           |
            | submit report  | false      | project documents     |
            | organize files | false      | alphabetical order    |

    # Alternate Flow
    Scenario: Adding a new todo without a description
        When the user adds the following todos without descriptions:
            | title         | doneStatus | description |
            | sweep floor   | false      |             |
            | wash dishes   | false      |             |
            | tidy bed      | false      |             |
        Then the system should contain the following todos without descriptions:
            | title         | doneStatus | description |
            | sweep floor   | false      |             |
            | wash dishes   | false      |             |
            | tidy bed      | false      |             |

    # Error Flow
    Scenario: Adding a new todo with missing title
        When the user attempts to add the following todos with missing titles:
            | title | doneStatus | description           |
            |       | false      | missing title example |
        Then an error message "Failed Validation: title : can not be empty" should be returned