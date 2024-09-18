package com.solonezowaty.currencydetails.domain

import arrow.core.Either
import com.solonezowaty.currencydetails.domain.model.CurrencyDetails
import com.solonezowaty.currencydetails.domain.model.CurrencyRate
import com.solonezowaty.currencydetails.domain.repositories.CurrencyDetailsRepository
import com.solonezowaty.currencydetails.domain.usecases.GetCurrencyDetailsUseCaseImpl
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import java.time.Instant
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
class GetCurrencyDetailsUseCaseImplTest {

    private val currencyDetailsRepository: CurrencyDetailsRepository = mockk()
    private lateinit var useCase: GetCurrencyDetailsUseCaseImpl
    private val testDispatcher = UnconfinedTestDispatcher()

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        useCase = GetCurrencyDetailsUseCaseImpl(currencyDetailsRepository)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `when repository returns values, then useCase returns correct data`() = runTest {
        //Given
        val tableType = "A"
        val currencyCode = "USD"
        val startDate = Instant.parse("2024-08-15T00:00:00Z")
        val endDate = Instant.parse("2024-08-01T00:00:00Z")
        val expectedCurrencyDetails = CurrencyDetails(
            currency = "USD",
            code = "USD",
            ratesList = listOf(
                CurrencyRate(date = "2024-08-15", rate = 3.5, isDeviated = false),
                CurrencyRate(date = "2024-08-01", rate = 3.6, isDeviated = true)
            )
        )

        coEvery {
            currencyDetailsRepository.getCurrencyDetails(any(), any(), any(), any())
        } returns Either.Right(expectedCurrencyDetails)

        //When
        val result = useCase.getCurrencyDetails(tableType, currencyCode, startDate, endDate)

        //Then
        assertTrue(result.isRight())
        result.onRight { item ->
            assertEquals(expectedCurrencyDetails, item)
        }


        coVerify {
            currencyDetailsRepository.getCurrencyDetails(
                tableType,
                currencyCode,
                "2024-08-15",
                "2024-08-01"
            )
        }
    }

    @Test
    fun `when repository throw error, then useCase returns error`() = runTest {
        //Given
        val mockError = Throwable("Network error")
        coEvery {
            currencyDetailsRepository.getCurrencyDetails(any(), any(), any(), any())
        } returns Either.Left(mockError)

        val tableType = "A"
        val currencyCode = "USD"
        val startDate = Instant.parse("2024-08-15T00:00:00Z")
        val endDate = Instant.parse("2024-08-01T00:00:00Z")

        //When
        val result = useCase.getCurrencyDetails(tableType, currencyCode, startDate, endDate)

        //Then
        assertTrue(result is Either.Left)
        assertEquals(mockError, (result as Either.Left).value)

        coVerify {
            currencyDetailsRepository.getCurrencyDetails(
                tableType,
                currencyCode,
                "2024-08-15",
                "2024-08-01"
            )
        }
    }

    @Test
    fun `when getCurrencyDetails is called then correct parameters are passed to repository`() = runTest {
        val expectedCurrencyDetails = CurrencyDetails(
            currency = "USD",
            code = "USD",
            ratesList = listOf(
                CurrencyRate(date = "2024-08-04", rate = 3.5, isDeviated = false)
            )
        )

        coEvery {
            currencyDetailsRepository.getCurrencyDetails(any(), any(), any(), any())
        } returns Either.Right(expectedCurrencyDetails)

        val startDate = Instant.parse("2024-08-15T10:15:30Z")
        val endDate = Instant.parse("2024-08-01T10:15:30Z")

        useCase.getCurrencyDetails("A", "USD", startDate, endDate)

        coVerify {
            currencyDetailsRepository.getCurrencyDetails(
                "A",
                "USD",
                "2024-08-15",
                "2024-08-01"
            )
        }
    }
}
