package com.example.afinal.data

import com.example.afinal.data.api.RecipeApiService
import com.example.afinal.data.db.RecipeDao
import com.example.afinal.model.Recipe

class RecipeRepository(private val apiService: RecipeApiService, private val recipeDao: RecipeDao) {
    suspend fun searchRecipes(query: String): List<Recipe> {
        val localRecipes = recipeDao.searchRecipes("%" + query + "%")
        if (localRecipes.isNotEmpty()) {
            return localRecipes
        }

        val remoteRecipes = apiService.searchRecipes(query).meals
        if (remoteRecipes != null) {
            recipeDao.insertRecipes(remoteRecipes)
            return remoteRecipes
        }
        return emptyList()
    }

    suspend fun getRecipeById(id: String): Recipe? {
        return recipeDao.getRecipeById(id)
    }

    suspend fun updateRecipe(recipe: Recipe) {
        recipeDao.updateRecipe(recipe)
    }

    suspend fun getFavoriteRecipes(): List<Recipe> {
        return recipeDao.getFavoriteRecipes()
    }
}