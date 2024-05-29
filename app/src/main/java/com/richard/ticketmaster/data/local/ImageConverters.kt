package com.richard.ticketmaster.data.local

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.richard.ticketmaster.data.models.Image

class ImageConverters {

    @TypeConverter
    fun listToJson(value: List<Image>?) = Gson().toJson(value)

    @TypeConverter
    fun jsonToList(value: String) = Gson().fromJson(value, Array<Image>::class.java).toList()
}