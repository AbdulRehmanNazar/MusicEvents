package com.richard.ticketmaster.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.richard.ticketmaster.data.models.City
import kotlinx.coroutines.flow.Flow

@Dao
interface CityDao {
    @Query("SELECT * FROM City")
    fun getAllCity(): Flow<List<City>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllCity(cities: List<City>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCity(city: City)
}