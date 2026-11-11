package com.example.tripmate.navigation



import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.Modifier
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.tripmate.components.TripAppScaffold




import com.example.tripmate.screens.JournalScreen
import com.example.tripmate.screens.ProfileScreen


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
import com.example.tripmate.screens.JournalDetailsScreen
import com.example.tripmate.screens.AddJournalScreen
import com.example.tripmate.screens.HomeScreen
import com.example.tripmate.viewmodel.JournalViewModel


@Composable
fun DestinationsNavigation() {
    val navController = rememberNavController()
    
    val vmd: DestinationsViewModel = viewModel()
    val uiStateD by vmd.uiState.collectAsStateWithLifecycle()
    val vmj: JournalViewModel = viewModel()
    val uiStateJ by vmj.uiState.collectAsStateWithLifecycle()
    
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = backStackEntry?.destination?.route ?: "home"
    val showBack = currentRoute.startsWith("details/") ||
            currentRoute.startsWith("journalDetails/") ||
            currentRoute == "addJournal"
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
                        uiState = uiStateD,
                        onQueryChange = vmd::updateQuery,
                        onTagClick = vmd::toggleFilterTag,
                        onDestinationClick = { destinationId ->
                            navController.navigate("details/$destinationId")
                        },
                        onRetry = vmd::refresh,
                    )
                }
                
                composable(route = "details/{destinationId}", arguments = listOf(navArgument("destinationId") { type = NavType.IntType })
                    ) {
                        entry ->
                        val destinationId = entry.arguments?.getInt("destinationId")
                        val destination = uiStateD.destinations.firstOrNull {
                        it.id == destinationId
                    }
                            TripDetailsScreen(
                        destination = destination,
                        
                    )
                }


                composable("journal") {
                    JournalScreen(uiState = uiStateJ,
                        onQueryChange = vmj::updateQuery,
                        onJournalClick = { journalId ->
                            navController.navigate("journalDetails/$journalId")
                        },
                        onAddJournalClick = {
                            navController.navigate("addJournal")
                        },
                        onRetry = vmj::refresh,
                    )
                }

                composable("addJournal") {
                    AddJournalScreen(
                        onJournalAdded = {
                            vmj.refresh()
                            navController.popBackStack()
                        }
                    )
                }

                composable(route = "journalDetails/{journalId}", arguments = listOf(navArgument("journalId") { type = NavType.IntType })
                ) {
                        entry ->
                    val journalId = entry.arguments?.getInt("journalId")
                    val journal = uiStateJ.journaux.firstOrNull {
                        it.id == journalId
                    }
                    JournalDetailsScreen(
                        journal = journal,

                        )
                }
                
                composable("profile") {
                    ProfileScreen(
                        tags = uiStateD.destinations.flatMap { it.tags }.distinct(),
                        selectedTags = uiStateD.preferredTags,
                        onTagClick = vmd::togglePreferredTag,
                    )
                }

                
       }

}}