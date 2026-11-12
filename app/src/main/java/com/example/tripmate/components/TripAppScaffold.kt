package com.example.tripmate.components

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import com.example.tripmate.R




@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TripAppScaffold(
    selectedRoute: String,
    showBack: Boolean,
    onBack: () -> Unit,
    onNavigate: (String) -> Unit,
    content: @Composable (PaddingValues) -> Unit,
) {
    
    Scaffold(
    modifier = Modifier.fillMaxSize(),
    topBar = {
            TopAppBar(
                title = {
                    if (showBack) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.End,
                        ) {
                            Text(
                                text = "TripMate",
                                modifier = Modifier.clickable { onNavigate("home") },
                            )
                        }
                    } else {
                        Text(
                            text = "TripMate",
                            modifier = Modifier.clickable { onNavigate("home") },
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary,
                ),
                navigationIcon = {
                    if (showBack) {
                        IconButton(onClick = onBack) {
                            Icon(
                                painter = painterResource(R.drawable.ic_arrow_back),
                                contentDescription = "Retour",
                                tint = MaterialTheme.colorScheme.onPrimary,
                            )
                        }
                    }
                },
            )
    },
    bottomBar = {
        
        TripBottomBar(
            selectedRoute = selectedRoute,
            onNavigate = onNavigate,
        )
        
    },
    content = content,
)
    
    
    
    
}