package com.example.bhasasetu.presentation.curriculum

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bhasasetu.domain.model.FlashcardItem
import com.example.bhasasetu.domain.model.Worksheet
import com.example.bhasasetu.domain.repository.SyncRepository
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

data class CurriculumUiState(
    val flashcards: List<FlashcardItem> = emptyList(),
    val worksheets: List<Worksheet> = emptyList(),
    val isSyncing: Boolean = false,
    val error: String? = null
)

class CurriculumViewModel(
    private val syncRepository: SyncRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(CurriculumUiState())
    val uiState: StateFlow<CurriculumUiState> = _uiState.asStateFlow()

    init {
        observeCurriculumData()
    }

    private fun observeCurriculumData() {
        combine(
            syncRepository.getFlashcards(),
            syncRepository.getWorksheets(),
            syncRepository.isSyncing
        ) { flashcards, worksheets, isSyncing ->
            CurriculumUiState(
                flashcards = flashcards,
                worksheets = worksheets,
                isSyncing = isSyncing
            )
        }.onEach { newState ->
            _uiState.update { it.copy(
                flashcards = newState.flashcards,
                worksheets = newState.worksheets,
                isSyncing = newState.isSyncing
            ) }
        }.launchIn(viewModelScope)
    }

    fun syncCurriculum(grade: String, subject: String, topic: String) {
        viewModelScope.launch {
            val result = syncRepository.syncDailyCurriculum(grade, subject, topic)
            if (result.isFailure) {
                _uiState.update { it.copy(error = result.exceptionOrNull()?.message ?: "Unknown Sync Error") }
            } else {
                _uiState.update { it.copy(error = null) }
            }
        }
    }

    fun clearError() {
        _uiState.update { it.copy(error = null) }
    }
}