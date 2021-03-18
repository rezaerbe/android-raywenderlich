package com.erbe.findmylaunch

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.erbe.findmylaunch.db.Launch
import com.erbe.findmylaunch.db.LaunchDao
import com.erbe.findmylaunch.db.LaunchesDatabase
import com.erbe.findmylaunch.details.DetailsViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class DetailsViewModelTest {

    private lateinit var db: LaunchesDatabase
    private lateinit var dao: LaunchDao

    @Before
    fun setup() {
        db = Room.inMemoryDatabaseBuilder(
                ApplicationProvider.getApplicationContext(),
                LaunchesDatabase::class.java
        ).build()
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
    fun shouldFetchLaunchWithGivenName() = runBlocking {
        val viewModel = DetailsViewModel(dao)
        viewModel.fetchLaunchDetails("1")
        delay(100)
        with(viewModel.launch.value) {
            assert(this != null)
            assert(this!!.name == "1")
        }
    }
}