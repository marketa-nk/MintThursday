package com.mintthursday.database.converters

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class ListStringConverter {

    private val listType = object : TypeToken<List<String>>() {}.type

    @TypeConverter
    fun toJson(list: List<String>): String {
        return Gson().toJson(list, listType)
    }

    @TypeConverter
    fun fromJson(json: String): List<String> {
        return Gson().fromJson(json, listType)
    }
}