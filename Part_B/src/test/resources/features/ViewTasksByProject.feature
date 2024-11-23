@ViewTasksByProject

Feature: View Tasks By Project

    As a user, I want to be able to query tasks related to a specific project, so I can manage my time and effort when I start to work on a project.

  Background:
    Given the API server is running

    And the following projects are recorded in the system
      | title                   | completed | active | description                  |
      | Kitchen Remodel         | false     | true   | Updating kitchen appliances    |
      | Garden Landscaping      | false     | true   | Designing a new garden layout  |
      And the following todos are associated with 'Kitchen Remodel'
      | title                           | doneStatus | description                |
      | Install New Cabinets            | false      | Installing cabinets         |
      | Purchase Countertop             | true       | Selecting countertop styles |

  # Normal Flow
  Scenario Outline: Request tasks for a project
      When the user retrieves the tasks for a project
      Then <n> todos will be returned
      And each todo returned will correspond to a task of the project titled <projectTitle>
    Examples:
      | projectTitle    | n |
      | Kitchen Remodel | 1 |

  # Alternate Flow
  Scenario Outline: Request tasks for a project with no tasks
    Given <projectTitle> is the title of a project in the system
      And the project with title <projectTitle> has no outstanding tasks
     When the user retrieves the tasks for the project titled <projectTitle>
     Then 1 todos will be returned
    Examples:
      | projectTitle            |
      | Garden Landscaping      |

  # Error Flow
  Scenario Outline: Request tasks for a project not registered in the system
    Given <projectTitle> is not a recognized title of a project in the system
     When the user requests the tasks for the project titled <projectTitle>
    Examples:
      | projectTitle  | errorMessage                        | 
      | Nonexistent    | Could not find an instance with     | 

