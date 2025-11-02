package com.example.afinal.data

import com.example.afinal.data.api.RecipeApiService
import com.example.afinal.model.Recipe

class RecipeRepository(private val apiService: RecipeApiService) {
    suspend fun searchRecipes(query: String): List<Recipe>? {
        return apiService.searchRecipes(query).meals
    }
}
