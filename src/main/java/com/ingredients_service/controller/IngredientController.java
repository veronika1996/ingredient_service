package com.ingredients_service.controller;


import com.ingredients_service.api.IngredientApi;
import com.ingredients_service.dto.IngredientDTO;
import com.ingredients_service.service.IngredientService;
import java.util.List;
import org.apache.coyote.BadRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/ingredients")
public class IngredientController implements IngredientApi {

  private final IngredientService ingredientService;

  public IngredientController(IngredientService ingredientService) {
    this.ingredientService = ingredientService;
  }

  @PostMapping
  public ResponseEntity<IngredientDTO> createIngredient(@RequestBody IngredientDTO ingredientDTO)
      throws BadRequestException {
    IngredientDTO response = ingredientService.createIngredient(ingredientDTO);
    return ResponseEntity
        .status(HttpStatus.CREATED)
        .body(response);
  }

  @GetMapping(params = {"username", "!name"})
  public ResponseEntity<List<IngredientDTO>> getAllIngredientsByUsername(String username) {
    List<IngredientDTO> response = ingredientService.getAllIngredientsByUsername(username);
    return ResponseEntity
        .status(HttpStatus.OK)
        .body(response);
  }

  @GetMapping(params = {"name", "username"})
  public ResponseEntity<IngredientDTO> getIngredientByNameAndUserName(@RequestParam String name, @RequestParam String username) {
    IngredientDTO response = ingredientService.getIngredientByNameAndUsername(name, username);
    return ResponseEntity
        .status(HttpStatus.OK)
        .body(response);
  }

  @PostMapping("/byIds")
  public ResponseEntity<List<IngredientDTO>> getIngredientsByIds(
      @RequestBody List<Long> ids) {
    List<IngredientDTO> ingredients = ingredientService.getIngredientsByIds(ids);
    return ResponseEntity.ok(ingredients);
  }
}
