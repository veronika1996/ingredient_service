package com.ingredients_service.service;

import com.ingredients_service.dto.IngredientDTO;
import com.ingredients_service.entity.IngredientEntity;
import com.ingredients_service.repository.IngredientRepository;
import jakarta.validation.Valid;
import java.util.List;
import org.apache.coyote.BadRequestException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

@Service
@Validated
public class IngredientService {

    private static final String INGREDIENT_NOT_FOUND_ERROR = "Nije pronadjen sastojak sa imenom: ";
    private static final String INGREDIENT_NAME_ALREADY_EXIST = "Vec postoji sastojak sa imenom: ";


    private final IngredientRepository ingredientRepository;

    public IngredientService(IngredientRepository ingredientRepository) {
        this.ingredientRepository = ingredientRepository;
    }

    public IngredientDTO createIngredient(@Valid IngredientDTO ingredientDTO)
        throws BadRequestException {
      if (ingredientRepository.findByNameAndAddedBy(ingredientDTO.getName(),
          ingredientDTO.getAddedBy()).isPresent()) {
            throw new BadRequestException(INGREDIENT_NAME_ALREADY_EXIST + ingredientDTO.getName());
        }

        IngredientEntity ingredientEntity = ingredientDTO.mapToEntity();
        IngredientEntity savedEntity = ingredientRepository.save(ingredientEntity);
        return savedEntity.mapToDto();
    }


  public List<IngredientDTO> getAllIngredientsByUsername(String username) {
    List<IngredientEntity> listOfIngredientsEntity = ingredientRepository.findAllByAddedBy(
        username);
        return listOfIngredientsEntity.stream().map(IngredientEntity::mapToDto).toList();
    }

  public IngredientDTO getIngredientByNameAndUsername(String name, String username) {
    IngredientEntity ingredientEntity = ingredientRepository.findByNameAndAddedBy(name, username)
        .orElseThrow(() -> new UsernameNotFoundException(INGREDIENT_NOT_FOUND_ERROR + name));
        return ingredientEntity.mapToDto();
    }

    public List<IngredientDTO> getIngredientsByIds(List<Long> ids) {
        return ingredientRepository.findAllById(ids)
            .stream()
            .map(IngredientEntity::mapToDto)
            .toList();
    }

}
