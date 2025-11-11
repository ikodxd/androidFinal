package com.example.afinal.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.afinal.model.Recipe

@Database(entities = [Recipe::class], version = 2, exportSchema = false) // ВЕРСИЯ ИЗМЕНЕНА НА 2
abstract class RecipeDatabase : RoomDatabase() {

    abstract fun recipeDao(): RecipeDao

    companion object {
        @Volatile
        private var INSTANCE: RecipeDatabase? = null

        fun getDatabase(context: Context): RecipeDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    RecipeDatabase::class.java,
                    "recipe_database"
                )
                .fallbackToDestructiveMigration() // Добавлено для обработки смены версии
                .build()
                INSTANCE = instance
                instance
            }
        }
    }
}
