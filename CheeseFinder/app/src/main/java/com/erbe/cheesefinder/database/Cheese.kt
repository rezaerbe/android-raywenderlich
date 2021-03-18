package com.erbe.cheesefinder.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "cheeses", indices = [Index(value = ["name"], unique = true)])
data class Cheese(@PrimaryKey(autoGenerate = true) var id: Long = 0,
                  @ColumnInfo(name = "name") var name: String,
                  @ColumnInfo(name = "favorite") var favorite: Int = 0)