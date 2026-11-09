package com.example.tripmate.viewmodel

import com.example.tripmate.model.Destination
import androidx.lifecycle.ViewModel
import com.example.tripmate.api.DestinationsAPI
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

data class DestinationsUiState(
    val destinations: List<Destination> = emptyList(),
    val query: String = "",
    val selectedTag: String? = null,
    
    val isLoading: Boolean = false,
    val error: String? = null,
    val compactMode: Boolean = false
)


class DestinationsViewModel : ViewModel() {
    
    private val _uiState = MutableStateFlow(DestinationsUiState())
    
    val uiState: StateFlow<DestinationsUiState> = _uiState.asStateFlow()
    
    init {
    refresh()
    }
    
    fun updateQuery(query: String) {
    _uiState.update { current -> current.copy(query = query) }
}
    fun updateTag(tag: String?) {
    _uiState.update { current -> current.copy(selectedTag = tag) }
}
    fun refresh() {
    _uiState.update { current -> current.copy(destinations = DestinationsAPI.getDestinations()) }
    }
}