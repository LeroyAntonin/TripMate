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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import kotlinx.coroutines.launch
import com.example.tripmate.api.DatabaseApi


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TripDetailsScreen(destination: Destination?)  {
    


    

if (destination == null) {
    Text("Destination introuvable", modifier = Modifier.padding(16.dp))
} else {
    
    val currency = if (destination.devise == "EUR") "€" else destination.devise
    val priceText = "${destination.prix} $currency / ${destination.unitePrix.trim()}"
    val datesText = "${formatTripDate(destination.dateArrivee)} – ${formatTripDate(destination.dateDepart)}"
    val coroutineScope = rememberCoroutineScope()
    var commentaire by remember(destination.id) { mutableStateOf("") }
    var commentaires by remember(destination.id) { mutableStateOf(destination.commentaires) }
    var isSaving by remember(destination.id) { mutableStateOf(false) }
    var error by remember(destination.id) { mutableStateOf<String?>(null) }
    
    Column(
    modifier = Modifier.fillMaxSize()
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

        Text(
            text = "Commentaires",
            style = MaterialTheme.typography.titleMedium,
        )

        OutlinedTextField(
            value = commentaire,
            onValueChange = {
                commentaire = it
                error = null
            },
            label = { Text("Ajouter un commentaire") },
            modifier = Modifier.fillMaxWidth(),
            enabled = !isSaving,
            minLines = 2,
        )

        Button(
            onClick = {
                val nouveauCommentaire = commentaire.trim()
                if (nouveauCommentaire.isBlank()) {
                    error = "Le commentaire ne peut pas être vide."
                } else {
                    coroutineScope.launch {
                        isSaving = true
                        error = null
                        try {
                            val destinationMiseAJour = DatabaseApi.addDestinationComment(
                                destinationId = destination.id,
                                commentaire = nouveauCommentaire,
                                commentairesExistants = commentaires,
                            )
                            commentaires = destinationMiseAJour.commentaires
                            commentaire = ""
                        } catch (exception: Exception) {
                            error = "Impossible d'ajouter le commentaire : ${exception.message}"
                        } finally {
                            isSaving = false
                        }
                    }
                }
            },
            enabled = !isSaving,
            modifier = Modifier.fillMaxWidth(),
        ) {
            Text(if (isSaving) "Enregistrement..." else "Ajouter")
        }

        if (error != null) {
            Text(
                text = error!!,
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodySmall,
            )
        }

        if (commentaires.isEmpty()) {
            Text(
                text = "Aucun commentaire pour cette destination.",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
        } else {
            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                commentaires.forEach { commentaire ->
                    Card(
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.surfaceVariant,
                        ),
                        modifier = Modifier.fillMaxWidth(),
                    ) {
                        Text(
                            text = commentaire,
                            style = MaterialTheme.typography.bodyMedium,
                            modifier = Modifier.padding(16.dp),
                        )
                    }
                }
            }
        }
    }

    }
}
    
}


private fun formatTripDate(value: String): String {
    return value.split("-").reversed().joinToString("/")
}