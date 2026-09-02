package com.example.bhasasetu.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.bhasasetu.data.local.dao.BhasasetuDao
import com.example.bhasasetu.data.local.entity.FlashcardEntity
import com.example.bhasasetu.data.local.entity.TranslationEntity
import com.example.bhasasetu.data.local.entity.WorksheetEntity

@Database(
    entities = [
        TranslationEntity::class,
        FlashcardEntity::class,
        WorksheetEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun bhasasetuDao(): BhasasetuDao
}