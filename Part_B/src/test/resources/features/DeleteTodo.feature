@DeleteTodo
Feature: Delete a todo
    As a user, I want to be able to delete a todo so that I can clear out unwanted items from my list.

Background:
    Given the API server is running
    And the following todo instances are present in the system:
        | id | title           | doneStatus | description           |
        | 7  | submit report   | false      | project documents     |
        | 8  | organize files  | false      | alphabetical order    |
        | 9  | sweep floor     | false      |                       |

# Normal flow
Scenario: Successfully deleting a task
    When a user deletes a todo with the specified id:
        | id | title           | doneStatus | description           |
        | 7  | submit report   | false      | project documents     |
        | 8  | organize files  | false      | alphabetical order    |
    Then the following todos should no longer be present in the system:
        | id | title           | doneStatus | description           |
        | 7  | submit report   | false      | project documents     |
        | 8  | organize files  | false      | alphabetical order    |

# Alternate flow
Scenario: Successfully deleting an initial todo
    When a user deletes a todo with the specified id:
        | id | title           | doneStatus | description           |
        | 2  | archive notes   | false      |                       |
    Then the following todos should no longer be present in the system:
        | id | title           | doneStatus | description           |
        | 2  | archive notes   | false      |                       |

# Error flow
Scenario Outline: Attempting to delete a non-existing task
    When a user tries to delete a todo with an id that doesn’t exist:
        | id | error                                        |
        | <id> | Could not find an instance with todos/<id> |
    Then the following todos should not be found in the system:
        | id | error                                        |
        | <id> | Could not find an instance with todos/<id> |
    And the server should raise an error:
        | id | error                                        |
        | <id> | Could not find an instance with todos/<id> |

Examples:
    | id  |
    | 180 |
    | 99  |