package com.example.tripmate.screens

import com.example.tripmate.viewmodel.DestinationsUiState


import com.example.tripmate.components.DestinationSearchBar
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.tripmate.components.DestinationCard



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    uiState: DestinationsUiState, 
    onQueryChange: (String) -> Unit,
    onTagClick: (String) -> Unit,
    onDestinationClick: (Int) -> Unit,
    onRetry: () -> Unit,
) {
    
    



Column(
        modifier =
            Modifier
                .fillMaxSize()
                .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
    

        val visibleDestinations = uiState.destinations.filter { destination ->
            (destination.nom.contains(uiState.query, ignoreCase = true) ||
                destination.pays.contains(uiState.query, ignoreCase = true)) &&
                (uiState.filterTags.isEmpty() ||
                    destination.tags.any { it in uiState.filterTags })
        }.sortedByDescending { destination ->
            destination.tags.count { it in uiState.preferredTags }
        }
        val tags = uiState.destinations.flatMap { it.tags }.distinct()
        
        DestinationSearchBar(value = uiState.query, onValueChange = onQueryChange)    

        if (tags.isNotEmpty()) {
            val rowSize = (tags.size + 1) / 2
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                tags.chunked(rowSize).forEach { rowTags ->
                    LazyRow(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                    ) {
                        items(rowTags) { tag ->
                            FilterChip(
                                selected = tag in uiState.filterTags,
                                onClick = { onTagClick(tag) },
                                label = { Text(tag) },
                            )
                        }
                    }
                }
            }
        }

  
        when {
            uiState.isLoading -> CircularProgressIndicator()
            uiState.error != null -> {
                Text(uiState.error, color = MaterialTheme.colorScheme.error)
                Button(onClick = onRetry) {
                    Text("Réessayer")
                }
            }
            visibleDestinations.isEmpty() -> Text("Aucune destination trouvée.")
            else -> {
                LazyColumn(
                    modifier = Modifier.weight(1f).fillMaxWidth(),
                ) {
                    items(visibleDestinations, key = { it.id }) { destination ->
                        DestinationCard(
                            destination = destination,
                            modifier = Modifier.padding(vertical = 6.dp),
                            onClick = { onDestinationClick(destination.id) },
                        )
                    }
                }
            }
        }
    }
}
