@ViewTasksByProject

Feature:

    As a user, I want to be able to query tasks related to a specific project, so I can manage my time and effort when I start to work on a project.

  Background:
    Given the API server is running 
      And the following projects exist on the system
      | title                  | completed | active | description            |
      | Living Room Renovation | false     | true   | test                   |
      | Bathroom Renovation    | false     | true   | test                   |
      And the following todos are associated with 'Living Room Renovation'
      | title                           | doneStatus | description              |
      | Renovate Ceiling                | false      | test                     |
      | Buy New TV                      | true       | test                     |
      And no todos are associated with 'Bathroom Renovation'

  #Normal Flow
  Scenario Outline: Request tasks for a project
    Given <projectTitle> is the title of a project on the system
      And the project with title <projectTitle> has outstanding tasks
      When the user requests the tasks for the project with title <projectTitle>
      Then <n> todos will be returned
      And each todo returned will be a task of the project with title <projectTitle>
    Examples:
      | projectTitle               | n |
      | Living Room Renovation     | 2 |

  #Alternate Flow
  Scenario Outline: Request tasks for a project with no tasks
    Given <projectTitle> is the title of a project on the system
      And the project with title <projectTitle> has no outstanding tasks
     When the user requests the tasks for the project with title <projectTitle>
     Then 0 todos will be returned
    Examples:
      | projectTitle            |
      | Bathroom Renovation     |

  #Error Flow
  Scenario Outline: Request tasks for a project not registered on the system
    Given <projectTitle> is not a title of a project on the system
     When the user requests the tasks for the project with title <projectTitle>
     Then the API server should respond with an error message <errorMessage>
    Examples:
      | projectTitle  | errorMessage                        | 
      | Doesn't exist | Could not find an instance with     | 
