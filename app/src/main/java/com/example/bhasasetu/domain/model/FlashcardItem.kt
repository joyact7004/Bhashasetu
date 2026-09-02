package com.example.bhasasetu.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class FlashcardItem(
    val id: String,
    val topic: String,
    val questionHindi: String,
    val answerHindi: String,
    val questionSanthali: String,
    val answerSanthali: String,
    val difficulty: String // e.g., "Easy", "Medium", "Hard"
)