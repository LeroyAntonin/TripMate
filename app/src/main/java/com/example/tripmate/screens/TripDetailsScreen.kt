package com.example.tripmate.screens



import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TripDetailsScreen(onBack: () -> Unit) {
    
    Scaffold(
    topBar = {
        TopAppBar(
            title = { Text("Détails") },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = MaterialTheme.colorScheme.primary,
                titleContentColor = MaterialTheme.colorScheme.onPrimary
            ),
            navigationIcon = {
                TextButton(onClick = onBack) { Text("Retour", color = MaterialTheme.colorScheme.onPrimary) }
            }
        )
    }
) { innerPadding ->
    Text("Une Destination apparaîtra ici", modifier = Modifier.padding(innerPadding))
}
}