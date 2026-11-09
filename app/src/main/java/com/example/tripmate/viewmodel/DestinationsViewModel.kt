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
    val preferredTags: Set<String> = emptySet(),
    val filterTags: Set<String> = emptySet(),
    
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
    fun togglePreferredTag(tag: String) {
    _uiState.update { current ->
        val preferredTags = current.preferredTags.toMutableSet()
        if (!preferredTags.add(tag)) {
            preferredTags.remove(tag)
        }
        current.copy(preferredTags = preferredTags)
    }
}
    fun toggleFilterTag(tag: String) {
    _uiState.update { current ->
        val filterTags = current.filterTags.toMutableSet()
        if (!filterTags.add(tag)) {
            filterTags.remove(tag)
        }
        current.copy(filterTags = filterTags)
    }
}
    fun refresh() {
    _uiState.update { current -> current.copy(destinations = DestinationsAPI.getDestinations()) }
    }
}