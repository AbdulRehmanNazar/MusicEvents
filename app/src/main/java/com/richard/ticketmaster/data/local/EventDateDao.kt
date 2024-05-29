package com.richard.ticketmaster.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.richard.ticketmaster.data.models.EventDate
import kotlinx.coroutines.flow.Flow

@Dao
interface EventDateDao {
    @Query("SELECT * FROM EventDate")
    fun getAllEventDate(): Flow<List<EventDate>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllEventDate(eventDates: List<EventDate>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertEventDate(eventDate: EventDate)
}