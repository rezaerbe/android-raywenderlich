package com.erbe.listmaster.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.erbe.listmaster.model.ListItem

@Dao
interface ListItemDao {

    @Query("SELECT * FROM list_items")
    fun getAll(): LiveData<List<ListItem>>

    @Query("SELECT * FROM list_items WHERE list_category_id = :listCategoryId")
    fun getAllByListCategoryId(listCategoryId: Long): LiveData<List<ListItem>>

    @Insert
    fun insertAll(vararg listItems: ListItem)
}