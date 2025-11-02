package com.example.afinal.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.afinal.data.RecipeRepository
import com.example.afinal.data.api.RetrofitInstance
import com.example.afinal.model.Recipe
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {

    private val repository = RecipeRepository(RetrofitInstance.api)

    private val _recipes = MutableLiveData<List<Recipe>>()
    val recipes: LiveData<List<Recipe>> = _recipes

    fun searchRecipes(query: String) {
        viewModelScope.launch {
            val result = repository.searchRecipes(query)
            _recipes.postValue(result ?: emptyList())
        }
    }
}
