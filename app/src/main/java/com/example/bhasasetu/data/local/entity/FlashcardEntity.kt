package com.example.bhasasetu.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "flashcards")
data class FlashcardEntity(
    @PrimaryKey val id: String,
    val topic: String,
    val questionHindi: String,
    val answerHindi: String,
    val questionSanthali: String,
    val answerSanthali: String,
    val difficulty: String
)