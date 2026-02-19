package com.coveros.training.flavorhub.service;

import com.coveros.training.flavorhub.model.Recipe;
import com.coveros.training.flavorhub.repository.RecipeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * Unit tests for RecipeService
 */
@ExtendWith(MockitoExtension.class)
class RecipeServiceTest {
    
    @Mock
    private RecipeRepository recipeRepository;
    
    @InjectMocks
    private RecipeService recipeService;
    
    private Recipe testRecipe;
    
    @BeforeEach
    void setUp() {
        testRecipe = new Recipe("Test Recipe", "Test Description", 10, 20, 4, "Easy", "Italian");
        testRecipe.setId(1L);
        testRecipe.setAverageRating(0.0);
        testRecipe.setRatingCount(0);
    }
    
    @Test
    void testAddRating_WhenFirstRating_ThenAverageEqualsRating() {
        // Arrange
        when(recipeRepository.findById(1L)).thenReturn(Optional.of(testRecipe));
        when(recipeRepository.save(any(Recipe.class))).thenAnswer(invocation -> invocation.getArgument(0));
        
        // Act
        Recipe result = recipeService.addRating(1L, 5);
        
        // Assert
        assertEquals(5.0, result.getAverageRating());
        assertEquals(1, result.getRatingCount());
        verify(recipeRepository).save(testRecipe);
    }
    
    @Test
    void testAddRating_WhenMultipleRatings_ThenAverageCalculatedCorrectly() {
        // Arrange - recipe already has ratings
        testRecipe.setAverageRating(4.5);
        testRecipe.setRatingCount(4);
        when(recipeRepository.findById(1L)).thenReturn(Optional.of(testRecipe));
        when(recipeRepository.save(any(Recipe.class))).thenAnswer(invocation -> invocation.getArgument(0));
        
        // Act - add a rating of 3
        Recipe result = recipeService.addRating(1L, 3);
        
        // Assert - ((4.5 * 4) + 3) / 5 = 4.2
        assertEquals(4.2, result.getAverageRating(), 0.01);
        assertEquals(5, result.getRatingCount());
        verify(recipeRepository).save(testRecipe);
    }
    
    @Test
    void testAddRating_WhenRatingLessThanOne_ThenThrowsException() {
        // Act & Assert
        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> recipeService.addRating(1L, 0)
        );
        assertEquals("Rating must be between 1 and 5", exception.getMessage());
        verify(recipeRepository, never()).save(any());
    }
    
    @Test
    void testAddRating_WhenRatingGreaterThanFive_ThenThrowsException() {
        // Act & Assert
        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> recipeService.addRating(1L, 6)
        );
        assertEquals("Rating must be between 1 and 5", exception.getMessage());
        verify(recipeRepository, never()).save(any());
    }
    
    @Test
    void testAddRating_WhenRecipeNotFound_ThenThrowsException() {
        // Arrange
        when(recipeRepository.findById(999L)).thenReturn(Optional.empty());
        
        // Act & Assert
        IllegalStateException exception = assertThrows(
            IllegalStateException.class,
            () -> recipeService.addRating(999L, 5)
        );
        assertTrue(exception.getMessage().contains("Recipe not found"));
        verify(recipeRepository, never()).save(any());
    }
    
    @Test
    void testAddRating_WhenValidRatingOfOne_ThenUpdatesCorrectly() {
        // Arrange
        when(recipeRepository.findById(1L)).thenReturn(Optional.of(testRecipe));
        when(recipeRepository.save(any(Recipe.class))).thenAnswer(invocation -> invocation.getArgument(0));
        
        // Act
        Recipe result = recipeService.addRating(1L, 1);
        
        // Assert
        assertEquals(1.0, result.getAverageRating());
        assertEquals(1, result.getRatingCount());
    }
    
    @Test
    void testAddRating_WhenValidRatingOfFive_ThenUpdatesCorrectly() {
        // Arrange
        when(recipeRepository.findById(1L)).thenReturn(Optional.of(testRecipe));
        when(recipeRepository.save(any(Recipe.class))).thenAnswer(invocation -> invocation.getArgument(0));
        
        // Act
        Recipe result = recipeService.addRating(1L, 5);
        
        // Assert
        assertEquals(5.0, result.getAverageRating());
        assertEquals(1, result.getRatingCount());
    }
}
