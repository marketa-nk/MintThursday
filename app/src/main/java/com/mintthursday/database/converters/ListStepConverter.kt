package com.mintthursday.database.converters

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.mintthursday.models.Step

class ListStepConverter {

    private val listType = object : TypeToken<List<Step>>() {}.type

    @TypeConverter
    fun toJson(list: List<Step>): String {
        return Gson().toJson(list, listType)
    }

    @TypeConverter
    fun fromJson(json: String): List<Step> {
        return Gson().fromJson(json, listType)
    }
}