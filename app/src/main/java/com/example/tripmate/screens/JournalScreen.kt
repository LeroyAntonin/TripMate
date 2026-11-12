package com.example.tripmate.screens

import com.example.tripmate.components.DestinationSearchBar
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.tripmate.components.JournalCard
import com.example.tripmate.viewmodel.JournalUiState


@Composable
fun JournalScreen(
    uiState: JournalUiState,
    onQueryChange: (String) -> Unit,
    onJournalClick: (Int) -> Unit,
    onAddJournalClick: () -> Unit,
    onRetry: () -> Unit,
) {





    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = onAddJournalClick) {
                Text("+", style = MaterialTheme.typography.headlineSmall)
            }
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


            // Recherche dans le nom de la ville et dans le pays.
            val visibleDestinations = uiState.journaux.filter {
                it.ville.contains(uiState.query, ignoreCase = true) ||
                    it.pays.contains(uiState.query, ignoreCase = true)
            }

            DestinationSearchBar(value = uiState.query, onValueChange = onQueryChange)



            when {
                uiState.isLoading -> CircularProgressIndicator()
                uiState.error != null -> {
                    Text(uiState.error, color = MaterialTheme.colorScheme.error)
                    Button(onClick = onRetry) {
                        Text("Réessayer")
                    }
                }
                visibleDestinations.isEmpty() -> Text("Aucun journal trouvé.")
                else -> {
                    LazyColumn(
                        modifier = Modifier.weight(1f).fillMaxWidth(),
                    ) {
                        items(visibleDestinations, key = { it.id }) { journal ->
                            JournalCard(
                                journal = journal,
                                modifier = Modifier.padding(vertical = 6.dp),
                                onClick = { onJournalClick(journal.id) },
                            )
                        }
                    }
                }
            }
        }
    }
}