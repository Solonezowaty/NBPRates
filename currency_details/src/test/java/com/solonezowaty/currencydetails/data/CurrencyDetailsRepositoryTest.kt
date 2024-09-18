package com.solonezowaty.currencydetails.data

import arrow.core.Either
import com.solonezowaty.currencydetails.data.remote.api.CurrencyDetailsApi
import com.solonezowaty.currencydetails.data.remote.model.CurrencyDetailsData
import com.solonezowaty.currencydetails.data.remote.model.Rate
import com.solonezowaty.currencydetails.data.repository.CurrencyDetailsRepositoryImpl
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class CurrencyDetailsRepositoryImplTest {

    private lateinit var currencyDetailsApi: CurrencyDetailsApi
    private lateinit var repository: CurrencyDetailsRepositoryImpl

    @Before
    fun setUp() {
        currencyDetailsApi = mockk()
        repository = CurrencyDetailsRepositoryImpl(currencyDetailsApi)
    }

    @Test
    fun `when getCurrencyDetails is successful, then return correct data`(): Unit = runBlocking {
        // Given
        val tableType = "A"
        val currencyCode = "USD"
        val startDate = "2023-08-01"
        val endDate = "2023-08-31"

        coEvery {
            currencyDetailsApi.getCurrencyDetailsFromLastTwoWeeks(any(), any(), any(), any())
        } returns Either.Right(mockCurrencyDetailsData())

        // When
        val result = repository.getCurrencyDetails(tableType, currencyCode, startDate, endDate)

        // Then
        coVerify(exactly = 1) { currencyDetailsApi.getCurrencyDetailsFromLastTwoWeeks(tableType, currencyCode, startDate, endDate) }
        assertTrue(result.isRight())
        result.onRight { currencyDetails ->
            assertEquals("EUR", currencyDetails.code)
            assertEquals("Euro", currencyDetails.currency)
        }
    }

    @Test
    fun `when getCurrencyDetails fails, then return error`(): Unit = runBlocking {
        // Given
        val tableType = "A"
        val currencyCode = "USD"
        val startDate = "2023-08-01"
        val endDate = "2023-08-31"

        val errorMessage = Throwable("Network Error")
        coEvery { currencyDetailsApi.getCurrencyDetailsFromLastTwoWeeks(any(), any(), any(), any()) } returns Either.Left(errorMessage)

        // When
        val result = repository.getCurrencyDetails(tableType, currencyCode, startDate, endDate)

        // Then
        coVerify(exactly = 1) { currencyDetailsApi.getCurrencyDetailsFromLastTwoWeeks(tableType, currencyCode, startDate, endDate) }
        assertTrue(result.isLeft())
        result.onLeft { error ->
            assertEquals("Network Error", error.message)
        }
    }

    private fun mockCurrencyDetailsData(): CurrencyDetailsData {
        return CurrencyDetailsData(
            code = "EUR",
            currency = "Euro",
            rates = listOf(
                Rate("2023-09-10", 3.7, "1"),
                Rate("2023-09-12", 3.9, "2"),
                Rate("2023-09-11", 3.8, "3")
            ),
            table = "A"
        )
    }
}
