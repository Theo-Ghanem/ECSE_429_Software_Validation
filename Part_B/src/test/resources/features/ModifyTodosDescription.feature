@ModifyTodosDescription
Feature: Modify a todo's description field
   As a user, I want to be able to edit the description of a todo to correct any details as needed

   Background:
       Given the API server is running

   # Normal Flow
   Scenario: Modifying a todo's description successfully
       Given the following todo exists:
           | title         | doneStatus | description       |
           | submit report | false      | project documents |
       When the user modifies the description of the todo with title "submit report" to "final report"
       Then the system should contain the following todo with the modified descriptions:
           | title         | doneStatus | description  |
           | submit report | false      | final report |

   # Alternate Flow
   Scenario: Modifying a todo's description to an empty string
       Given the following todo exists:
           | title         | doneStatus | description       |
           | organize files| false      | alphabetical order|
       When the user modifies the description of the todo with title "organize files" to ""
       Then the system should contain the following todo:
           | title         | doneStatus | description |
           | organize files| false      |             |

   # Error Flow
   Scenario: Modifying a non-existent todo's description
       When the user attempts to modify the description of a non-existent todo with title "non-existent" to "new description"
       Then a todo description error message "Todo not found" should be returned