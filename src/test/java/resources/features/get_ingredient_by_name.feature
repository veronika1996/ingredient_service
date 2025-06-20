Feature: Get an ingredient by name and username

  Background:
    Given the system is initialized

  @getByName
  Scenario: Successfully retrieve an ingredient by name and username
    When I send a GET request to "/ingredients?name=Tomato&username=Admin"
    Then I should receive a 200 response
    And the ingredient response should be like the ingredient data in JSON file "/responses/get_ingredient_by_name_response.json"

  @error
  Scenario: Ingredient doesn't exist for given name
    When I send a GET request to "/ingredients?name=NonExistingIngredient&username=Admin"
    Then I should receive a 404 response
    And the response should contain error message "Nije pronadjen sastojak sa imenom: NonExistingIngredient"

  @error
  Scenario: Ingredient doesn't exist for given username
    When I send a GET request to "/ingredients?name=Tomato&username=User"
    Then I should receive a 404 response
    And the response should contain error message "Nije pronadjen sastojak sa imenom: Tomato"
