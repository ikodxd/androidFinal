package com.example.afinal.ui.main

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.afinal.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainViewModel by viewModels()
    private lateinit var recipeAdapter: RecipeAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupRecyclerView()
        observeViewModel()

        binding.searchEditText.addTextChangedListener {
            viewModel.searchRecipes(it.toString())
        }
        
        // Initial search
        viewModel.searchRecipes("c")
    }

    private fun setupRecyclerView() {
        recipeAdapter = RecipeAdapter(emptyList())
        binding.recipesRecyclerView.apply {
            adapter = recipeAdapter
            layoutManager = LinearLayoutManager(this@MainActivity)
        }
    }

    private fun observeViewModel() {
        viewModel.recipes.observe(this) {
            recipeAdapter.updateRecipes(it)
        }
    }
}
