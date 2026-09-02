package com.example.bhasasetu.data.local.dao

import androidx.room.*
import com.example.bhasasetu.data.local.entity.FlashcardEntity
import com.example.bhasasetu.data.local.entity.TranslationEntity
import com.example.bhasasetu.data.local.entity.WorksheetEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface BhasasetuDao {

    // Translation History
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTranslation(translation: TranslationEntity)

    @Query("SELECT * FROM translation_history ORDER BY timestamp DESC")
    fun getAllTranslations(): Flow<List<TranslationEntity>>

    @Query("DELETE FROM translation_history")
    suspend fun clearTranslationHistory()

    // Flashcards
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFlashcards(flashcards: List<FlashcardEntity>)

    @Query("SELECT * FROM flashcards WHERE topic = :topic")
    fun getFlashcardsByTopic(topic: String): Flow<List<FlashcardEntity>>

    @Query("SELECT * FROM flashcards")
    fun getAllFlashcards(): Flow<List<FlashcardEntity>>

    // Worksheets
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWorksheet(worksheet: WorksheetEntity)

    @Query("SELECT * FROM worksheets ORDER BY generatedDate DESC")
    fun getAllWorksheets(): Flow<List<WorksheetEntity>>

    @Query("SELECT * FROM worksheets WHERE id = :id")
    suspend fun getWorksheetById(id: String): WorksheetEntity?
}