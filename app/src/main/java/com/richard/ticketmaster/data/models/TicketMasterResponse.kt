package com.richard.ticketmaster.data.models

data class TicketMasterResponse(var _embedded: Embedded? = null, var page: Page? = null)

data class Embedded(var events: List<Event> = ArrayList())

data class Page(
    var size: Int = 0, var totalElements: Int = 0, var totalPages: Int = 0, var number: Int = 0
)