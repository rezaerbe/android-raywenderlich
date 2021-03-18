package com.erbe.smallvictories

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.nhaarman.mockito_kotlin.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class VictoryViewModelTest {

    @Rule
    @JvmField
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private val viewStateObserver: Observer<VictoryUiModel> = mock()
    private val mockVictoryRepository: VictoryRepository = mock()
    private val viewModel = VictoryViewModel()

    @Before
    fun setUpTaskDetailViewModel() {
        viewModel.viewState.observeForever(viewStateObserver)
        viewModel.repository = mockVictoryRepository
    }

    @Test
    fun initializeReturnsTitle() {
        val title = "New title"
        val count = 5
        stubVictoryRepositoryGetVictoryTitleAndCount(Pair(title, count))
        viewModel.initialize()

        verify(viewStateObserver).onChanged(VictoryUiModel.TitleUpdated(title))
    }

    @Test
    fun initializeReturnsCount() {
        val title = "New title"
        val count = 5
        stubVictoryRepositoryGetVictoryTitleAndCount(Pair(title, count))
        viewModel.initialize()

        verify(viewStateObserver).onChanged(VictoryUiModel.CountUpdated(count))
    }

    @Test
    fun setVictoryTitleSavesTitle() {
        val title = "New title"
        viewModel.setVictoryTitle(title)

        verify(mockVictoryRepository).setVictoryTitle(title)
    }

    @Test
    fun setVictoryTitleReturnsTitle() {
        val title = "New title"
        viewModel.setVictoryTitle(title)

        verify(viewStateObserver).onChanged(VictoryUiModel.TitleUpdated(title))
    }

    @Test
    fun incrementVictoryCountCallsRepository() {
        stubVictoryRepositoryGetVictoryCount(5)
        viewModel.incrementVictoryCount()

        verify(mockVictoryRepository).getVictoryCount()
    }

    @Test
    fun incrementVictoryCountUpdatesCount() {
        val previousCount = 5
        stubVictoryRepositoryGetVictoryCount(previousCount)
        viewModel.incrementVictoryCount()

        verify(mockVictoryRepository).setVictoryCount(previousCount + 1)
    }

    @Test
    fun incrementVictoryCountReturnsUpdatedCount() {
        val previousCount = 5
        stubVictoryRepositoryGetVictoryCount(previousCount)
        viewModel.incrementVictoryCount()

        verify(viewStateObserver).onChanged(VictoryUiModel.CountUpdated(previousCount + 1))
    }

    @Test
    fun resetCallsRepository() {
        val title = "New title"
        val count = 5
        stubVictoryRepositoryGetVictoryTitleAndCount(Pair(title, count))
        viewModel.reset()

        verify(mockVictoryRepository).clear()
    }

    @Test
    fun resetReturnsTitle() {
        val title = "New title"
        val count = 5
        stubVictoryRepositoryGetVictoryTitleAndCount(Pair(title, count))
        viewModel.reset()

        verify(viewStateObserver).onChanged(VictoryUiModel.TitleUpdated(title))
    }

    @Test
    fun resetReturnsCount() {
        val title = "New title"
        val count = 5
        stubVictoryRepositoryGetVictoryTitleAndCount(Pair(title, count))
        viewModel.reset()

        verify(viewStateObserver).onChanged(VictoryUiModel.CountUpdated(count))
    }

    private fun stubVictoryRepositoryGetVictoryTitleAndCount(titleAndCount: Pair<String, Int>) {
        stubVictoryRepositoryGetVictoryTitle(titleAndCount.first)
        stubVictoryRepositoryGetVictoryCount(titleAndCount.second)
        whenever(mockVictoryRepository.getVictoryTitleAndCount())
            .thenReturn(titleAndCount)
    }

    private fun stubVictoryRepositoryGetVictoryTitle(title: String) {
        whenever(mockVictoryRepository.getVictoryTitle())
            .thenReturn(title)
    }

    private fun stubVictoryRepositoryGetVictoryCount(count: Int) {
        whenever(mockVictoryRepository.getVictoryCount())
            .thenReturn(count)
    }
}