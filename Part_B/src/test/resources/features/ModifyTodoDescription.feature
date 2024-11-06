@ModifyTodoDescription
Feature: Modify a todo description
  As a user, I want to be able to modify the description of a todo so that I can update the details of my tasks.

  Background:
    Given the API server is running
    And the following todo instances with descriptions are present in the system:
      | title           | doneStatus | description           |
      | submit report   | false      | project documents     |
      | organize files  | false      | alphabetical order    |
      | sweep floor     | false      |                       |

# Normal flow
  Scenario: Successfully modifying a todo description
    When a user modifies the description of a todo with the specified title:
      | title           | description               |
      | submit report   | updated project documents |
      | organize files  | updated alphabetical order|
    Then the following todos should be present in the system with updated descriptions:
      | title           | doneStatus | description               |
      | submit report   | false      | updated project documents |
      | organize files  | false      | updated alphabetical order|

# Error flow
  Scenario Outline: Attempting to modify the description of a non-existing task
    When a user tries to modify the description of a todo with a title that doesn’t exist:
      | title           | description         |
      | <title>         | some new description|
    Then the server should raise an error for modifying a description:
      | error                                        |
      | Could not find an instance with todos/<title>   |

    Examples:
      | title          |
      | non-existing-1 |
      | non-existing-2 |