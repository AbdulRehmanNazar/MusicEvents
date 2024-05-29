package com.richard.ticketmaster.datastates

import com.richard.ticketmaster.data.models.Event

data class DataStates(
    var loading: Boolean = true,
    var paginationLoading: Boolean = false,
    var eventList: List<Event> = emptyList()
)