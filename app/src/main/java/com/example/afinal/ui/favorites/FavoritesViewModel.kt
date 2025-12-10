package com.example.afinal.ui.favorites

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

class FavoritesViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: RecipeRepository

    private val _favoriteRecipes = MutableLiveData<List<Recipe>>()
    val favoriteRecipes: LiveData<List<Recipe>> = _favoriteRecipes

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _isEmpty = MutableLiveData<Boolean>()
    val isEmpty: LiveData<Boolean> = _isEmpty

    init {
        val recipeDao = RecipeDatabase.getDatabase(application).recipeDao()
        repository = RecipeRepository(RetrofitInstance.api, recipeDao)
        loadFavoriteRecipes()
    }

    fun loadFavoriteRecipes() {
        viewModelScope.launch {
            _isLoading.postValue(true)
            val result = repository.getFavoriteRecipes()
            _favoriteRecipes.postValue(result)
            _isEmpty.postValue(result.isEmpty())
            _isLoading.postValue(false)
        }
    }
}
