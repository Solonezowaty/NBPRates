package com.solonezowaty.currentrates.domain

import arrow.core.Either
import com.solonezowaty.core.utils.Constants
import com.solonezowaty.currentrates.domain.model.RateItem
import com.solonezowaty.currentrates.domain.repository.CurrentRatesRepository
import com.solonezowaty.currentrates.domain.usecase.GetCurrentRatesUseCaseImpl
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class GetCurrentRatesUseCaseImplTest {

    private val currentRatesRepository: CurrentRatesRepository = mockk()
    private lateinit var getCurrentRatesUseCase: GetCurrentRatesUseCaseImpl

    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        getCurrentRatesUseCase = GetCurrentRatesUseCaseImpl(currentRatesRepository)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `when ratesA and ratesB are successful then return combined list`(): Unit = runBlocking {
        // Given
        val ratesA = mockRatesTableA
        val ratesB = mockRatesTableB
        val expectedRateItems = ratesA + ratesB

        coEvery { currentRatesRepository.getCurrentRates(Constants.RATES_LIST_TYPE_A) } returns Either.Right(ratesA)
        coEvery { currentRatesRepository.getCurrentRates(Constants.RATES_LIST_TYPE_B) } returns Either.Right(ratesB)

        // When
        val result = getCurrentRatesUseCase.getCurrentRates()

        // Then
        coVerify(exactly = 1) { currentRatesRepository.getCurrentRates(Constants.RATES_LIST_TYPE_A) }
        coVerify(exactly = 1) { currentRatesRepository.getCurrentRates(Constants.RATES_LIST_TYPE_B) }

        assertTrue(result.isRight())
        result.onRight { rateItems ->
            assertEquals(expectedRateItems, rateItems)
            assertEquals(4, rateItems.size)
        }
    }

    @Test
    fun `when ratesA fails then return Error`(): Unit = runBlocking {
        // Given
        val error = Throwable("Repository A error")
        val ratesB = mockRatesTableB

        coEvery { currentRatesRepository.getCurrentRates(Constants.RATES_LIST_TYPE_A) } returns Either.Left(error)
        coEvery { currentRatesRepository.getCurrentRates(Constants.RATES_LIST_TYPE_B) } returns Either.Right(ratesB)

        // When
        val result = getCurrentRatesUseCase.getCurrentRates()

        // Then
        coVerify(exactly = 1) { currentRatesRepository.getCurrentRates(Constants.RATES_LIST_TYPE_A) }
        coVerify(exactly = 1) { currentRatesRepository.getCurrentRates(Constants.RATES_LIST_TYPE_B) }

        assertTrue(result.isLeft())
        result.onLeft { throwable ->
            assertEquals("Repository A error", throwable.message)
        }
    }

    @Test
    fun `when ratesB fails then return Error`(): Unit = runBlocking {
        // Given
        val ratesA = mockRatesTableA
        val error = Throwable("Repository B error")

        coEvery { currentRatesRepository.getCurrentRates(Constants.RATES_LIST_TYPE_A) } returns Either.Right(ratesA)
        coEvery { currentRatesRepository.getCurrentRates(Constants.RATES_LIST_TYPE_B) } returns Either.Left(error)

        // When
        val result = getCurrentRatesUseCase.getCurrentRates()

        // Then
        coVerify(exactly = 1) { currentRatesRepository.getCurrentRates(Constants.RATES_LIST_TYPE_A) }
        coVerify(exactly = 1) { currentRatesRepository.getCurrentRates(Constants.RATES_LIST_TYPE_B) }

        assertTrue(result.isLeft())
        result.onLeft { throwable ->
            assertEquals("Repository B error", throwable.message)
        }
    }

    @Test
    fun `when both lists are empty then return empty list`(): Unit = runBlocking {
        // Given
        coEvery { currentRatesRepository.getCurrentRates(Constants.RATES_LIST_TYPE_A) } returns Either.Right(emptyList())
        coEvery { currentRatesRepository.getCurrentRates(Constants.RATES_LIST_TYPE_B) } returns Either.Right(emptyList())

        // When
        val result = getCurrentRatesUseCase.getCurrentRates()

        // Then
        coVerify(exactly = 1) { currentRatesRepository.getCurrentRates(Constants.RATES_LIST_TYPE_A) }
        coVerify(exactly = 1) { currentRatesRepository.getCurrentRates(Constants.RATES_LIST_TYPE_B) }

        assertTrue(result.isRight())
        result.onRight { rateItems ->
            assertTrue(rateItems.isEmpty())
        }
    }

    private val mockRatesTableA = listOf(
        RateItem(
            "A",
            "USD",
            "Dolar amerykanski",
            "3.50"
        ),
        RateItem(
            "A",
            "EUR",
            "Euro",
            "4.50"
        )
    )

    private val mockRatesTableB = listOf(
        RateItem(
            "B",
            "GBP",
            "Funt szterling",
            "5.50"
        ),
        RateItem(
            "B",
            "JPY",
            "Jen japoński",
            "6.50"
        )
    )
}