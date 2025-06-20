Feature: Create a new ingredient

  @create
  Scenario: Successfully create a new ingredient
    Given the ingredient data in JSON file "/requests/create_ingredient_request.json"
    When I send a POST request to "/ingredients"
    Then I should receive a 201 response
    And the ingredient response should be like the ingredient data in JSON file "/responses/create_ingredient_response.json"

  @error
  Scenario: Create ingredient with missing name
    Given the ingredient data in JSON file "/requests/create_ingredient_request_missing_name.json"
    When I send a POST request to "/ingredients"
    Then I should receive a 400 response
    And the response should contain error message "Name of ingredient cannot be empty"

  @error
  Scenario: Create ingredient with negative calorie number
    Given the ingredient data in JSON file "/requests/create_ingredient_request_negative_calories.json"
    When I send a POST request to "/ingredients"
    Then I should receive a 400 response
    And the response should contain error message "Calorie number needs to be positive value"

  @error
  Scenario: Create ingredient with missing calorie number
    Given the ingredient data in JSON file "/requests/create_ingredient_request_missing_calories.json"
    When I send a POST request to "/ingredients"
    Then I should receive a 400 response
    And the response should contain error message "Calorie number needs to be positive value"

  @error
  Scenario: Create ingredient name already exists
    Given the ingredient data in JSON file "/requests/create_ingredient_request.json"
    When I add an ingredient and send a POST request to "/ingredients"
    Then I should receive a 400 response
    And the response should contain error message "Vec postoji sastojak sa imenom: Apple"