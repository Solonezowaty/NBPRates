package com.solonezowaty.currentrates.data

import arrow.core.Either
import com.solonezowaty.currentrates.data.remote.CurrentRatesApi
import com.solonezowaty.currentrates.data.remote.model.RateTablesData
import com.solonezowaty.currentrates.data.remote.model.TableRate
import com.solonezowaty.currentrates.data.repository.CurrentRatesRepositoryImpl
import com.solonezowaty.currentrates.domain.repository.CurrentRatesRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class CurrentRatesRepositoryTest {

    private lateinit var currentRatesRepository: CurrentRatesRepository
    private lateinit var currentRatesApi: CurrentRatesApi

    @Before
    fun setUp() {
        currentRatesApi = mockk()
        currentRatesRepository = CurrentRatesRepositoryImpl(currentRatesApi)
    }

    @Test
    fun `when getCurrentRates is successful then return list of RateItem`(): Unit = runBlocking {
        // Given
        val tableType = "A"
        coEvery { currentRatesApi.getCurrentRatesTable(any()) } returns Either.Right(listOf(mockCurrentRatesData()))

        // When
        val result = currentRatesRepository.getCurrentRates(tableType)

        // Then
        coVerify(exactly = 1) { currentRatesApi.getCurrentRatesTable(tableType) }
        assertTrue(result.isRight())
        result.onRight { rateItems ->
            assertEquals(2, rateItems.size)
            assertEquals("USD", rateItems[0].code)
            assertEquals("EUR", rateItems[1].code)
        }
    }

    @Test
    fun `when getCurrentRates fails then return Error`(): Unit = runBlocking {
        // Given
        val tableType = "A"
        val errorMessage = Throwable("Network Error")
        coEvery { currentRatesApi.getCurrentRatesTable(any()) } returns Either.Left(errorMessage)

        // When
        val result = currentRatesRepository.getCurrentRates(tableType)

        // Then
        coVerify(exactly = 1) { currentRatesApi.getCurrentRatesTable(tableType) }
        assertTrue(result.isLeft())
        result.onLeft { error ->
            assertEquals("Network Error", error.message)
        }
    }

    private fun mockCurrentRatesData(): RateTablesData {
        return RateTablesData(
            table = "A",
            rates = listOf(
                TableRate(code = "USD", currency = "Dolar amerykański", mid = 3.8),
                TableRate(code = "EUR", currency = "Euro", mid = 4.2)
            ),
            no = "2",
            effectiveDate = "2024-08-12"
        )
    }
}