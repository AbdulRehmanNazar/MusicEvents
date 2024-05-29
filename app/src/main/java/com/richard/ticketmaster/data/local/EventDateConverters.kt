package com.richard.ticketmaster.data.local

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.richard.ticketmaster.data.models.EventDate

class EventDateConverters {

    @TypeConverter
    fun listToJson(value: EventDate?) = Gson().toJson(value)

    @TypeConverter
    fun jsonToList(value: String) = Gson().fromJson(value, EventDate::class.java)
}