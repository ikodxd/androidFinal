package com.example.afinal.ui.main

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

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: RecipeRepository

    private val _recipes = MutableLiveData<List<Recipe>>()
    val recipes: LiveData<List<Recipe>> = _recipes

    init {
        val recipeDao = RecipeDatabase.getDatabase(application).recipeDao()
        repository = RecipeRepository(RetrofitInstance.api, recipeDao)
    }

    fun searchRecipes(query: String) {
        viewModelScope.launch {
            val result = repository.searchRecipes(query)
            _recipes.postValue(result)
        }
    }
}