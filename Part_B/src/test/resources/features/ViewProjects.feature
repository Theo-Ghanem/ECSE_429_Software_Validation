@ViewProjects
Feature: View projects

  As a user, I want to query all available projects so that I can monitor the projects I currently have underway.

Background:
  Given the API server is running

# Normal flow
Scenario: Retrieving all projects
    Given the following projects are recorded in the system:
      | title                   | description                     | 
      | Plan Family Vacation     | Organizing a summer getaway    | 
      | Launch Marketing Campaign | Strategy for product promotion  | 
    When a GET request is made to /projects
    Then the response body should include the following details:
      | title                   | description                     | 
      | Plan Family Vacation     | Organizing a summer getaway    | 
      | Launch Marketing Campaign | Strategy for product promotion  | 

# Alternate flow
Scenario: Retrieving projects when none are registered
    When a GET request is made to /projects
    Then the response body should display only the default project:
      | title         | id   | 
      | Office Work   | 1    | 


# Error flow
Scenario: Attempting to query projects using an invalid endpoint
    When a GET request is made to /projects
    Then the request to the endpoint /projects should return a 404 Not Found status code


