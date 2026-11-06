package com.example.tripmate.screens


import com.example.tripmate.components.TripDetailRow

import com.example.tripmate.model.Destination

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.example.tripmate.R








import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api

import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TripDetailsScreen(destination: Destination?)  {
    


    

if (destination == null) {
    Text("Destination introuvable", modifier = Modifier.padding(16.dp))
} else {
    
    val currency = if (destination.devise == "EUR") "€" else destination.devise
    val priceText = "${destination.prix} $currency / ${destination.unitePrix.trim()}"
    val datesText = "${formatTripDate(destination.dateArrivee)} – ${formatTripDate(destination.dateDepart)}"
    
    Column(
    modifier = Modifier.fillMaxSize().height(200.dp)
        .verticalScroll(rememberScrollState()),
    verticalArrangement = Arrangement.spacedBy(12.dp)
) {

    AsyncImage(
    model = destination.imageUrl,
    contentDescription = "Illustration de ${destination.nom}",
    contentScale = ContentScale.Crop,
    modifier = Modifier.fillMaxWidth().height(280.dp),
    placeholder = painterResource(R.drawable.ic_launcher_background),
    error = painterResource(R.drawable.ic_launcher_background),
    fallback = painterResource(R.drawable.ic_launcher_background)
)
    Column(
        modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 4.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        Text(
            text = "${destination.hotel}, ${destination.nom}",
            style = MaterialTheme.typography.titleLarge,
        )
        TripDetailRow(
            iconRes = R.drawable.ic_trip_location,
            label = "Adresse",
            value = destination.adresse,
        )
        
        TripDetailRow(
        iconRes = R.drawable.ic_trip_price,
        label = "Prix",
        value = priceText,
    )
    
        TripDetailRow(
        iconRes = R.drawable.ic_trip_calendar,
        label = "Dates",
        value = datesText,
    )
        
        
        
        
        
            }
    
    }
}
    
}


private fun formatTripDate(value: String): String {
    return value.split("-").reversed().joinToString("/")
}