package com.coveros.training.flavorhub.service;

import com.coveros.training.flavorhub.model.Ingredient;
import com.coveros.training.flavorhub.model.UserPantry;
import com.coveros.training.flavorhub.repository.IngredientRepository;
import com.coveros.training.flavorhub.repository.UserPantryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service for managing user pantry
 */
@Service
@RequiredArgsConstructor
public class UserPantryService {
    
    private final UserPantryRepository userPantryRepository;
    private final IngredientRepository ingredientRepository;
    
    /**
     * Retrieves all pantry items for a specific user
     * @param userId the user ID
     * @return list of pantry items belonging to the user
     */
    @Transactional(readOnly = true)
    public List<UserPantry> getUserPantry(Long userId) {
        return userPantryRepository.findByUserId(userId);
    }
    
    /**
     * Retrieves a pantry item by its ID
     * @param id the pantry item ID
     * @return Optional containing the pantry item if found
     */
    @Transactional(readOnly = true)
    public Optional<UserPantry> getPantryItemById(Long id) {
        return userPantryRepository.findById(id);
    }
    
    /**
     * Adds a new item to the user's pantry
     * @param pantryItem the pantry item to add
     * @return the saved pantry item
     */
    @Transactional
    public UserPantry addPantryItem(UserPantry pantryItem) {
        return userPantryRepository.save(pantryItem);
    }
    
    /**
     * Updates an existing pantry item with new quantity, unit, and notes
     * @param id the pantry item ID to update
     * @param updatedPantryItem the pantry item with updated values
     * @return the updated pantry item
     * @throws IllegalArgumentException if pantry item with the given ID is not found
     */
    @Transactional
    public UserPantry updatePantryItem(Long id, UserPantry updatedPantryItem) {
        return userPantryRepository.findById(id)
            .map(existing -> {
                existing.setQuantity(updatedPantryItem.getQuantity());
                existing.setUnit(updatedPantryItem.getUnit());
                existing.setNotes(updatedPantryItem.getNotes());
                return userPantryRepository.save(existing);
            })
            .orElseThrow(() -> new IllegalArgumentException("Pantry item not found with id: " + id));
    }
    
    /**
     * Deletes a pantry item by its ID
     * @param id the pantry item ID to delete
     */
    @Transactional
    public void deletePantryItem(Long id) {
        userPantryRepository.deleteById(id);
    }
    
    /**
     * Clears all pantry items for a specific user
     * @param userId the user ID whose pantry items should be cleared
     */
    @Transactional
    public void clearUserPantry(Long userId) {
        userPantryRepository.deleteByUserId(userId);
    }
    
    /**
     * Check if user has sufficient quantity of an ingredient
     * NOTE: This method is intentionally left incomplete for workshop participants
     */
    // TODO: Implement method to check if user has enough of an ingredient
    
    /**
     * Get list of ingredient names that user has in pantry
     * NOTE: Workshop participants will implement this using Copilot
     */
    // TODO: Implement method to get ingredient names from user's pantry
}
