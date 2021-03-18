package com.erbe.listmaster

import android.content.Context
import android.util.Log
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.erbe.listmaster.database.AppDatabase
import com.erbe.listmaster.database.ListCategoryDao
import com.erbe.listmaster.model.ListCategory
import org.junit.*
import org.junit.rules.TestRule
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ListCategoryDaoTest {

    @Rule
    @JvmField
    val rule: TestRule = InstantTaskExecutorRule()

    private lateinit var database: AppDatabase
    private lateinit var listCategoryDao: ListCategoryDao

    @Before
    fun setup() {
        val context: Context = InstrumentationRegistry.getInstrumentation().context
        try {
            database = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java)
                .allowMainThreadQueries().build()
        } catch (e: Exception) {
            Log.i("test", e.message.toString())
        }
        listCategoryDao = database.listCategoryDao()
    }

    @Test
    fun testAddingAndRetrievingData() {

        val preInsertRetrievedCategories = listCategoryDao.getAll().blockingObserve()

        val listCategory = ListCategory("Cats", 1)
        listCategoryDao.insertAll(listCategory)

        val postInsertRetrievedCategories = listCategoryDao.getAll().blockingObserve()
        val sizeDifference = postInsertRetrievedCategories!!.size - preInsertRetrievedCategories!!.size
        Assert.assertEquals(1, sizeDifference)
        val retrievedCategory = postInsertRetrievedCategories.last()
        Assert.assertEquals("Cats", retrievedCategory.categoryName)
    }

    @After
    fun tearDown() {
        database.close()
    }
}