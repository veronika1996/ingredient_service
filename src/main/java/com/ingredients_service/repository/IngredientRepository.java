package com.ingredients_service.repository;

import com.ingredients_service.entity.IngredientEntity;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IngredientRepository extends JpaRepository<IngredientEntity, Long> {
    Optional<IngredientEntity> findByName(String name);

    List<IngredientEntity> findAllByAddedBy(String addedBy);

    Optional<IngredientEntity> findByNameAndAddedBy(String name, String addedBy);
}
