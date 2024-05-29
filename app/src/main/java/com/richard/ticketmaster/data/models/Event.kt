package com.richard.ticketmaster.data.models

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
data class Event(
    @PrimaryKey var id: String = "",
    var name: String = "",
    var type: String = "",
    var url: String = "",
    var locale: String = "",
    var test: Boolean = false,
    var images: List<Image> = emptyList(),
    @Embedded
    @SerializedName("_embedded") var embedded: EmbeddedVennu? = null,
    @Embedded
    @SerializedName("dates") var eventDate: EventDate? = null
)

@Entity
data class Image(@PrimaryKey var url: String = "")

@Entity
data class EventDate(
    @Embedded
    @SerializedName("start") var start: StartDate? = null
) {
    @PrimaryKey(autoGenerate = true)
    var eventId: Int = 0
}

@Entity
data class StartDate(@PrimaryKey var dateTime: String = "", var localDate: String = "")

@Entity
data class EmbeddedVennu(
    var venues: List<Venue> = emptyList()
) {
    @PrimaryKey(autoGenerate = true)
    var embeddedVenueID: Int = 0
}

@Entity
data class Venue(
    @Embedded var city: City? = null,
    @Embedded var state: State? = null,
    @Embedded var address: Address? = null
) {
    @PrimaryKey(autoGenerate = true)
    var venueId: Int = 0
}

@Entity
data class City(
    @PrimaryKey
    @SerializedName("name")
    @ColumnInfo("cityName")
    var name: String = ""
)

@Entity
data class State(@PrimaryKey var name: String = "")

@Entity
data class Address(@PrimaryKey var line1: String = "")