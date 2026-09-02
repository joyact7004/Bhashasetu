package com.example.bhasasetu.data.repository

import com.example.bhasasetu.data.local.dao.BhasasetuDao
import com.example.bhasasetu.data.local.entity.TranslationEntity
import com.example.bhasasetu.domain.model.TranslationResult
import com.example.bhasasetu.domain.repository.TranslationRepository
import com.example.bhasasetu.util.SanthaliTranslator
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class TranslationRepositoryImpl(
    private val dao: BhasasetuDao
) : TranslationRepository {

    override fun getTranslationHistory(): Flow<List<TranslationResult>> {
        return dao.getAllTranslations().map { entities ->
            entities.map { it.toDomain() }
        }
    }

    override suspend fun translate(
        text: String,
        sourceLang: String,
        targetLang: String
    ): Result<TranslationResult> {
        return try {
            val translatedText = SanthaliTranslator.translate(text)
            
            val result = TranslationResult(
                sourceText = text,
                translatedText = translatedText,
                sourceLang = sourceLang,
                targetLang = targetLang
            )

            dao.insertTranslation(result.toEntity())
            Result.success(result)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun clearHistory() {
        dao.clearTranslationHistory()
    }

    private fun TranslationEntity.toDomain() = TranslationResult(
        sourceText = sourceText,
        translatedText = translatedText,
        sourceLang = sourceLang,
        targetLang = targetLang,
        timestamp = timestamp
    )

    private fun TranslationResult.toEntity() = TranslationEntity(
        sourceText = sourceText,
        translatedText = translatedText,
        sourceLang = sourceLang,
        targetLang = targetLang,
        timestamp = timestamp
    )
}