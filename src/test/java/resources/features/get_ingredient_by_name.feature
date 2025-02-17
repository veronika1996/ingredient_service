Feature: Get an ingredient by name

  Background:
    Given the system is initialized

  @getByName
  Scenario: Successfully retrieve an ingredient by name
    When I send a GET request to "/ingredients/Tomato"
    Then I should receive a 200 response
    And the ingredient response should be like the ingredient data in JSON file "/responses/get_ingredient_by_name_response.json"

  @error
  Scenario: Get a non-existing ingredient
    When I send a GET request to "/ingredients/NonExistingIngredient"
    Then I should receive a 404 response
    And the response should contain error message "Ingredient not found for ingredient name: NonExistingIngredient"
