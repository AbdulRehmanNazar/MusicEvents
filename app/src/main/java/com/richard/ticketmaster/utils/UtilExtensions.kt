package com.richard.ticketmaster.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.widget.Toast
import androidx.compose.runtime.Composable
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Locale

/**
 * Utility method to get Day formated from Date String
 */
fun getDayFromDate(dateTimeString: String): String {
    var resultDate = ""
    try {
        val dateFormat = SimpleDateFormat("MMM dd", Locale.ENGLISH)
        val dateTime = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'").parse(dateTimeString)
        resultDate = dateFormat.format(dateTime)
    } catch (e: ParseException) {
        e.printStackTrace()
    }
    return resultDate
}

/**
 * Utility method to show toast
 */
fun showToast(context: Context, msg: String) {
    Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
}

/**
 * Utility method to check internet avalible or not
 * @param context
 */
fun isInternetAvailable(context: Context): Boolean {
    val connectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val network = connectivityManager.activeNetwork ?: return false
    val networkCapabilities = connectivityManager.getNetworkCapabilities(network) ?: return false
    return networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
}