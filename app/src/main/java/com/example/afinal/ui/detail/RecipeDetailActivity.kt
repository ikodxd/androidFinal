package com.example.afinal.ui.detail

import android.os.Bundle
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import coil.load
import com.example.afinal.R
import com.example.afinal.databinding.ActivityRecipeDetailBinding

class RecipeDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRecipeDetailBinding
    private val viewModel: RecipeDetailViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRecipeDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val recipeId = intent.getStringExtra("recipe_id")

        if (recipeId != null) {
            viewModel.getRecipeById(recipeId)
        }

        observeViewModel()

        binding.fabFavorite.setOnClickListener {
            viewModel.toggleFavorite()
        }
    }

    private fun observeViewModel() {
        viewModel.recipe.observe(this) {
            if (it != null) {
                binding.collapsingToolbar.title = it.name
                binding.recipeName.text = it.name
                binding.recipeCategory.text = it.category
                binding.recipeInstructions.text = it.instructions
                binding.recipeImage.load(it.thumb) {
                    crossfade(true)
                }

                if (it.isFavorite) {
                    binding.fabFavorite.setImageResource(R.drawable.ic_favorite)
                } else {
                    binding.fabFavorite.setImageResource(R.drawable.ic_favorite_border)
                }
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            onBackPressedDispatcher.onBackPressed()
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}