@MarkTaskAsDone
Feature:
    As a user, I want to be able to mark a task as complete, to record my progress

Background:
    Given the API server is running
    And the following todos are registered in the system:
        | title      | doneStatus | description  |
        | example1   | false      | test         |
        | example2   | true       | test         |
        | x          | true       | test         |

# Normal Flow
Scenario Outline: Mark an incomplete task as completed
    Given a todo with the title <selectedTitle> exists in the system
    And the todo titled <selectedTitle> is currently marked as not done
    When the user marks the task titled <selectedTitle> as done
    Then the todo titled <selectedTitle> should be updated to done status in the system
    And the updated todo, marked as done, will be returned to the user
    Examples:
        | selectedTitle |
        | example1      |

# Alternate Flow
Scenario Outline: Attempting to mark an already completed task as done
    Given a todo with the title <selectedTitle> exists in the system
    And the todo titled <selectedTitle> is already marked as done
    When the user tries to mark the task titled <selectedTitle> as done
    Then no changes will be made to the todo in the system
    And the todo will be returned to the user as is
    Examples:
        | selectedTitle |
        | example2      |
        | x             |

# Error Flow
Scenario Outline: Attempting to mark a non-existent task as completed
    Given no todo with the title <selectedTitle> exists in the system
    When the user tries to mark the task titled <selectedTitle> as done
    Then no todos in the system will be modified
    And the user will receive an error message indicating that the specified todo does not exist
    Examples:
        | selectedTitle |
        | fake title    |
        | null          |

