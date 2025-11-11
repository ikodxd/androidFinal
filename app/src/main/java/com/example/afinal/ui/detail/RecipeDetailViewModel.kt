package com.example.afinal.ui.detail

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.afinal.data.RecipeRepository
import com.example.afinal.data.api.RetrofitInstance
import com.example.afinal.data.db.RecipeDatabase
import com.example.afinal.model.Recipe
import kotlinx.coroutines.launch

class RecipeDetailViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: RecipeRepository

    private val _recipe = MutableLiveData<Recipe?>()
    val recipe: LiveData<Recipe?> = _recipe

    init {
        val recipeDao = RecipeDatabase.getDatabase(application).recipeDao()
        repository = RecipeRepository(RetrofitInstance.api, recipeDao)
    }

    fun getRecipeById(id: String) {
        viewModelScope.launch {
            _recipe.postValue(repository.getRecipeById(id))
        }
    }

    fun toggleFavorite() {
        _recipe.value?.let { currentRecipe ->
            val updatedRecipe = currentRecipe.copy(isFavorite = !currentRecipe.isFavorite)
            viewModelScope.launch {
                repository.updateRecipe(updatedRecipe)
                _recipe.postValue(updatedRecipe)
            }
        }
    }
}