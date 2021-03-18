package com.erbe.rwquotes.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

@Entity(tableName = "rwquotes")
data class Quote(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "text") val text: String = "",
    @ColumnInfo(name = "author") var author: String = "",
    @ColumnInfo(name = "date") val date: String = "",
    @ColumnInfo(name = "stars") val stars: Int = 0
)


