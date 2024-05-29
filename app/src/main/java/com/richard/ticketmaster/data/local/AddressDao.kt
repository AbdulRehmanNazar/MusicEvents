package com.richard.ticketmaster.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.richard.ticketmaster.data.models.Address
import kotlinx.coroutines.flow.Flow

@Dao
interface AddressDao {
    @Query("SELECT * FROM Address")
    fun getAllAddress(): Flow<List<Address>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllAddress(addresses: List<Address>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAddress(address: Address)
}