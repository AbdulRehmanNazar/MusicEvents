package com.richard.ticketmaster.data.local

import android.content.Context
import androidx.room.*
import com.richard.ticketmaster.data.models.Address
import com.richard.ticketmaster.data.models.City
import com.richard.ticketmaster.data.models.EmbeddedVennu
import com.richard.ticketmaster.data.models.Event
import com.richard.ticketmaster.data.models.EventDate
import com.richard.ticketmaster.data.models.Image
import com.richard.ticketmaster.data.models.StartDate
import com.richard.ticketmaster.data.models.State
import com.richard.ticketmaster.data.models.Venue


@Database(
    entities = [Event::class, Image::class, EventDate::class, StartDate::class, EmbeddedVennu::class, Venue::class, City::class, State::class, Address::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(value = [ImageConverters::class, VennuConverters::class, EventDateConverters::class])
abstract class AppDataBase : RoomDatabase() {
    abstract fun eventDao(): EventDao
    abstract fun imageDao(): ImageDao
    abstract fun embededVenueDao(): EmbeddedVennuDao
    abstract fun venueDao(): VennuDao
    abstract fun cityDao(): CityDao
    abstract fun stateDao(): StateDao
    abstract fun addressDao(): AddressDao
    abstract fun eventDateDao(): EventDateDao

    companion object {
        @Volatile
        private var instance: AppDataBase? = null

        fun getDatabase(context: Context): AppDataBase =
            instance ?: synchronized(this) {
                instance ?: buildDatabase(context).also {
                    instance = it
                }
            }

        private fun buildDatabase(appContext: Context) =
            Room.databaseBuilder(appContext, AppDataBase::class.java, "eventsDB")
                .fallbackToDestructiveMigration()
                .build()
    }
}