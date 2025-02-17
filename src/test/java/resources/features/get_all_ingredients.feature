Feature: Get all ingredients

  @getAll
  Scenario: Successfully retrieve all ingredients when list is empty
    Given the ingredient list is empty
    When I send a GET request to "/ingredients"
    Then I should receive a 200 response
    And the response should contain an empty list

  @getAll
  Scenario Outline: Successfully retrieve all ingredients when list has one ingredient
    Given the ingredient list contains <number> ingredient
    When I send a GET request to "/ingredients"
    Then I should receive a 200 response
    And the response should contain <number> ingredient
    And the response should be list of data as in JSON file "/responses/<fileName>"
    Examples:
      | number | fileName |
      | 1      | get_all_ingredient_size_one_response.json |
      | 2      | get_all_ingredient_size_two_response.json |

