package com.richard.ticketmaster.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.richard.ticketmaster.data.models.Image
import kotlinx.coroutines.flow.Flow

@Dao
interface ImageDao {

    @Query("SELECT * FROM Image")
    fun getAllEvents(): Flow<List<Image>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllEvents(images: List<Image>)

}