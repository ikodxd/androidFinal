package com.example.afinal.data.api

import com.example.afinal.model.Recipe
import retrofit2.http.GET
import retrofit2.http.Query

data class Category(val strCategory: String)

data class CategoryResponse(val meals: List<Category>)

interface RecipeApiService {
    @GET("search.php")
    suspend fun searchRecipes(@Query("s") query: String): RecipeResponse

    @GET("list.php?c=list")
    suspend fun getCategories(): CategoryResponse

    @GET("filter.php")
    suspend fun filterByCategory(@Query("c") category: String): RecipeResponse
}

data class RecipeResponse(val meals: List<Recipe>?)
