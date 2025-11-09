package com.example.afinal.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.afinal.model.Recipe

@Dao
interface RecipeDao {
    @Query("SELECT * FROM recipes WHERE name LIKE '%' || :query || '%'")
    suspend fun searchRecipes(query: String): List<Recipe>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRecipes(recipes: List<Recipe>)
}
