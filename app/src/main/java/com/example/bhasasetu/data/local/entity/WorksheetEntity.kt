package com.example.bhasasetu.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "worksheets")
data class WorksheetEntity(
    @PrimaryKey val id: String,
    val subject: String,
    val grade: String,
    val chapter: String,
    val questionsJson: String, // Stored as JSON string
    val generatedDate: Long
)