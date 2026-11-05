package com.example.tripmate.navigation

import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.tripmate.viewmodel.DestinationsViewModel

import androidx.navigation.NavType
import androidx.navigation.navArgument
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.tripmate.screens.TripDetailsScreen
import com.example.tripmate.screens.HomeScreen


@Composable
fun DestinationsNavigation() {
    val navController = rememberNavController()
    
    val vm: DestinationsViewModel = viewModel()
    val uiState by vm.uiState.collectAsStateWithLifecycle()
    
    
    NavHost(navController = navController, startDestination = "home") {
        composable("home") {
    
HomeScreen(
    uiState = uiState,
    onQueryChange = vm::updateQuery,
    onDestinationClick = { destinationId ->
        navController.navigate("details/$destinationId")
    }
)
        }
composable(
    route = "details/{destinationId}",
    arguments = listOf(navArgument("destinationId") { type = NavType.IntType })
) {
    entry ->
    val destinationId = entry.arguments?.getInt("destinationId")
    val destination = uiState.destinations.firstOrNull {
    it.id == destinationId
}
    TripDetailsScreen(
    destination = destination,
    onBack = { navController.popBackStack() }
)
}

}}