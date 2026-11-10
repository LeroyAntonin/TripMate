package com.example.tripmate.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tripmate.api.DatabaseApi
import com.example.tripmate.model.Journal
import kotlinx.coroutines.launch
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
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }
            try {
                val journaux = DatabaseApi.getJournaux()
                _uiState.update {
                    it.copy(journaux = journaux, isLoading = false)
                }
            } catch (exception: Exception) {
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        error = "Impossible de charger les journaux : ${exception.message}",
                    )
                }
            }
        }
    }
}