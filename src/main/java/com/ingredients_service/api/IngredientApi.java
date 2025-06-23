package com.ingredients_service.api;

import com.ingredients_service.dto.IngredientDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import java.util.List;
import org.apache.coyote.BadRequestException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

public interface IngredientApi {

  @Operation(
      summary = "Create a new ingredient",
      description = "This endpoint allows you to create a new ingredient.",
      responses = {
          @ApiResponse(
              responseCode = "201",
              description = "Ingredient successfully created",
              content = @Content(mediaType = "application/json", schema = @Schema(implementation = IngredientDTO.class))
          ),
          @ApiResponse(responseCode = "400", description = "Invalid input")
      }
  )
  ResponseEntity<IngredientDTO> createIngredient(@Parameter(description = "Ingredient to be created") @RequestBody IngredientDTO ingredientDTO)
      throws BadRequestException;

  @Operation(
      summary = "Get all ingredients by username",
      description = "This endpoint returns a list of all ingredients for a specific user.",
      responses = {
          @ApiResponse(
              responseCode = "200",
              description = "List of all ingredients retrieved for user with given username successfully",
              content = @Content(mediaType = "application/json", schema = @Schema(implementation = IngredientDTO.class))
          )
      }
  )
  ResponseEntity<List<IngredientDTO>> getAllIngredientsByUsername(
      @Parameter(description = "Name of the ingredient to be fetched") @RequestParam String username);

  @Operation(
      summary = "Get an ingredient by name and username",
      description = "This endpoint allows you to get an ingredient by its name and username.",
      responses = {
          @ApiResponse(
              responseCode = "200",
              description = "Ingredient retrieved successfully",
              content = @Content(mediaType = "application/json", schema = @Schema(implementation = IngredientDTO.class))
          ),
          @ApiResponse(responseCode = "404", description = "Ingredient not found")
      }
  )
  ResponseEntity<IngredientDTO> getIngredientByNameAndUserName(
      @Parameter(description = "Name of the ingredient to be fetched") @RequestParam String name,
      @Parameter(description = "Username of the ingredient to be fetched") @RequestParam String username);

  @Operation(
      summary = "Get ingredients by ids",
      description = "This endpoint returns a list by ids",
      responses = {
          @ApiResponse(
              responseCode = "200",
              description = "Ingredient retrieved successfully",
              content = @Content(mediaType = "application/json", schema = @Schema(implementation = IngredientDTO.class))
          ),
          @ApiResponse(responseCode = "404", description = "Ingredient not found")
      }
  )
  ResponseEntity<List<IngredientDTO>> getIngredientsByIds(@RequestBody List<Long> ids);
}
