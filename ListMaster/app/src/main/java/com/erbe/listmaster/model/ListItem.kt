package com.erbe.listmaster.model

import androidx.room.*
import androidx.room.ForeignKey.CASCADE

@Entity(
    tableName = "list_items",
    foreignKeys = [ForeignKey(
        entity = ListCategory::class,
        parentColumns = ["id"],
        childColumns = ["list_category_id"],
        onDelete = CASCADE
    )],
    indices = [Index(value = arrayOf("list_category_id"), name = "index_list_category_id")]
)
data class ListItem(
    @ColumnInfo(name = "item_description") var itemDescription: String,
    @ColumnInfo(name = "item_priority") var itemPriority: Int,
    @ColumnInfo(name = "list_category_id") var listCategoryId: Long,
    @ColumnInfo(name = "id") @PrimaryKey(autoGenerate = true) var id: Long = 0
)