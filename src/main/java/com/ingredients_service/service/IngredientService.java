package com.ingredients_service.service;

import com.ingredients_service.dto.IngredientDTO;
import com.ingredients_service.entity.IngredientEntity;
import com.ingredients_service.repository.IngredientRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import java.util.List;
import org.apache.coyote.BadRequestException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

@Service
@Validated
public class IngredientService {

    private static final String INGREDIENT_NOT_FOUND_ERROR = "Ingredient not found for ingredient name: ";
    private static final String INGREDIENT_NAME_ALREADY_EXIST = "Ingredient name already exists: ";


    private final IngredientRepository ingredientRepository;

    public IngredientService(IngredientRepository ingredientRepository) {
        this.ingredientRepository = ingredientRepository;
    }

    public IngredientDTO createIngredient(@Valid IngredientDTO ingredientDTO)
        throws BadRequestException {
        ingredientRepository.findByName(ingredientDTO.getName());
        if( ingredientRepository.findByName(ingredientDTO.getName()).isPresent()) {
            throw new BadRequestException(INGREDIENT_NAME_ALREADY_EXIST + ingredientDTO.getName());
        }

        IngredientEntity ingredientEntity = ingredientDTO.mapToEntity();
        IngredientEntity savedEntity = ingredientRepository.save(ingredientEntity);
        return savedEntity.mapToDto();
    }

    @Transactional
    public IngredientDTO updateIngredient(String name, @Valid IngredientDTO ingredientDTO) {
        //checking if the entity exists in the database
        IngredientEntity ingredientEntity = findEntityByName(name);

        ingredientEntity = ingredientDTO.mapToEntity();
        ingredientEntity.setId(ingredientDTO.getId());
        IngredientEntity updatedEntity = ingredientRepository.save(ingredientEntity);
        return updatedEntity.mapToDto();
    }

    public void deleteIngredientByName(String name) {
        IngredientEntity ingredientEntity = findEntityByName(name);

        ingredientRepository.deleteByName(name);
    }

    public List<IngredientDTO> getAllIngredients() {
        List<IngredientEntity> listOfIngredientsEntity = ingredientRepository.findAll();
        return listOfIngredientsEntity.stream().map(IngredientEntity::mapToDto).toList();
    }

    public IngredientDTO getIngredientByName(String name) {
        IngredientEntity ingredientEntity = findEntityByName(name);
        return ingredientEntity.mapToDto();
    }

    public List<IngredientDTO> getIngredientsByIds(List<Long> ids) {
        return ingredientRepository.findAllById(ids)
            .stream()
            .map(IngredientEntity::mapToDto)
            .toList();
    }

    private IngredientEntity findEntityByName(String name) {
        return ingredientRepository.findByName(name)
                .orElseThrow(() -> new UsernameNotFoundException(INGREDIENT_NOT_FOUND_ERROR + name));
    }

}
