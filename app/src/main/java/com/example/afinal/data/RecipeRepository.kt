package com.example.afinal.data

import com.example.afinal.data.api.RecipeApiService
import com.example.afinal.data.db.RecipeDao
import com.example.afinal.model.Recipe

class RecipeRepository(
    private val apiService: RecipeApiService,
    private val recipeDao: RecipeDao // Добавлено
) {
    suspend fun searchRecipes(query: String): List<Recipe>? {
        // Новая логика кеширования
        val localRecipes = recipeDao.searchRecipes(query)
        if (localRecipes.isNotEmpty()) {
            return localRecipes
        }

        val remoteRecipes = apiService.searchRecipes(query).meals
        remoteRecipes?.let {
            recipeDao.insertRecipes(it)
        }
        return remoteRecipes
    }
}
