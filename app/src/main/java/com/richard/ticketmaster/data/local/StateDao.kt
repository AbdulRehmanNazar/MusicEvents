package com.richard.ticketmaster.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.richard.ticketmaster.data.models.State
import kotlinx.coroutines.flow.Flow

@Dao
interface StateDao {
    @Query("SELECT * FROM State")
    fun getAllState(): Flow<List<State>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllState(startDates: List<State>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertState(startDate: State)
}