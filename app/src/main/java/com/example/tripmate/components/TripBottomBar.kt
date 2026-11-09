package com.example.tripmate.components

import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import com.example.tripmate.R


@Composable
fun TripBottomBar(
    selectedRoute: String,
    onNavigate: (String) -> Unit,
) {
    
    val itemColors = NavigationBarItemDefaults.colors(
    selectedIconColor = MaterialTheme.colorScheme.onPrimary,
    selectedTextColor = MaterialTheme.colorScheme.onPrimary,
    unselectedIconColor = MaterialTheme.colorScheme.onPrimary,
    unselectedTextColor = MaterialTheme.colorScheme.onPrimary,
    indicatorColor = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.12f),
)
    NavigationBar(
    containerColor = MaterialTheme.colorScheme.primary,
    contentColor = MaterialTheme.colorScheme.onPrimary,
) {
    
    NavigationBarItem(
    selected = selectedRoute == "home",
    onClick = { onNavigate("home") },
    icon = { Icon(painterResource(R.drawable.ic_nav_home), contentDescription = null) },
    label = { Text("Accueil") },
    colors = itemColors,
)

   NavigationBarItem(
    selected = selectedRoute == "journal",
    onClick = { onNavigate("journal") },
    icon = { Icon(painterResource(R.drawable.ic_nav_journal), contentDescription = null) },
    label = { Text("Journal") },
    colors = itemColors,
)
    NavigationBarItem(
    selected = selectedRoute == "profile",
    onClick = { onNavigate("profile") },
    icon = { Icon(painterResource(R.drawable.ic_nav_profile), contentDescription = null) },
    label = { Text("Profil") },
    colors = itemColors,
)
    
}




}