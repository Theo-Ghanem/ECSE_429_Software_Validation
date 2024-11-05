@AddATask
Feature: Create a new instance of todo
    As a user, I want to be able to add a task to a project, so I can organize a project by tasks

    Background:
        Given the API server is running

    # Normal Flow 
    Scenario: Creating a task successfully
        When a user adds a todo with a title, a doneStatus, and a description:
            | title           | doneStatus | description           |
            | submit report   | false      | project documents     |
            | organize files  | false      | alphabetical order    |
        Then these todos should be present in the system:
            | title           | doneStatus | description           |
            | submit report   | false      | project documents     |
            | organize files  | false      | alphabetical order    |

    # Alternate flow
    Scenario: Creating a task successfully with no description
        When a user adds a todo with a title, a doneStatus, and no description:
            | title         | doneStatus | description         |   
            | sweep floor   | false      |                     |
            | wash dishes   | false      |                     |
            | tidy bed      | false      |                     |
        Then these todos should still be present in the system:
            | title         | doneStatus | description         |
            | sweep floor   | false      |                     |
            | wash dishes   | false      |                     |
            | tidy bed      | false      |                     |

    # Error flow
    Scenario: Failing to create a task with an empty title
        When a user attempts to add a todo with an empty title:
            | title     | doneStatus | description | error                                         |
            |           | false      |             | Failed Validation: title cannot be empty      |
        Then todos with an empty title should not exist in the system:
            | title     | doneStatus | description | error                                         |
            |           | false      |             | Failed Validation: title cannot be empty      |
        And the server should raise an error:
            | title     | doneStatus | description | error                                         |
            |           | false      |             | Failed Validation: title cannot be empty      |

 
