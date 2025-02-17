package com.ingredients_service.controller;


import com.ingredients_service.api.IngredientApi;
import com.ingredients_service.dto.IngredientDTO;
import com.ingredients_service.service.IngredientService;
import java.util.List;
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
  public ResponseEntity<IngredientDTO> createIngredient(@RequestBody IngredientDTO ingredientDTO) {
    IngredientDTO response = ingredientService.createIngredient(ingredientDTO);
    return ResponseEntity
        .status(HttpStatus.CREATED)
        .body(response);
  }

  @PutMapping("/{name}")
  public ResponseEntity<IngredientDTO> updateIngredient(@PathVariable String name, @RequestBody IngredientDTO ingredientDTO) {
    IngredientDTO response = ingredientService.updateIngredient(name, ingredientDTO);
    return ResponseEntity
        .status(HttpStatus.OK)
        .body(response);
  }


  @DeleteMapping("/{name}")
  public ResponseEntity<Void> deleteIngredient(@PathVariable String name) {
    ingredientService.deleteIngredientByName(name);
    return ResponseEntity.noContent().build();
  }

  @GetMapping
  public ResponseEntity<List<IngredientDTO>> getAllIngredients() {
    List<IngredientDTO> response = ingredientService.getAllIngredients();
    return ResponseEntity
        .status(HttpStatus.OK)
        .body(response);
  }

  @GetMapping("/{name}")
  public ResponseEntity<IngredientDTO> getIngredientByName(@PathVariable String name) {
    IngredientDTO response = ingredientService.getIngredientByName(name);
    return ResponseEntity
        .status(HttpStatus.OK)
        .body(response);
  }
}
