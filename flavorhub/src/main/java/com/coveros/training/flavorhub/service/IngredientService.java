package com.coveros.training.flavorhub.service;

import com.coveros.training.flavorhub.model.Ingredient;
import com.coveros.training.flavorhub.repository.IngredientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service for managing ingredients
 */
@Service
@RequiredArgsConstructor
public class IngredientService {
    
    private final IngredientRepository ingredientRepository;
    
    /**
     * Retrieves all ingredients from the database
     * @return list of all ingredients
     */
    @Transactional(readOnly = true)
    public List<Ingredient> getAllIngredients() {
        return ingredientRepository.findAll();
    }
    
    /**
     * Retrieves an ingredient by its ID
     * @param id the ingredient ID
     * @return Optional containing the ingredient if found
     */
    @Transactional(readOnly = true)
    public Optional<Ingredient> getIngredientById(Long id) {
        return ingredientRepository.findById(id);
    }
    
    /**
     * Retrieves an ingredient by its name (case-insensitive)
     * @param name the ingredient name
     * @return Optional containing the ingredient if found
     */
    @Transactional(readOnly = true)
    public Optional<Ingredient> getIngredientByName(String name) {
        return ingredientRepository.findByNameIgnoreCase(name);
    }
    
    /**
     * Retrieves all ingredients in a specific category
     * @param category the ingredient category
     * @return list of ingredients in the category
     */
    @Transactional(readOnly = true)
    public List<Ingredient> getIngredientsByCategory(String category) {
        return ingredientRepository.findByCategory(category);
    }
    
    /**
     * Searches for ingredients by name containing the search term (case-insensitive)
     * @param searchTerm the search term
     * @return list of matching ingredients
     */
    @Transactional(readOnly = true)
    public List<Ingredient> searchIngredients(String searchTerm) {
        return ingredientRepository.findByNameContainingIgnoreCase(searchTerm);
    }
    
    /**
     * Saves or updates an ingredient
     * @param ingredient the ingredient to save
     * @return the saved ingredient
     */
    @Transactional
    public Ingredient saveIngredient(Ingredient ingredient) {
        return ingredientRepository.save(ingredient);
    }
    
    /**
     * Deletes an ingredient by its ID
     * @param id the ingredient ID to delete
     */
    @Transactional
    public void deleteIngredient(Long id) {
        ingredientRepository.deleteById(id);
    }
}
