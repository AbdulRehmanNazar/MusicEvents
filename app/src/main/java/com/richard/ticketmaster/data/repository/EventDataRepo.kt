package com.richard.ticketmaster.data.repository

import android.util.Log
import com.richard.ticketmaster.BuildConfig
import com.richard.ticketmaster.data.api.APIService
import com.richard.ticketmaster.data.local.AppDataBase
import com.richard.ticketmaster.data.models.Event
import com.richard.ticketmaster.datastates.DataStates
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.withContext
import org.json.JSONException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import javax.inject.Inject

/**
 * Repository class that holds the Local and Remote data logic
 */
class EventDataRepo @Inject constructor(
    private val apiService: APIService, private val appDataBase: AppDataBase
) {

    private val _loadMoreDataState = MutableStateFlow(true)
     val loadMoreDataState : StateFlow<Boolean>
        get() = _loadMoreDataState

    private val _dataStates = MutableStateFlow(DataStates())
    val dataStates: StateFlow<DataStates>
        get() = _dataStates

    /**
     * Get Local events from Room DB
     */
    suspend fun getLocalEvents() {
        try {
            withContext(Dispatchers.IO) {
                val eventList = appDataBase.eventDao().getAllEvents()
                eventList.collect {
                    _dataStates.value = DataStates(false, false, it)
                    _loadMoreDataState.value = false
                }
            }
        } catch (e: Exception) {
            _dataStates.value = DataStates(false, false, emptyList())
            _loadMoreDataState.value = false
        }
    }

    /**
     * Get Remote events
     */
    suspend fun getEventsList(keywordText: String, page: Int) {
        if (page >= 1) {
            _dataStates.value =
                DataStates(loading = false, paginationLoading = true, _dataStates.value.eventList)
        } else {
            _dataStates.value =
                DataStates(loading = true, paginationLoading = false, _dataStates.value.eventList)
        }

        try {
            val response =
                apiService.getTickerMasterEvents(BuildConfig.app_key, keywordText, page.toString())

            Log.d("Request URL: ", response.raw().request.url.toString())
            if (response.isSuccessful && response.body() != null) {
                if (response.body()?._embedded != null) {
                    if (page == 0) {
                        withContext(Dispatchers.IO) {
                            try {
                                appDataBase.eventDao()
                                    .insertAllEvents(response.body()?._embedded?.events!!)
                            } catch (e: Exception) {
                                e.printStackTrace()
                            }
                        }
                        _loadMoreDataState.value = true
                        _dataStates.value =
                            DataStates(false, false, response.body()?._embedded?.events!!)
                    } else {
                        val pagginatedList: List<Event> =
                            _dataStates.value.eventList.plus(response.body()?._embedded?.events!!)
                        withContext(Dispatchers.IO) {
                            try {
                                appDataBase.eventDao().insertAllEvents(pagginatedList)
                            } catch (e: Exception) {
                                e.printStackTrace()
                            }
                        }
                        _loadMoreDataState.value = true
                        _dataStates.value = DataStates(false, false, pagginatedList)
                    }
                } else {
                    _loadMoreDataState.value = false
                    if (page == 0) {
                        _dataStates.value = DataStates(false, false, emptyList())
                    } else {
                        _dataStates.value = DataStates(false, false, _dataStates.value.eventList)
                    }
                }
            }
        } catch (e: NullPointerException) {
            _dataStates.value = DataStates(false, false, emptyList())
            e.printStackTrace()
        } catch (e: UnknownHostException) {
            getLocalEvents()
            e.printStackTrace()
        } catch (e: JSONException) {
            _dataStates.value = DataStates(false, false, emptyList())
            e.printStackTrace()
        } catch (e: SocketTimeoutException) {
            getLocalEvents()
            e.printStackTrace()
        } catch (e: Exception) {
            _dataStates.value = DataStates(false, false, emptyList())
            e.printStackTrace()
        }
    }
}