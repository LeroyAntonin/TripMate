package com.example.tripmate.viewmodel

import com.example.tripmate.model.Destination
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tripmate.api.DatabaseApi
import kotlinx.coroutines.launch
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
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }
            try {
                val destinations = DatabaseApi.getDestinations()
                _uiState.update {
                    it.copy(destinations = destinations, isLoading = false)
                }
            } catch (exception: Exception) {
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        error = "Impossible de charger les destinations : ${exception.message}",
                    )
                }
            }
        }
    }
}