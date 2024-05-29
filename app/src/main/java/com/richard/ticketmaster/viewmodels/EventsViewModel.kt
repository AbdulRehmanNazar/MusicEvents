package com.richard.ticketmaster.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.richard.ticketmaster.data.repository.EventDataRepo
import com.richard.ticketmaster.datastates.DataStates
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class EventsViewModel @Inject constructor(
    private val eventDataRepo: EventDataRepo,
) : ViewModel() {


    val loadMoreDataState: StateFlow<Boolean>
        get() = eventDataRepo.loadMoreDataState

    // Loading state to check is API Loading
    val dataStates: StateFlow<DataStates>
        get() = eventDataRepo.dataStates

    //Page number for pagination
    var pageNumber = MutableStateFlow(0)

    //Previous value of search to prevent multiple calls of the APIs
    private val previousSearchText = MutableStateFlow("No-Value")
    private val _searchText = MutableStateFlow("")
    private val searchText = _searchText.asStateFlow()

    /**
     * Launch the Search event with 1sec bounce while user typing
     */
    fun searchEventScopeLaunch() {
        viewModelScope.launch {
            _searchText.debounce(1000).collect { query ->
                onSearchChange(query)
                pageNumber.value = 0
                if (previousSearchText.value != searchText.value) {
                    searchEvents()
                }
            }
        }
    }

    /**
     * on Search Update
     */
    fun onSearchChange(text: String) {
        _searchText.value = text
    }

    /**
     * Get Local events
     */
    fun getLocalEvents() {
        viewModelScope.launch {
            eventDataRepo.getLocalEvents()
        }
    }

    /**
     * SearchEvents to call remote API
     */
    fun searchEvents() {
        viewModelScope.launch {
            previousSearchText.value = searchText.value
            eventDataRepo.getEventsList(searchText.value, page = pageNumber.value)
        }
    }


}