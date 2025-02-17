Feature: Delete an ingredient

  Background:
    Given the system is initialized

  @delete
  Scenario: Successfully delete an ingredient
    When I send a DELETE request to "/ingredients/Tomato"
    Then I should receive a 204 response
    And the ingredient "Tomato" should be deleted from the system

  @error
  Scenario: Delete a non-existing ingredient
    When I send a DELETE request to "/ingredients/NonExistingIngredient"
    Then I should receive a 404 response
    And the response should contain error message "Ingredient not found for ingredient name: NonExistingIngredient"
