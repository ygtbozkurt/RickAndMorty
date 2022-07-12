package com.example.rickandmorty.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.rickandmorty.data.model.EpisodeTypeConverter
import com.example.rickandmorty.data.model.RickAndMortyCharacter

@Database(entities = [RickAndMortyCharacter::class], version = 3, exportSchema = false)
@TypeConverters(EpisodeTypeConverter::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun characterDao(): CharacterDao

    companion object {
        @Volatile
        private var instance: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase =
            instance ?: synchronized(this) {
                instance ?: buildDatabase(context).also {
                    instance = it
                }
            }

        private fun buildDatabase(appContext: Context) =
            Room.databaseBuilder(appContext, AppDatabase::class.java, "characters")
                .fallbackToDestructiveMigration()
                .build()
    }

}