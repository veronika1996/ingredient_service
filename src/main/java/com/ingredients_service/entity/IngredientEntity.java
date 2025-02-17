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

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCalorieNumber() {
        return calorieNumber;
    }

    public void setCalorieNumber(int calorieNumber) {
        this.calorieNumber = calorieNumber;
    }

    public String getAddedBy() {
        return addedBy;
    }

    public void setAddedBy(String addedBy) {
        this.addedBy = addedBy;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public IngredientDTO mapToDto() {
        return new IngredientDTO(this.getName(), this.getCalorieNumber(), this.getAddedBy(), this.getCategory());
    }
}
