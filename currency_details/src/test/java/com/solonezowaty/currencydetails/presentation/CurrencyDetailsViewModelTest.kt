package com.solonezowaty.currencydetails.presentation

import androidx.lifecycle.SavedStateHandle
import app.cash.turbine.test
import arrow.core.Either
import com.solonezowaty.core.utils.NetworkResponse
import com.solonezowaty.currencydetails.domain.model.CurrencyDetails
import com.solonezowaty.currencydetails.domain.model.CurrencyRate
import com.solonezowaty.currencydetails.domain.usecases.GetCurrencyDetailsUseCase
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class CurrencyDetailsViewModelTest {

    private lateinit var getCurrencyDetailsUseCase: GetCurrencyDetailsUseCase
    private lateinit var savedStateHandle: SavedStateHandle
    private lateinit var viewModel: CurrencyDetailsViewModel
    private val testDispatcher = UnconfinedTestDispatcher()

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        getCurrencyDetailsUseCase = mockk()
        savedStateHandle = mockk()

        every { savedStateHandle.get<String>("tableType") } returns "A"
        every { savedStateHandle.get<String>("code") } returns "USD"
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    private fun initViewModel() {
        viewModel = CurrencyDetailsViewModel(
            savedStateHandle = savedStateHandle,
            getCurrencyDetailsUseCase = getCurrencyDetailsUseCase
        )
    }

    @Test
    fun `when useCase returns values, then state is Success`() = runTest {
        //Given
        coEvery { getCurrencyDetailsUseCase.getCurrencyDetails(any(), any(), any(), any()) }
            .returns(Either.Right(mockCurrencyDetails()))

        //When
        initViewModel()

        //Then
        viewModel.state.test {
            val state = awaitItem()
            assertTrue(state.currencyDetailsNetworkResponse is NetworkResponse.Success)
            assertEquals(mockCurrencyDetails(), state.currencyDetailsNetworkResponse.data)
        }
    }

    @Test
    fun `when getCurrencyDetails throws exception, then state is Error`() = runTest {
        //Given
        val errorMessage = "Network error"
        coEvery { getCurrencyDetailsUseCase.getCurrencyDetails(any(), any(), any(), any()) }
            .returns(Either.Left(Throwable(errorMessage)))

        //When
        initViewModel()

        //Then
        viewModel.state.test {
            val state = awaitItem()
            assertTrue(state.currencyDetailsNetworkResponse is NetworkResponse.Error)
            assertEquals(errorMessage, state.currencyDetailsNetworkResponse.message)
        }
    }

    private fun mockCurrencyDetails(): CurrencyDetails {
        return CurrencyDetails(
            code = "USD",
            currency = "Dollar",
            ratesList = listOf(
                CurrencyRate("2023-09-01", 3.8, isDeviated = true),
                CurrencyRate("2023-09-02", 3.9, isDeviated = false)
            )
        )
    }
}