package com.example.tripmate.screens

import com.example.tripmate.viewmodel.DestinationsUiState

import com.example.tripmate.api.DestinationsAPI

import com.example.tripmate.components.DestinationSearchBar
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf

import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.tripmate.R
import com.example.tripmate.components.DestinationCard



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    uiState: DestinationsUiState, 
    onQueryChange: (String) -> Unit,
    onDestinationClick: (Int) -> Unit) {
    
    
    Scaffold(

modifier = Modifier.fillMaxSize(),
topBar = {
    TopAppBar(
        title = { Text("TripMate") },
        colors =
            TopAppBarDefaults.topAppBarColors(
                containerColor = MaterialTheme.colorScheme.primary,
                titleContentColor = MaterialTheme.colorScheme.onPrimary,
            ),
    )
},
) { innerPadding ->
    Column(
        modifier =
            Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        var query by rememberSaveable { mutableStateOf("") }

        val visibleDestinations = uiState.destinations.filter {
             it.nom.contains(uiState.query, ignoreCase = true)
}
        
        DestinationSearchBar(value = uiState.query, onValueChange = onQueryChange)    
            
/*         OutlinedTextField(
            colors =
                OutlinedTextFieldDefaults.colors(
                    cursorColor = Color.Black,
                    focusedBorderColor = Color.Black,
                    focusedLabelColor = Color.Black,
                    focusedLeadingIconColor = Color.Black,
                ),
            value = query,
            onValueChange = { newValue -> query = newValue },
            label = { Text("Rechercher un destination") },
            leadingIcon = {
                Icon(
                    painter = painterResource(R.drawable.baseline_search_24),
                    contentDescription = "Recherche",
                )
            },

            singleLine = true,
            modifier = Modifier.fillMaxWidth(),
        )
*/
  
      LazyColumn(
            modifier = Modifier.weight(1f).fillMaxWidth(),
        ) {
            items(visibleDestinations) { destination ->
                DestinationCard(
                    destination = destination,
                    modifier = Modifier.padding(vertical = 6.dp),
                    onClick = { onDestinationClick(destination.id) }
                )
            }
        }
    }
}

}





