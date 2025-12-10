package com.example.afinal.ui.favorites

import android.os.Bundle
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.afinal.R
import com.example.afinal.databinding.ActivityFavoritesBinding
import com.example.afinal.ui.main.RecipeAdapter
import jp.wasabeef.recyclerview.animators.SlideInUpAnimator

class FavoritesActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFavoritesBinding
    private val viewModel: FavoritesViewModel by viewModels()
    private lateinit var recipeAdapter: RecipeAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoritesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Favorite Recipes"

        setupRecyclerView()
        observeViewModel()
    }

    override fun onResume() {
        super.onResume()
        viewModel.loadFavoriteRecipes()
    }

    private fun setupRecyclerView() {
        recipeAdapter = RecipeAdapter(emptyList())
        binding.favoritesRecyclerView.apply {
            adapter = recipeAdapter
            layoutManager = LinearLayoutManager(this@FavoritesActivity)
            itemAnimator = SlideInUpAnimator()
        }
    }

    private fun observeViewModel() {
        viewModel.favoriteRecipes.observe(this) {
            recipeAdapter.updateRecipes(it)
        }

        viewModel.isLoading.observe(this) {
            binding.progressBar.isVisible = it
        }

        viewModel.isEmpty.observe(this) {
            binding.emptyTextView.isVisible = it
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            onBackPressedDispatcher.onBackPressed()
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}
