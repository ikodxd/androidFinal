package com.example.afinal.ui.main

import android.app.Application // Добавлено
import androidx.lifecycle.AndroidViewModel // Изменено с ViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.afinal.data.RecipeRepository
import com.example.afinal.data.api.RetrofitInstance
import com.example.afinal.data.db.RecipeDatabase // Добавлено
import com.example.afinal.model.Recipe
import kotlinx.coroutines.launch

class MainViewModel(application: Application) : AndroidViewModel(application) { // Изменено

    private val repository: RecipeRepository // Изменено

    private val _recipes = MutableLiveData<List<Recipe>>()
    val recipes: LiveData<List<Recipe>> = _recipes

    init { // Добавлен блок init
        val recipeDao = RecipeDatabase.getDatabase(application).recipeDao()
        repository = RecipeRepository(RetrofitInstance.api, recipeDao)
    }

    fun searchRecipes(query: String) {
        viewModelScope.launch {
            val result = repository.searchRecipes(query)
            _recipes.postValue(result ?: emptyList())
        }
    }
}
