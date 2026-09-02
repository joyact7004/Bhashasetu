package com.example.bhasasetu.data.repository

import com.example.bhasasetu.data.local.dao.BhasasetuDao
import com.example.bhasasetu.data.local.entity.FlashcardEntity
import com.example.bhasasetu.data.local.entity.WorksheetEntity
import com.example.bhasasetu.data.remote.CurriculumApiService
import com.example.bhasasetu.data.remote.CurriculumRequest
import com.example.bhasasetu.domain.model.FlashcardItem
import com.example.bhasasetu.domain.model.Worksheet
import com.example.bhasasetu.domain.repository.SyncRepository
import com.example.bhasasetu.domain.repository.TranslationRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class SyncRepositoryImpl(
    private val apiService: CurriculumApiService,
    private val dao: BhasasetuDao,
    private val translationRepository: TranslationRepository
) : SyncRepository {

    private val _isSyncing = MutableStateFlow(false)
    override val isSyncing: StateFlow<Boolean> = _isSyncing.asStateFlow()

    override fun getFlashcards(): Flow<List<FlashcardItem>> {
        return dao.getAllFlashcards().map { entities ->
            entities.map { it.toDomain() }
        }
    }

    override fun getWorksheets(): Flow<List<Worksheet>> {
        return dao.getAllWorksheets().map { entities ->
            entities.map { it.toDomain() }
        }
    }

    override suspend fun syncDailyCurriculum(grade: String, subject: String, topic: String): Result<Unit> {
        _isSyncing.value = true
        return try {
            val response = apiService.generateCurriculum(CurriculumRequest(grade, subject, topic))
            
            // 1. Process Flashcards
            val flashcardEntities = response.flashcards.map { item ->
                // Trigger offline translation if Santhali text is empty
                val translatedItem = if (item.questionSanthali.isEmpty() || item.answerSanthali.isEmpty()) {
                    translateFlashcardLocally(item)
                } else {
                    item
                }
                translatedItem.toEntity()
            }
            dao.insertFlashcards(flashcardEntities)

            // 2. Process Worksheets
            val worksheetEntities = response.worksheets.map { it.toEntity() }
            worksheetEntities.forEach { dao.insertWorksheet(it) }

            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        } finally {
            _isSyncing.value = false
        }
    }

    private suspend fun translateFlashcardLocally(item: FlashcardItem): FlashcardItem {
        val qTranslation = translationRepository.translate(item.questionHindi, "hi", "sat")
        val aTranslation = translationRepository.translate(item.answerHindi, "hi", "sat")
        
        return item.copy(
            questionSanthali = qTranslation.getOrNull()?.translatedText ?: "",
            answerSanthali = aTranslation.getOrNull()?.translatedText ?: ""
        )
    }

    private fun FlashcardEntity.toDomain() = FlashcardItem(
        id = id,
        topic = topic,
        questionHindi = questionHindi,
        answerHindi = answerHindi,
        questionSanthali = questionSanthali,
        answerSanthali = answerSanthali,
        difficulty = difficulty
    )

    private fun WorksheetEntity.toDomain() = Worksheet(
        id = id,
        subject = subject,
        grade = grade,
        chapter = chapter,
        questionsList = Json.decodeFromString(questionsJson),
        generatedDate = generatedDate
    )

    private fun FlashcardItem.toEntity() = FlashcardEntity(
        id = id,
        topic = topic,
        questionHindi = questionHindi,
        answerHindi = answerHindi,
        questionSanthali = questionSanthali,
        answerSanthali = answerSanthali,
        difficulty = difficulty
    )

    private fun Worksheet.toEntity() = WorksheetEntity(
        id = id,
        subject = subject,
        grade = grade,
        chapter = chapter,
        questionsJson = Json.encodeToString(questionsList),
        generatedDate = generatedDate
    )
}