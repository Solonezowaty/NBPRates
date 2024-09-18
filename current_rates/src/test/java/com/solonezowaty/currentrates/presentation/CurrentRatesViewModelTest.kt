package com.solonezowaty.currentrates.presentation

import app.cash.turbine.test
import arrow.core.Either
import com.solonezowaty.core.utils.NetworkResponse
import com.solonezowaty.currentrates.domain.model.RateItem
import com.solonezowaty.currentrates.domain.usecase.GetCurrentRatesUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue

@OptIn(ExperimentalCoroutinesApi::class)
class CurrentRatesViewModelTest {

    private val getCurrentRatesUseCase: GetCurrentRatesUseCase = mockk()
    private lateinit var viewModel: CurrentRatesViewModel
    private val testDispatcher = UnconfinedTestDispatcher()

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    private fun initViewModel() {
        viewModel = CurrentRatesViewModel(
            getCurrentRatesUseCase = getCurrentRatesUseCase
        )
    }

    @Test
    fun `when useCase returns values, then state is Success`() = runTest {
        //Given
        coEvery { getCurrentRatesUseCase.getCurrentRates() } returns Either.Right(mockListRateItems)

        //When
        initViewModel()

        //Then
        viewModel.state.test {
            val state = awaitItem()
            assertTrue(state.currentRatesNetworkResponse is NetworkResponse.Success)
            assertEquals(mockListRateItems, state.currentRatesNetworkResponse.data)

            coVerify { getCurrentRatesUseCase.getCurrentRates() }
        }
    }

    @Test
    fun `when getCurrentRates throws exception, then state is Error`() = runTest {
        //Given
        val mockError = Throwable("Network Error")
        coEvery { getCurrentRatesUseCase.getCurrentRates() } returns Either.Left(mockError)

        //When
        initViewModel()

        //Then
        viewModel.state.test {
            val state = awaitItem()
            assertTrue(state.currentRatesNetworkResponse is NetworkResponse.Error)
            assertEquals("Network Error", (state.currentRatesNetworkResponse as NetworkResponse.Error).message)

            coVerify { getCurrentRatesUseCase.getCurrentRates() }
        }
    }

    private val mockListRateItems = listOf(
        RateItem("A", "USD", "Dollar", "3.50"),
        RateItem("A", "EUR", "Euro", "4.50")
    )
}
