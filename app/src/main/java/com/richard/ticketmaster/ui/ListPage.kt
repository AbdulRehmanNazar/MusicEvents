package com.richard.ticketmaster.ui

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberAsyncImagePainter
import com.richard.ticketmaster.R
import com.richard.ticketmaster.data.models.Event
import com.richard.ticketmaster.datastates.DataStates
import com.richard.ticketmaster.utils.getDayFromDate
import com.richard.ticketmaster.utils.isInternetAvailable
import com.richard.ticketmaster.utils.showToast
import com.richard.ticketmaster.viewmodels.EventsViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun ShowEventsList(onClick: (data: Event) -> Unit) {
    val context = LocalContext.current
    val eventsViewModel: EventsViewModel = hiltViewModel()
    val apiState: State<DataStates> = eventsViewModel.dataStates.collectAsState()
    val loadMoreData: State<Boolean> = eventsViewModel.loadMoreDataState.collectAsState()
    var searchText by remember { mutableStateOf("") }



    if (apiState.value.loading) {
        SearchBar(searchText) {
            searchText = it
            if (isInternetAvailable(context)) {
                eventsViewModel.onSearchChange(it)
            } else {
                showToast(
                    context, "There is no internet connection, these are offline results"
                )
            }
        }
        Box(contentAlignment = Alignment.Center, content = {
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(8.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                CircularProgressIndicator(color = Color.Black)
                Spacer(modifier = Modifier.padding(10.dp))
                Text(
                    text = stringResource(id = R.string.please_wait),
                    style = MaterialTheme.typography.titleLarge
                )
            }
        })
    } else {
        if (apiState.value.eventList.isEmpty()) {
            SearchBar(searchText) {
                searchText = it
                if (isInternetAvailable(context)) {
                    eventsViewModel.onSearchChange(it)
                } else {
                    showToast(
                        context, "There is no internet connection, these are offline results"
                    )
                }
            }
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Spacer(modifier = Modifier.padding(40.dp))
                Text(
                    text = stringResource(id = R.string.no_data_found),
                    style = MaterialTheme.typography.titleLarge
                )
            }
        } else {
            Box {
                Column {
                    SearchBar(searchText) {
                        searchText = it
                        if (isInternetAvailable(context)) {
                            eventsViewModel.onSearchChange(it)
                        } else {
                            showToast(
                                context,
                                "There is no internet connection, these are offline results"
                            )
                        }
                    }
                    LazyColumn(
                        modifier = Modifier.padding(8.dp),
                        contentPadding = PaddingValues(bottom = 50.dp, top = 5.dp)
                    ) {
                        items(apiState.value.eventList.size) { index ->
                            ContributerItem(apiState.value.eventList[index], onClick)
                        }
                        item {
                            LaunchedEffect(true) {
                                if (loadMoreData.value) {
                                    eventsViewModel.pageNumber.value++
                                    eventsViewModel.searchEvents()
                                }
                            }
                        }
                    }
                }
                if (apiState.value.paginationLoading) {
                    Column(
                        modifier = Modifier.fillMaxSize(1f),
                        verticalArrangement = Arrangement.Bottom
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp),
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically

                        ) {
                            CircularProgressIndicator(color = Color.Black)
                        }
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalComposeUiApi::class, ExperimentalMaterial3Api::class)
@Composable
fun SearchBar(searchText: String, onSearch: (String) -> Unit) {
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current

    TextField(
        value = searchText,
        onValueChange = {
            onSearch(it)
        },
        label = { Text("Search") },
        leadingIcon = { Icon(Icons.Filled.Search, contentDescription = null) },
        modifier = Modifier.fillMaxWidth(),
        trailingIcon = {
            Icon(Icons.Default.Clear,
                contentDescription = "clear text",
                modifier = Modifier.clickable {
                    onSearch("")
                })
        },
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
        keyboardActions = KeyboardActions(onSearch = {
            keyboardController?.hide()
            focusManager.clearFocus()
        })
    )
}

@Composable
fun ContributerItem(event: Event, onClick: (data: Event) -> Unit) {
    Card(elevation = CardDefaults.cardElevation(
        defaultElevation = 10.dp
    ), modifier = Modifier
        .padding(8.dp)
        .fillMaxWidth(1f)
        .clickable {
            onClick(event)
        }) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Image(
                painter = rememberAsyncImagePainter(event.images[0].url),
                contentDescription = "",
                modifier = Modifier
                    .size(120.dp)
                    .clip(RoundedCornerShape(10.dp)),
                contentScale = ContentScale.Crop,
            )
            Spacer(modifier = Modifier.padding(5.dp))
            Column {
                Text(text = event.name, fontWeight = FontWeight.Bold)
                Text(text = getDayFromDate(event.eventDate?.start?.dateTime.toString()))
                if (event.embedded != null) {
                    Text(text = event.embedded?.venues?.get(0)?.address?.line1.toString())
                    Text(
                        text = event.embedded?.venues?.get(0)?.city?.name.toString() + ", " + event.embedded?.venues?.get(
                            0
                        )?.state?.name.toString()
                    )
                }
            }
        }
    }
}