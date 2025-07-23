@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.rajbaricity

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.core.net.toUri
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.*
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject
import java.io.IOException

@Composable
fun NearbyMosqueScreen() {
    val context = LocalContext.current
    val fusedClient = remember { LocationServices.getFusedLocationProviderClient(context) }

    // Rajbari fallback location if GPS/permission unavailable
    val fallbackLocation = LatLng(23.7574, 89.6440)

    var userLocation by remember { mutableStateOf(fallbackLocation) }
    var hasPermission by remember { mutableStateOf(false) }
    var isGPSEnabled by remember { mutableStateOf(isLocationEnabled(context)) }
    var mosqueList by remember { mutableStateOf<List<Pair<LatLng, String>>>(emptyList()) }

    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { granted ->
        hasPermission = granted
        if (granted && isGPSEnabled) {
            fetchLocationAndMosques(fusedClient) { location, mosques ->
                userLocation = location
                mosqueList = mosques
            }
        }
    }

    // Initial permission and location check
    LaunchedEffect(Unit) {
        hasPermission = ContextCompat.checkSelfPermission(
            context, Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED

        isGPSEnabled = isLocationEnabled(context)

        if (hasPermission && isGPSEnabled) {
            fetchLocationAndMosques(fusedClient) { location, mosques ->
                userLocation = location
                mosqueList = mosques
            }
        } else {
            permissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
        }
    }

    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(userLocation, 14f)
    }

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("🕌 কাছাকাছি মসজিদ (Free)") })
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
        ) {
            // Google Map with user + mosque markers
            GoogleMap(
                modifier = Modifier.weight(1f),
                cameraPositionState = cameraPositionState,
                properties = MapProperties(isMyLocationEnabled = hasPermission && isGPSEnabled)
            ) {
                // User location marker
                Marker(
                    state = MarkerState(position = userLocation),
                    title = "আপনার অবস্থান"
                )

                // Mosque markers
                mosqueList.forEach { (location, name) ->
                    Marker(
                        state = MarkerState(position = location),
                        title = name
                    )
                }
            }

            // Button to open Google Maps with mosque search
            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                onClick = {
                    val uri = "https://www.google.com/maps/search/nearby+mosque/@${userLocation.latitude},${userLocation.longitude},2000m"
                        .toUri()

                    val mapIntent = Intent(Intent.ACTION_VIEW, uri).apply {
                        setPackage("com.google.android.apps.maps")
                    }

                    if (mapIntent.resolveActivity(context.packageManager) != null) {
                        context.startActivity(mapIntent)
                    } else {
                        context.startActivity(Intent(Intent.ACTION_VIEW, uri))
                    }
                }
            ) {
                Text("গুগল ম্যাপে মসজিদ দেখুন")
            }
        }
    }
}

/**
 * Fetch user location and nearby mosques
 */
private fun fetchLocationAndMosques(
    fusedClient: FusedLocationProviderClient,
    onResult: (LatLng, List<Pair<LatLng, String>>) -> Unit
) {
    try {
        fusedClient.lastLocation.addOnSuccessListener { location: Location? ->
            location?.let {
                val latLng = LatLng(it.latitude, it.longitude)
                fetchMosquesFromOverpass(latLng.latitude, latLng.longitude) { mosques ->
                    onResult(latLng, mosques)
                }
            }
        }
    } catch (e: SecurityException) {
        e.printStackTrace()
        onResult(LatLng(23.7574, 89.6440), emptyList()) // fallback
    }
}

/**
 * Fetch nearby mosques using Overpass API
 */
private fun fetchMosquesFromOverpass(
    lat: Double,
    lon: Double,
    callback: (List<Pair<LatLng, String>>) -> Unit
) {
    val query = """
        [out:json];
        node["amenity"="place_of_worship"]["religion"="muslim"]
        (around:2000,$lat,$lon);
        out;
    """.trimIndent()

    val requestBody = "data=$query"
        .toRequestBody("application/x-www-form-urlencoded".toMediaType())

    val client = OkHttpClient()
    val request = Request.Builder()
        .url("https://overpass-api.de/api/interpreter")
        .post(requestBody)
        .build()

    client.newCall(request).enqueue(object : Callback {
        override fun onFailure(call: Call, e: IOException) {
            callback(emptyList())
        }

        override fun onResponse(call: Call, response: Response) {
            response.body?.string()?.let { jsonString ->
                val json = JSONObject(jsonString)
                val elements = json.optJSONArray("elements")
                val result = mutableListOf<Pair<LatLng, String>>()

                for (i in 0 until (elements?.length() ?: 0)) {
                    val obj = elements?.getJSONObject(i)
                    val lat = obj?.optDouble("lat", 0.0) ?: 0.0
                    val lon = obj?.optDouble("lon", 0.0) ?: 0.0
                    val name = obj?.optJSONObject("tags")?.optString("name", "মসজিদ") ?: "মসজিদ"
                    result.add(LatLng(lat, lon) to name)
                }

                callback(result)
            } ?: callback(emptyList())
        }
    })
}

/**
 * Check if GPS or Network location is enabled
 */
private fun isLocationEnabled(context: Context): Boolean {
    val locationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
    return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) ||
            locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
}
