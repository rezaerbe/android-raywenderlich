package com.erbe.listmaster.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "list_categories")
data class ListCategory(
    @ColumnInfo(name = "category_name") var categoryName: String,
    @ColumnInfo(name = "id") @PrimaryKey(autoGenerate = true) var id: Long = 0
)