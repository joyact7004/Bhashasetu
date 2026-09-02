package com.example.bhasasetu.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class Worksheet(
    val id: String,
    val subject: String,
    val grade: String,
    val chapter: String,
    val questionsList: List<String>,
    val generatedDate: Long = System.currentTimeMillis()
)