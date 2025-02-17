package com.ingredients_service.controller;


import com.ingredients_service.api.IngredientApi;
import com.ingredients_service.dto.IngredientDTO;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/ingredients")
public class IngredientController implements IngredientApi {

  @Override
  public ResponseEntity<IngredientDTO> createIngredient(IngredientDTO ingredientDTO) {
    return null;
  }

  @Override
  public ResponseEntity<IngredientDTO> updateIngredient(String name, IngredientDTO ingredientDTO) {
    return null;
  }

  @Override
  public ResponseEntity<Void> deleteIngredient(String name) {
    return null;
  }

  @Override
  public ResponseEntity<List<IngredientDTO>> getAllIngredients() {
    return null;
  }

  @Override
  public ResponseEntity<IngredientDTO> getIngredientByName(String name) {
    return null;
  }
}
