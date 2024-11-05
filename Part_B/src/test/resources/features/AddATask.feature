@AddATask
Feature: Create a new instance of todo
    As a user, I want to be able to add a task to a project, so I can organize a project by tasks

    Background:
        Given the API server is running

    # Normal Flow
    Scenario: Creating a task successfully
        When a user adds the following tasks with descriptions:
            | title           | doneStatus | description           |
            | submit report   | false      | project documents     |
            | organize files  | false      | alphabetical order    |
        Then the system should contain the following tasks with descriptions:
            | title           | doneStatus | description           |
            | submit report   | false      | project documents     |
            | organize files  | false      | alphabetical order    |

    # Alternate flow
    Scenario: Creating a task successfully with no description
        When a user adds the following tasks without descriptions:
            | title         | doneStatus | description         |
            | sweep floor   | false      |                     |
            | wash dishes   | false      |                     |
            | tidy bed      | false      |                     |
        Then the system should contain the following tasks without descriptions:
            | title         | doneStatus | description         |
            | sweep floor   | false      |                     |
            | wash dishes   | false      |                     |
            | tidy bed      | false      |                     |

    # Error flow
    Scenario: Failing to create a task with an empty title
        When a user attempts to add the following tasks with empty titles:
            | title     | doneStatus | description           |
            |           | false      | missing title example |
        Then a task error message "Failed Validation: title : can not be empty" should be returned