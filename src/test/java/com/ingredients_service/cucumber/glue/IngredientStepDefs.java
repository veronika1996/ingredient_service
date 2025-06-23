package com.ingredients_service.cucumber.glue;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ingredients_service.dto.ErrorDto;
import com.ingredients_service.dto.IngredientDTO;
import com.ingredients_service.entity.IngredientEntity;
import com.ingredients_service.enums.Category;
import com.ingredients_service.repository.IngredientRepository;
import com.ingredients_service.service.IngredientService;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

@ActiveProfiles("test")
public class IngredientStepDefs {

  private static final String BASE_PATH = "http://localhost:" + 8083 + "/meal_plan";
  private static final String RESOURCES_PATH = "src/test/java/resources/";
  private final ObjectMapper objectMapper = new ObjectMapper();
  private final RestTemplate restTemplate = new RestTemplate();
  private static final String ING_NAME = "Tomato";
  private static final Integer CALORIE_NUMBER = 20;
  private static final String ADDED_BY = "Admin";
  List<Long> byIdsRequest = new ArrayList<>();

  @Autowired
  private IngredientRepository ingredientRepository;
  @Autowired
  private IngredientService ingredientService;
  private ResponseEntity<String> response;
  private IngredientDTO ingredientDTO;

  @Given("the system is initialized")
  public void theSystemIsInitialized() {
    ingredientRepository.deleteAll();
    IngredientEntity ingredient = new IngredientEntity(ING_NAME, CALORIE_NUMBER, ADDED_BY,
        Category.FRUIT);
    ingredientRepository.save(ingredient);

    IngredientEntity savedIngredient = ingredientRepository.findByName(ING_NAME).orElse(null);
    assertNotNull("Ingredient should be saved and found", savedIngredient);
  }

  @Given("the ingredient data in JSON file {string}")
  public void theIngredientDataInJsonFile(String fileName) throws IOException {
    ObjectMapper objectMapper = new ObjectMapper();
    // Load the JSON file from the resources folder
    ingredientDTO =
        objectMapper.readValue(
            new File(RESOURCES_PATH + fileName), IngredientDTO.class);
  }

  @When("I send a POST request to {string}")
  public void iSendAPostRequestTo(String path) {
    ingredientRepository.deleteAll();
    String url = BASE_PATH + path;
    try {
      response = restTemplate.postForEntity(url, ingredientDTO, String.class);
    } catch (HttpClientErrorException | HttpServerErrorException e) {
      response = ResponseEntity.status(e.getStatusCode()).body(e.getResponseBodyAsString());
    }
  }

  @Then("I should receive a {int} response")
  public void iShouldReceiveAResponse(int statusCode) {
    assertEquals(HttpStatus.valueOf(statusCode), response.getStatusCode());
  }

  @And("the ingredient response should be like the ingredient data in JSON file {string}")
  public void theIngredientResponseShouldBeLikeTheIngredientDataInJsonFile(String fileName)
      throws IOException {
    ObjectMapper objectMapper = new ObjectMapper();
    IngredientDTO expectedIngredientDTO =
        objectMapper.readValue(
            new File(RESOURCES_PATH + fileName), IngredientDTO.class);
    IngredientDTO actualIngredientDTO =
        objectMapper.readValue(response.getBody(), IngredientDTO.class);

    assertEquals(expectedIngredientDTO.getName(), actualIngredientDTO.getName());
    assertEquals(expectedIngredientDTO.getCalorieNumber(), actualIngredientDTO.getCalorieNumber());
    assertEquals(expectedIngredientDTO.getCategory(), actualIngredientDTO.getCategory());
  }

  @Then("the response should contain error message {string}")
  public void theResponseShouldContainErrorMessage(String errorMessage) throws IOException {
    ErrorDto errorDto = objectMapper.readValue(response.getBody(), ErrorDto.class);
    assertEquals(errorMessage, errorDto.getMessage());
  }

  @When("I send a DELETE request to {string}")
  public void iSendADELETERequestTo(String path) {
    String url = BASE_PATH + path;

    try {
      response = restTemplate.exchange(url, HttpMethod.DELETE, null, String.class);
    } catch (HttpClientErrorException | HttpServerErrorException e) {
      response = ResponseEntity.status(e.getStatusCode()).body(e.getResponseBodyAsString());
    }
  }

  @And("the ingredient {string} should be deleted from the system")
  public void theIngredientShouldBeDeletedFromTheSystem(String ingredientName) {
    try {
      String getUrl = BASE_PATH + "/ingredients/";
      // Try to get the ingredient after deletion
      ResponseEntity<String> getResponse =
          restTemplate.exchange(getUrl + ingredientName, HttpMethod.GET, null, String.class);
      // Expect 404 response when ingredient is not found
      assertEquals(HttpStatus.NOT_FOUND, getResponse.getStatusCode());
    } catch (Exception e) {
      // Ingredient is deleted, so no entity is found, and exception will be thrown
    }
  }

  @Given("the ingredient list is empty")
  public void theIngredientListIsEmpty() {
    ingredientRepository.deleteAll();
  }

  @When("I send a GET request to {string}")
  public void iSendAGETRequestTo(String path) {
    String url = BASE_PATH + path;
    try {
      response = restTemplate.exchange(url, HttpMethod.GET, null, String.class);
    } catch (HttpClientErrorException | HttpServerErrorException e) {
      response = ResponseEntity.status(e.getStatusCode()).body(e.getResponseBodyAsString());
    }
  }

  @And("the response should contain an empty list")
  public void theResponseShouldContainAnEmptyList() throws JsonProcessingException {
    ObjectMapper objectMapper = new ObjectMapper();
    List<IngredientDTO> ingredients =
        objectMapper.readValue(response.getBody(), new TypeReference<List<IngredientDTO>>() {
        });

    assertTrue(ingredients == null || ingredients.isEmpty());
  }

  @Given("the ingredient list contains {int} ingredient")
  public void theIngredientListContainsIngredient(int size) {
    ingredientRepository.deleteAll();
    for (int i = 0; i < size; i++) {
      ingredientRepository.save(
          new IngredientEntity("Name" + i, CALORIE_NUMBER, "User1", Category.FRUIT));
    }

    List<IngredientEntity> savedIngredients = ingredientRepository.findAll();
    assertEquals(savedIngredients.size(), size);

    if (size == 1) {
      byIdsRequest = Collections.singletonList(savedIngredients.get(0).getId());
    } else if (size == 2) {
      byIdsRequest = Arrays.asList(savedIngredients.get(0).getId(),
          savedIngredients.get(1).getId());
    }

  }

  @And("the response should contain {int} ingredient")
  public void theResponseShouldContainNumberIngredient(int size) throws JsonProcessingException {
    ObjectMapper objectMapper = new ObjectMapper();
    List<IngredientDTO> ingredients =
        objectMapper.readValue(response.getBody(), new TypeReference<List<IngredientDTO>>() {
        });

    assertEquals(ingredients.size(), size);
  }

  @And("the response should be list of data as in JSON file {string}")
  public void theResponseShouldBeListOfDataAsInJSONFile(String filePath) {
    try {
      String expectedJson =
          new String(Files.readAllBytes(Paths.get(RESOURCES_PATH + filePath)));

      String actualJson = response.getBody();

      JSONAssert.assertEquals(
          expectedJson, actualJson, false);

    } catch (Exception e) {
      throw new RuntimeException("Error during JSON comparison: " + e.getMessage(), e);
    }
  }

  @When("I send a PUT request to {string}")
  public void iSendAPUTRequestTo(String path) {
    String url = BASE_PATH + path;
    try {
      response =
          restTemplate.exchange(url, HttpMethod.PUT, new HttpEntity<>(ingredientDTO), String.class);
    } catch (HttpClientErrorException | HttpServerErrorException e) {
      response = ResponseEntity.status(e.getStatusCode()).body(e.getResponseBodyAsString());
    }
  }

  @When("I send a POST request by ids")
  public void iSendAPOSTRequestByIds() {
    String url = BASE_PATH + "/ingredients/byIds";

    try {
      HttpHeaders headers = new HttpHeaders();
      headers.setContentType(MediaType.APPLICATION_JSON);

      HttpEntity<List<Long>> requestEntity =
          new HttpEntity<>(byIdsRequest, headers);

      response = restTemplate.postForEntity(
          url,
          requestEntity,
          String.class
      );
    } catch (HttpClientErrorException | HttpServerErrorException e) {
      response = ResponseEntity.status(e.getStatusCode()).body(e.getResponseBodyAsString());
    }
  }

  @When("I add an ingredient and send a POST request to {string}")
  public void iAddAnIngredientAndSendAPOSTRequestTo(String path) {
    ingredientRepository.deleteAll();
    ingredientRepository.save(
        new IngredientEntity("Apple", CALORIE_NUMBER, ADDED_BY, Category.FRUIT));
    String url = BASE_PATH + path;
    try {
      response = restTemplate.postForEntity(url, ingredientDTO, String.class);
    } catch (HttpClientErrorException | HttpServerErrorException e) {
      response = ResponseEntity.status(e.getStatusCode()).body(e.getResponseBodyAsString());
    }
  }
}
