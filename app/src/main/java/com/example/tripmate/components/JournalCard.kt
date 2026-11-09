package com.example.tripmate.components


import androidx.compose.ui.res.painterResource
import com.example.tripmate.R

import androidx.compose.ui.text.style.TextOverflow

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.example.tripmate.model.Journal

@Composable
fun JournalCard(
    journal: Journal,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
) {
    Card(onClick = onClick, modifier = modifier.fillMaxWidth()) {
        Column {
            AsyncImage(
                model = journal.imageUrl,
                placeholder = painterResource(R.drawable.ic_launcher_background),
                error = painterResource(R.drawable.ic_launcher_background),
                fallback = painterResource(R.drawable.ic_launcher_background),
                contentDescription = "Illustration pour ${journal.ville}",
                modifier = Modifier.fillMaxWidth().height(180.dp),
                contentScale = ContentScale.Crop,
            )

            Row(
                modifier = Modifier.fillMaxWidth().padding(16.dp),
            ) {
                Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = journal.ville,
                    style = MaterialTheme.typography.titleMedium,
                )
                Text(
                    text = journal.pays,
                    style = MaterialTheme.typography.bodyMedium,
                )
                }
                Text(
                text = "Du ${journal.dateArrivee}\nau ${journal.dateDepart}",
                style = MaterialTheme.typography.bodySmall,
                textAlign = TextAlign.End,
                )
            }

            Text(
                text = journal.avis,
                modifier = Modifier.padding(start = 16.dp, end = 16.dp, bottom = 16.dp),
                style = MaterialTheme.typography.bodyMedium,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
            )
        }
    }
}
