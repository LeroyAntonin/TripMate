package com.example.tripmate.navigation



import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.tripmate.screens.TripDetailsScreen
import com.example.tripmate.screens.HomeScreen


@Composable
fun DestinationsNavigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "home") {
        composable("home") {
    HomeScreen(onDestinationClick = { navController.navigate("details") })
}
composable("details") {
    TripDetailsScreen(onBack = { navController.popBackStack() })
}
}
}