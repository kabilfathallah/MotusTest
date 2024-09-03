package com.kabil.core.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.kabil.core.database.datasource.LocalDatasource
import com.kabil.core.database.model.WordEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface WordsDao {
    @Insert
    suspend fun insertAll(vararg words: WordEntity)

    @Query("SELECT * FROM WordEntity")
    fun getAllWords(): Flow<List<WordEntity>>

    @Query("SELECT * FROM WordEntity ORDER BY RANDOM() LIMIT 1")
    fun getRandomWord(): Flow<WordEntity>

    @Query("SELECT EXISTS (SELECT * FROM WordEntity WHERE text = :word) ")
    fun checkIfExist(word: String): Boolean


}