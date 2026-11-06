package com.example.tripmate.navigation



import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.Modifier
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.tripmate.components.TripAppScaffold




import com.example.tripmate.screens.JournalScreen
import com.example.tripmate.screens.ProfileScreen
import com.example.tripmate.screens.PreferencesScreen


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
    
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = backStackEntry?.destination?.route ?: "home"
    val showBack = currentRoute.startsWith("details/")
    val selectedRoute = if (showBack) "home" else currentRoute
    
    
    
    
    TripAppScaffold(
    selectedRoute = selectedRoute,
    showBack = showBack,
    onBack = { navController.popBackStack() },
    onNavigate = { route ->
        navController.navigate(route) {
            popUpTo("home") { inclusive = false }
            launchSingleTop = true
        }
    },
) { innerPadding ->
    


        NavHost(
                navController = navController,
                startDestination = "home",
                modifier = Modifier
                    .padding(innerPadding)
                    .consumeWindowInsets(innerPadding),
            )
        
            {
                composable("home") {
            
                    
                    HomeScreen(
                        uiState = uiState,
                        onQueryChange = vm::updateQuery,
                        onDestinationClick = { destinationId ->
                            navController.navigate("details/$destinationId")
                        }
                    )
                }
                
                composable(route = "details/{destinationId}", arguments = listOf(navArgument("destinationId") { type = NavType.IntType })
                    ) {
                        entry ->
                        val destinationId = entry.arguments?.getInt("destinationId")
                        val destination = uiState.destinations.firstOrNull {
                        it.id == destinationId
                    }
                            TripDetailsScreen(
                        destination = destination,
                        
                    )
                }


                composable("journal") {
                    JournalScreen()
                }
                
                composable("profile") {
                    ProfileScreen()
                }
                
                composable("settings") {
                    PreferencesScreen()
                }
                
       }

}}