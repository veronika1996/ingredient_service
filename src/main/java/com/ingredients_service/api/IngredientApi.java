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
      summary = "Update an existing ingredient",
      description = "This endpoint allows you to update an existing ingredient by its name.",
      responses = {
          @ApiResponse(
              responseCode = "200",
              description = "Ingredient successfully updated",
              content = @Content(mediaType = "application/json", schema = @Schema(implementation = IngredientDTO.class))
          ),
          @ApiResponse(responseCode = "404", description = "Ingredient not found")
      }
  )
  ResponseEntity<IngredientDTO> updateIngredient(@Parameter(description = "Name of the ingredient to be updated") @PathVariable String name,
      @Parameter(description = "Updated ingredient details") @RequestBody IngredientDTO ingredientDTO);

  @Operation(
      summary = "Delete an ingredient",
      description = "This endpoint allows you to delete an ingredient by its name.",
      responses = {
          @ApiResponse(responseCode = "204", description = "Ingredient successfully deleted"),
          @ApiResponse(responseCode = "404", description = "Ingredient not found")
      }
  )
  ResponseEntity<Void> deleteIngredient(@Parameter(description = "Name of the ingredient to be deleted") @PathVariable String name);

  @Operation(
      summary = "Get all ingredients",
      description = "This endpoint returns a list of all ingredients.",
      responses = {
          @ApiResponse(
              responseCode = "200",
              description = "List of all ingredients retrieved successfully",
              content = @Content(mediaType = "application/json", schema = @Schema(implementation = IngredientDTO.class))
          )
      }
  )
  ResponseEntity<List<IngredientDTO>> getAllIngredients();

  @Operation(
      summary = "Get an ingredient by name",
      description = "This endpoint allows you to get an ingredient by its name.",
      responses = {
          @ApiResponse(
              responseCode = "200",
              description = "Ingredient retrieved successfully",
              content = @Content(mediaType = "application/json", schema = @Schema(implementation = IngredientDTO.class))
          ),
          @ApiResponse(responseCode = "404", description = "Ingredient not found")
      }
  )
  ResponseEntity<IngredientDTO> getIngredientByName(@Parameter(description = "Name of the ingredient to be fetched") @PathVariable String name);

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
