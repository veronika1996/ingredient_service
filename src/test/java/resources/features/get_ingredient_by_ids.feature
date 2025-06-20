Feature: Get ingredients by ids

  @get
  Scenario Outline: Get ingredients by given list of ids
    Given the ingredient list contains <number> ingredient
    When I send a POST request by ids
    Then I should receive a 200 response
    And the response should be list of data as in JSON file "/responses/<fileName>"

    Examples:
      | number | fileName |
      | 1      | get_all_ingredient_size_one_response.json |
      | 2      | get_all_ingredient_size_two_response.json |

