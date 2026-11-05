package com.example.tripmate.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp



@Composable
fun TripDetailRow(iconRes: Int, label: String, value: String) {
    
    Row(
    modifier = Modifier.fillMaxWidth(),
    horizontalArrangement = Arrangement.spacedBy(8.dp),
    verticalAlignment = Alignment.Top,
) {
    Icon(
    painter = painterResource(iconRes),
    contentDescription = null,
    modifier = Modifier.size(20.dp),
)
Text(
    text = label,
    modifier = Modifier.width(64.dp),
    style = MaterialTheme.typography.bodyMedium,
    fontWeight = FontWeight.Bold,
)
Text(
    text = value,
    modifier = Modifier.weight(1f),
    style = MaterialTheme.typography.bodyMedium,
)
}
}