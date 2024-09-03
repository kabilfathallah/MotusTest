package com.kabil.core.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class WordEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val text: String
)
