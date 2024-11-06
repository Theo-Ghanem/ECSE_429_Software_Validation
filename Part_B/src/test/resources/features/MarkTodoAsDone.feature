@MarkTodoAsDone
Feature: Mark a todo as done
    As a user, I want to be able to mark a todo as done so that I can track my completed tasks.

    Background:
        Given the API server is running
        And the following todo instances are present in the system:
            | id | title           | doneStatus | description           |
            | 1  | write report    | false      | quarterly summary     |
            | 2  | review code     | false      | bug fixes            |
            | 3  | clean desk      | false      | organize workspace    |

# Normal flow
    Scenario: Successfully marking a todo as done
        When a user marks the following todos as done:
            | id | title           | description           |
            | 1  | write report    | quarterly summary     |
            | 2  | review code     | bug fixes            |
        Then the following todos should be marked as done:
            | id | title           | description           |
            | 1  | write report    | quarterly summary     |
            | 2  | review code     | bug fixes            |

# Alternate flow
    Scenario: Successfully marking an already completed todo as done
        When a user marks the following todo as done:
            | id | title           | description           |
            | 3  | clean desk      | organize workspace    |
        And the user marks the same todo as done again:
            | id | title           | description           |
            | 3  | clean desk      | organize workspace    |
        Then the todo should remain marked as done:
            | id | title           | description           |
            | 3  | clean desk      | organize workspace    |

# Error flow
    Scenario Outline: Attempting to mark a non-existing todo as done
        When a user tries to mark a non-existing todo as done:
            | id | error                                        |
            | <id> | Could not find an instance with todos/<id> |
        Then the server should return a not found error:
            | id | error                                        |
            | <id> | Could not find an instance with todos/<id> |

        Examples:
            | id  |
            | 999 |
            | 888 |