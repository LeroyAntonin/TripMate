package com.example.tripmate.screens


import com.example.tripmate.model.Destination

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.foundation.layout.size
import androidx.compose.ui.unit.dp
import androidx.compose.ui.text.style.TextAlign
import com.example.tripmate.api.DatabaseApi
import com.example.tripmate.model.Journal
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun JournalDetailsScreen(
    journal: Journal?,
    onJournalDeleted: () -> Unit,
    onJournalEdit: (Int) -> Unit,
)  {

    val coroutineScope = rememberCoroutineScope()
    var showDeleteDialog by remember { mutableStateOf(false) }
    var isDeleting by remember { mutableStateOf(false) }
    var error by remember { mutableStateOf<String?>(null) }

    if (journal == null) {
        Text("Journal introuvable", modifier = Modifier.padding(16.dp))
    } else {

        val datesText = "${formatTripDate(journal.dateArrivee)} – ${formatTripDate(journal.dateDepart)}"

        Column(
            modifier = Modifier.fillMaxSize()
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {

            AsyncImage(
                model = journal.imageUrl,
                contentDescription = "Illustration de ${journal.ville}",
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
                    text = "${journal.ville}, ${journal.pays}",
                    style = MaterialTheme.typography.titleLarge,
                )

                Text(
                    text = datesText,
                    style = MaterialTheme.typography.bodyMedium,
                )

                Text(
                    text = journal.avis,
                    style = MaterialTheme.typography.headlineSmall,
                    textAlign = TextAlign.Justify
                )

                if (error != null) {
                    Text(
                        text = error!!,
                        color = MaterialTheme.colorScheme.error,
                        style = MaterialTheme.typography.bodySmall,
                    )
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                ) {
                    IconButton(
                        onClick = { showDeleteDialog = true },
                        enabled = !isDeleting,
                        modifier = Modifier.size(64.dp),
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.ic_delete),
                            contentDescription = "Supprimer le journal",
                            tint = MaterialTheme.colorScheme.error,
                            modifier = Modifier.size(36.dp),
                        )
                    }
                    IconButton(
                        onClick = { onJournalEdit(journal.id) },
                        enabled = !isDeleting,
                        modifier = Modifier.size(64.dp),
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.ic_edit),
                            contentDescription = "Modifier le journal",
                            tint = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.size(36.dp),
                        )
                    }
                }
            }

        }

        if (showDeleteDialog) {
            AlertDialog(
                onDismissRequest = {
                    if (!isDeleting) showDeleteDialog = false
                },
                title = { Text("Supprimer le journal ?") },
                text = { Text("Cette action supprimera définitivement ce journal de la base de données.") },
                confirmButton = {
                    TextButton(
                        onClick = {
                            coroutineScope.launch {
                                isDeleting = true
                                error = null
                                try {
                                    DatabaseApi.deleteJournal(journal.id)
                                    showDeleteDialog = false
                                    onJournalDeleted()
                                } catch (exception: Exception) {
                                    error = "Impossible de supprimer le journal : ${exception.message}"
                                    showDeleteDialog = false
                                } finally {
                                    isDeleting = false
                                }
                            }
                        },
                        enabled = !isDeleting,
                    ) {
                        Text("Supprimer", color = MaterialTheme.colorScheme.error)
                    }
                },
                dismissButton = {
                    TextButton(
                        onClick = { showDeleteDialog = false },
                        enabled = !isDeleting,
                    ) {
                        Text("Annuler")
                    }
                },
            )
        }
    }

}


private fun formatTripDate(value: String): String {
    return value.split("-").reversed().joinToString("/")
}