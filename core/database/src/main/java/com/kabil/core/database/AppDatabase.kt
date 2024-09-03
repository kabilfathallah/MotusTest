package com.kabil.core.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.kabil.core.database.dao.WordsDao
import com.kabil.core.database.model.WordEntity

@Database(entities = [WordEntity::class], version = 1)
internal abstract class AppDatabase : RoomDatabase() {
    abstract fun wordsDao(): WordsDao
}