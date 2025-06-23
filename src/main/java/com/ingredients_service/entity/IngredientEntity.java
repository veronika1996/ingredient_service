package com.ingredients_service.entity;

import com.ingredients_service.dto.IngredientDTO;
import com.ingredients_service.enums.Category;
import jakarta.persistence.*;


@Entity
@Table(name = "app_ingredient")
public class IngredientEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String name;
    private int calorieNumber;
    private String addedBy;
    private Category category;

    public IngredientEntity() {
    }

    public IngredientEntity(String name, int calorieNumber, String addedBy, Category category) {
        this.name = name;
        this.calorieNumber = calorieNumber;
        this.addedBy = addedBy;
        this.category = category;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getCalorieNumber() {
        return calorieNumber;
    }

    public String getAddedBy() {
        return addedBy;
    }

    public Category getCategory() {
        return category;
    }

    public IngredientDTO mapToDto() {
        return new IngredientDTO(this.id, this.getName(), this.getCalorieNumber(), this.getAddedBy(), this.getCategory());
    }
}
