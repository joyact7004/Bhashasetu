package com.example.bhasasetu.domain.repository

import com.example.bhasasetu.domain.model.TranslationResult
import kotlinx.coroutines.flow.Flow

interface TranslationRepository {
    fun getTranslationHistory(): Flow<List<TranslationResult>>
    
    suspend fun translate(
        text: String, 
        sourceLang: String, 
        targetLang: String
    ): Result<TranslationResult>

    suspend fun clearHistory()
}