package com.erbe.findmylaunch

import android.text.Editable
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.erbe.findmylaunch.db.Launch
import com.erbe.findmylaunch.db.LaunchDao
import com.erbe.findmylaunch.db.LaunchesDatabase
import com.erbe.findmylaunch.search.SearchViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class SearchViewModelTest {

    private lateinit var db: LaunchesDatabase
    private lateinit var dao: LaunchDao

    @Before
    fun setup() {
        db = Room.inMemoryDatabaseBuilder(ApplicationProvider.getApplicationContext(), LaunchesDatabase::class.java).build()
        runBlocking {
            db.launchDao().apply {
                save(Launch("1", "Launch One"))
                save(Launch("2", "Launch Two"))
                save(Launch("3", "Launch Three"))
            }
        }
        dao = db.launchDao()
    }

    @Test
    fun shouldFetchAllLaunchesAsSearchResultsWhenFirstCreated() {
        runBlocking {
            val viewModel = SearchViewModel(dao)
            delay(100)
            with(viewModel.searchResults.value) {
                assert(this != null)
                assert(this == dao.all())
            }
        }
    }

    @Test
    fun emptySearchQueryShouldReturnAllLaunchesInDatabase() {
        runBlocking {
            val viewModel = SearchViewModel(dao)
            val emptyQuery = Editable.Factory.getInstance().newEditable("")
            viewModel.search(emptyQuery)
            delay(100)
            with(viewModel.searchResults.value) {
                assert(this != null)
                assert(this == dao.all())
            }
        }
    }
}