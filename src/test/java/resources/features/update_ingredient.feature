Feature: Update an existing ingredient

  Background:
    Given the system is initialized

  @update
  Scenario: Successfully update an ingredient
    Given the ingredient data in JSON file "/requests/update_ingredient_request.json"
    When I send a PUT request to "/ingredients/Tomato"
    Then I should receive a 200 response
    And the ingredient response should be like the ingredient data in JSON file "/responses/update_ingredient_response.json"

  @error
  Scenario: Update non-existing ingredient
    Given the ingredient data in JSON file "/requests/update_ingredient_request.json"
    When I send a PUT request to "/ingredients/NonExistingIngredient"
    Then I should receive a 404 response
    And the response should contain error message "Ingredient not found for ingredient name: NonExistingIngredient"

  @error
  Scenario: Update ingredient with missing calorie number
    Given the ingredient data in JSON file "/requests/update_ingredient_request_missing_calories.json"
    When I send a PUT request to "/ingredients/Tomato"
    Then I should receive a 400 response
    And the response should contain error message "Calorie number needs to be positive value"
