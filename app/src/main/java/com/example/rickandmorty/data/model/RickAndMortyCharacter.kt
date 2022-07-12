package com.example.rickandmorty.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

@Entity(tableName = "characters")
data class RickAndMortyCharacter(
    @PrimaryKey
    val id: Int,
    val name: String,
    val gender: String,
    val created: String,
    val type: String,
    val image: String,
    val species: String,
    val status: String,
    val url: String,
    val episode: MutableList<String>,
)

class EpisodeTypeConverter {
    @TypeConverter
    fun fromString(value: String?): MutableList<String> {
        val listType = object : TypeToken<MutableList<String>>() {}.type
        return Gson().fromJson(value, listType)
    }

    @TypeConverter
    fun fromList(list: MutableList<String>): String {
        return Gson().toJson(list)
    }
}