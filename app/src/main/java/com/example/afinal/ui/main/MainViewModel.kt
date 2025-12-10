package com.example.afinal.ui.main

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.afinal.data.RecipeRepository
import com.example.afinal.data.api.Category
import com.example.afinal.data.api.RetrofitInstance
import com.example.afinal.data.db.RecipeDatabase
import com.example.afinal.model.Recipe
import kotlinx.coroutines.launch

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: RecipeRepository

    private val _recipes = MutableLiveData<List<Recipe>>()
    val recipes: LiveData<List<Recipe>> = _recipes

    private val _categories = MutableLiveData<List<Category>>()
    val categories: LiveData<List<Category>> = _categories

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    init {
        val recipeDao = RecipeDatabase.getDatabase(application).recipeDao()
        repository = RecipeRepository(RetrofitInstance.api, recipeDao)
        loadCategories()
    }

    fun loadCategories() {
        viewModelScope.launch {
            _isLoading.postValue(true)
            val categoryList = repository.getCategories()
            _categories.postValue(categoryList ?: emptyList())
            _isLoading.postValue(false)
        }
    }

    fun searchRecipes(query: String) {
        viewModelScope.launch {
            _isLoading.postValue(true)
            val result = repository.searchRecipes(query)
            _recipes.postValue(result ?: emptyList())
            _isLoading.postValue(false)
        }
    }

    fun filterByCategory(category: String) {
        viewModelScope.launch {
            _isLoading.postValue(true)
            val result = repository.filterByCategory(category)
            _recipes.postValue(result ?: emptyList())
            _isLoading.postValue(false)
        }
    }
}
