package com.example.afinal.data.api

import com.example.afinal.model.Recipe
import retrofit2.http.GET
import retrofit2.http.Query

interface RecipeApiService {
    @GET("search.php")
    suspend fun searchRecipes(@Query("s") query: String): RecipeResponse
}

data class RecipeResponse(val meals: List<Recipe>?) // Changed to nullable
