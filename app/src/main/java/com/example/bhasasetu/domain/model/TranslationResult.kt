package com.example.bhasasetu.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class TranslationResult(
    val sourceText: String,
    val translatedText: String,
    val sourceLang: String,
    val targetLang: String,
    val timestamp: Long = System.currentTimeMillis()
)