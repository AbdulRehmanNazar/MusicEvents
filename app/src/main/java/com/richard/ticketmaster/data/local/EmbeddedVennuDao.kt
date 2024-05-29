package com.richard.ticketmaster.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.richard.ticketmaster.data.models.EmbeddedVennu
import kotlinx.coroutines.flow.Flow

@Dao
interface EmbeddedVennuDao {
    @Query("SELECT * FROM EmbeddedVennu")
    fun getAllEmbededVennu(): Flow<List<EmbeddedVennu>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllEmbeddedVennu(embededVenues: List<EmbeddedVennu>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertEmbededVennu(embededVenue: EmbeddedVennu)
}