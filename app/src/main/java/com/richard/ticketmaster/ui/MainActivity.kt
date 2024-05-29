package com.richard.ticketmaster.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.richard.ticketmaster.R
import com.richard.ticketmaster.utils.showToast
import com.richard.ticketmaster.viewmodels.EventsViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Scaffold(topBar = {
                TopAppBar(
                    title = {
                        Text(
                            text = stringResource(R.string.events_list), color = Color.White
                        )
                    },
                    colors = TopAppBarDefaults.smallTopAppBarColors(containerColor = Color.Black)
                )
            }) {
                Box(modifier = Modifier.padding(it)) {
                    App()
                }
            }
        }
    }
}

@Composable
fun App() {
    val context = LocalContext.current
    val eventsViewModel: EventsViewModel = hiltViewModel()
    eventsViewModel.searchEventScopeLaunch()
    ShowEventsList {
        showToast(context, it.name)
    }
}