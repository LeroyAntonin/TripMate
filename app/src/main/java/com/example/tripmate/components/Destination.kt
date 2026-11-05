package com.example.tripmate.components


import androidx.compose.ui.res.painterResource
import com.example.tripmate.R

import androidx.compose.ui.text.style.TextOverflow

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.example.tripmate.model.Destination

@Composable
fun DestinationCard(
    destination: Destination,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
) {
    Card(onClick = onClick, modifier = modifier.fillMaxWidth()) {

        Row {

            AsyncImage(
                model = destination.imageUrl,
                placeholder = painterResource(R.drawable.ic_launcher_background),   
                error = painterResource(R.drawable.ic_launcher_background),
                fallback = painterResource(R.drawable.ic_launcher_background),
                contentDescription = "Illustration pour ${destination.nom}",
                modifier = Modifier.width(120.dp).height(140.dp),
                contentScale = ContentScale.Crop,
                 )
            Column(modifier = Modifier.weight(1f).padding(18.dp),
                 verticalArrangement = Arrangement.spacedBy(22.dp),) {
                Text(
                    text = "${destination.nom}, ${destination.pays}",
                    style = MaterialTheme.typography.titleMedium,
                )
                Text(
                    text = "À partir de ${destination.prix} ${destination.devise} par ${destination.unitePrix}",
                    style = MaterialTheme.typography.bodySmall,
                    maxLines = 1,

                )
                Row(
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    destination.tags.take(3).forEach { tag ->
                        Text(
                            text = tag,
                            color = MaterialTheme.colorScheme.onSecondaryContainer,
                            style = MaterialTheme.typography.labelSmall,
                            maxLines = 1,
                            modifier = Modifier
                                .background(
                                    MaterialTheme.colorScheme.secondaryContainer,
                                    RoundedCornerShape(20.dp)
                                )
                                .padding(horizontal = 8.dp, vertical = 3.dp)
                        )
                    }
                }               }
            }
        }
    }

