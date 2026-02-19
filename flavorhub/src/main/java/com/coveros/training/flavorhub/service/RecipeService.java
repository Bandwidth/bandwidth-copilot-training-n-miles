package com.coveros.training.flavorhub.service;

import com.coveros.training.flavorhub.model.Recipe;
import com.coveros.training.flavorhub.repository.RecipeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service for managing recipes
 */
@Service
@RequiredArgsConstructor
public class RecipeService {
    
    private final RecipeRepository recipeRepository;
    
    /**
     * Retrieves all recipes from the database
     * @return list of all recipes
     */
    @Transactional(readOnly = true)
    public List<Recipe> getAllRecipes() {
        return recipeRepository.findAll();
    }
    
    /**
     * Retrieves a recipe by its ID
     * @param id the recipe ID
     * @return Optional containing the recipe if found
     */
    @Transactional(readOnly = true)
    public Optional<Recipe> getRecipeById(Long id) {
        return recipeRepository.findById(id);
    }
    
    /**
     * Retrieves all recipes with a specific difficulty level
     * @param difficultyLevel the difficulty level (e.g., Easy, Medium, Hard)
     * @return list of recipes with the specified difficulty level
     */
    @Transactional(readOnly = true)
    public List<Recipe> getRecipesByDifficulty(String difficultyLevel) {
        return recipeRepository.findByDifficultyLevel(difficultyLevel);
    }
    
    /**
     * Retrieves all recipes of a specific cuisine type
     * @param cuisineType the cuisine type (e.g., Italian, Mexican, Chinese)
     * @return list of recipes of the specified cuisine type
     */
    @Transactional(readOnly = true)
    public List<Recipe> getRecipesByCuisine(String cuisineType) {
        return recipeRepository.findByCuisineType(cuisineType);
    }
    
    /**
     * Searches for recipes by name containing the search term (case-insensitive)
     * @param searchTerm the search term
     * @return list of matching recipes
     */
    @Transactional(readOnly = true)
    public List<Recipe> searchRecipes(String searchTerm) {
        return recipeRepository.findByNameContainingIgnoreCase(searchTerm);
    }
    
    /**
     * Saves or updates a recipe
     * @param recipe the recipe to save
     * @return the saved recipe
     */
    @Transactional
    public Recipe saveRecipe(Recipe recipe) {
        return recipeRepository.save(recipe);
    }
    
    /**
     * Deletes a recipe by its ID
     * @param id the recipe ID to delete
     */
    @Transactional
    public void deleteRecipe(Long id) {
        recipeRepository.deleteById(id);
    }
    
    /**
     * Find recipes that can be made based on available ingredients in the pantry
     * NOTE: This method is intentionally left incomplete for workshop participants
     * Participants will use GitHub Copilot to implement this recommendation logic
     */
    // TODO: Implement method to recommend recipes based on pantry ingredients
    
    /**
     * Get recipes that match specific dietary requirements or filters
     * NOTE: This is a more advanced feature to be implemented during the workshop
     */
    // TODO: Implement advanced filtering logic
}
