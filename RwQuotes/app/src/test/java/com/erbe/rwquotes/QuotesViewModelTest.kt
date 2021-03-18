package com.erbe.rwquotes

import android.os.Build
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.erbe.rwquotes.data.Quote
import com.erbe.rwquotes.data.QuotesRepositoryImpl
import com.erbe.rwquotes.ui.viewmodel.QuotesViewModel
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations

@RunWith(AndroidJUnit4::class)
class QuotesViewModelTest {

  @Mock
  private lateinit var viewModel: QuotesViewModel

  @Mock
  private lateinit var isLoadingLiveData: LiveData<Boolean>

  @Mock
  private lateinit var observer: Observer<Boolean>

  @get:Rule
  var instantExecutorRule = InstantTaskExecutorRule()

  /**
   * Setup values before init tests
   *
   */
  @Before
  fun setup() {
    MockitoAnnotations.initMocks(this)

    viewModel = spy(QuotesViewModel(ApplicationProvider.getApplicationContext(),
        QuotesRepositoryImpl(ApplicationProvider.getApplicationContext())))
    isLoadingLiveData = viewModel.dataLoading
  }

  /*
   * Testing *onChanged()* method for [LiveData]
   *
   */
  @Test
  fun `Verify livedata values changes on event`() {
    assertNotNull(viewModel.getAllQuotes())
    viewModel.dataLoading.observeForever(observer)
    verify(observer).onChanged(false)
    viewModel.getAllQuotes()
    verify(observer).onChanged(true)
  }

  /**
   * Test asserting values for [LiveData] items on [QuotesViewModel] to insert [Quote]
   *
   */
  @Test
  fun `Assert loading values are correct fetching quotes`() {
    val testQuote = Quote(id = 1, text = "Hello World!", author = "Ray Wenderlich",
        date = "27/12/1998")
    var isLoading = isLoadingLiveData.value
    assertNotNull(isLoading)
    isLoading?.let { assertTrue(it) }
    viewModel.insertQuote(testQuote)
    isLoading = isLoadingLiveData.value
    assertNotNull(isLoading)
    isLoading?.let { assertFalse(it) }
  }

  /**
   * Test asserting values for [LiveData] items on [QuotesViewModel] to delete [Quote]
   *
   */
  @Test
  fun `Assert loading values are correct deleting quote`() {
    val testQuote = Quote(id = 1, text = "Hello World!", author = "Ray Wenderlich",
        date = "27/12/1998")
    var isLoading = isLoadingLiveData.value
    assertNotNull(isLoading)
    isLoading?.let { assertTrue(it) }
    viewModel.delete(testQuote)
    isLoading = isLoadingLiveData.value
    assertNotNull(isLoading)
    isLoading?.let { assertFalse(it) }
  }

  /**
   * Test asserting values for [LiveData] items on [QuotesViewModel] to update [Quote]
   *
   */
  @Test
  fun `Assert loading values are correct updating quote`() {
    val testQuote = Quote(id = 1, text = "Hello World!", author = "Ray Wenderlich",
        date = "27/12/1998")
    var isLoading = isLoadingLiveData.value
    assertNotNull(isLoading)
    isLoading?.let { assertTrue(it) }
    viewModel.updateQuote(testQuote)
    isLoading = isLoadingLiveData.value
    assertNotNull(isLoading)
    isLoading?.let { assertFalse(it) }
  }
}