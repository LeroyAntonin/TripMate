package com.example.tripmate.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.example.tripmate.R
import androidx.compose.ui.res.painterResource

@Composable
fun AddJournalScreen() {
    var ville by rememberSaveable { mutableStateOf("") }
    var pays by rememberSaveable { mutableStateOf("") }
    var dateArrivee by rememberSaveable { mutableStateOf("") }
    var dateDepart by rememberSaveable { mutableStateOf("") }
    var imageUrl by rememberSaveable { mutableStateOf("") }
    var avis by rememberSaveable { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
            if (imageUrl.isNotBlank()) {
                AsyncImage(
                    model = imageUrl,
                    contentDescription = "Aperçu de l'image du journal",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(220.dp),
                    placeholder = painterResource(R.drawable.ic_launcher_background),
                    error = painterResource(R.drawable.ic_launcher_background),
                    fallback = painterResource(R.drawable.ic_launcher_background),
                )
            }

            JournalField(
                value = ville,
                onValueChange = { ville = it },
            label = "Ville",
            )
        JournalField(
            value = pays,
            onValueChange = { pays = it },
            label = "Pays",
        )
        JournalField(
            value = dateArrivee,
            onValueChange = { dateArrivee = it },
            label = "Date d'arrivée",
        )
        JournalField(
            value = dateDepart,
            onValueChange = { dateDepart = it },
            label = "Date de départ",
        )
        JournalField(
            value = imageUrl,
            onValueChange = { imageUrl = it },
            label = "URL de l'image",
        )
        JournalField(
            value = avis,
            onValueChange = { avis = it },
            label = "Avis",
            singleLine = false,
        )
    }
}

@Composable
private fun JournalField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    singleLine: Boolean = true,
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label) },
        modifier = Modifier.fillMaxWidth(),
        singleLine = singleLine,
    )
}
