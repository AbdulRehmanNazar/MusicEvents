package com.richard.ticketmaster.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.richard.ticketmaster.data.models.Venue
import kotlinx.coroutines.flow.Flow

@Dao
interface VennuDao {
    @Query("SELECT * FROM Venue")
    fun getAllVennu(): Flow<List<Venue>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllVennu(venues: List<Venue>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertVennu(venue: Venue)
}