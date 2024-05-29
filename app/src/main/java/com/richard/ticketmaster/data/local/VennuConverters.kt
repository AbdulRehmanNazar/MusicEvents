package com.richard.ticketmaster.data.local

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.richard.ticketmaster.data.models.Venue

class VennuConverters {

    @TypeConverter
    fun listToJson(value: List<Venue>?) = Gson().toJson(value)

    @TypeConverter
    fun jsonToList(value: String) = Gson().fromJson(value, Array<Venue>::class.java).toList()
}