package com.example.bhasasetu.data.remote

import com.example.bhasasetu.domain.model.FlashcardItem
import com.example.bhasasetu.domain.model.Worksheet
import kotlinx.serialization.Serializable
import retrofit2.http.Body
import retrofit2.http.POST

@Serializable
data class CurriculumRequest(
    val grade: String,
    val subject: String,
    val topic: String
)

@Serializable
data class CurriculumResponse(
    val flashcards: List<FlashcardItem>,
    val worksheets: List<Worksheet>
)

interface CurriculumApiService {
    @POST("api/v1/curriculum/generate")
    suspend fun generateCurriculum(@Body request: CurriculumRequest): CurriculumResponse
}