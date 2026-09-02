package com.example.bhasasetu.domain.repository

import com.example.bhasasetu.domain.model.FlashcardItem
import com.example.bhasasetu.domain.model.Worksheet
import kotlinx.coroutines.flow.Flow

interface SyncRepository {
    suspend fun syncDailyCurriculum(grade: String, subject: String, topic: String): Result<Unit>
    
    fun getFlashcards(): Flow<List<FlashcardItem>>
    fun getWorksheets(): Flow<List<Worksheet>>

    val isSyncing: Flow<Boolean>
}