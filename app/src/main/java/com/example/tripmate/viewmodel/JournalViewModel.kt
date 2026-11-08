package com.example.tripmate.viewmodel

import com.example.tripmate.model.Destination
import androidx.lifecycle.ViewModel
import com.example.tripmate.api.DestinationsAPI
import com.example.tripmate.api.JournalApi
import com.example.tripmate.model.Journal
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

data class JournalUiState(
    val journaux: List<Journal> = emptyList(),
    val query: String = "",

    val isLoading: Boolean = false,
    val error: String? = null,
    val compactMode: Boolean = false
)



class JournalViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(JournalUiState())

    val uiState: StateFlow<JournalUiState> = _uiState.asStateFlow()

    init {
        refresh()
    }

    fun updateQuery(query: String) {
        _uiState.update { current -> current.copy(query = query) }
    }
    fun refresh() {
        _uiState.update { current -> current.copy(journaux = JournalApi.getJournal()) }
    }
}