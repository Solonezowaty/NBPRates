package com.solonezowaty.currencydetails.data

import com.solonezowaty.currencydetails.data.mapper.toCurrencyDetails
import com.solonezowaty.currencydetails.data.remote.model.CurrencyDetailsData
import com.solonezowaty.currencydetails.data.remote.model.Rate
import com.solonezowaty.currencydetails.domain.model.CurrencyRate
import org.junit.Assert.assertEquals
import org.junit.Test

class CurrencyDetailsMapperTest {

    companion object {
        const val TEST_CURRENCY = "Dollar"
        const val TEST_CODE = "USD"
    }

    @Test
    fun `when CurrencyDetailsData is mapped to CurrencyDetails, then correct data is returned`() {
        // Given
        val currencyDetailsData = CurrencyDetailsData(
            code = TEST_CODE,
            currency = TEST_CURRENCY,
            rates = listOf(
                Rate(effectiveDate = "2024-08-17", mid = 3.50, no = "1"),
                Rate(effectiveDate = "2024-08-18", mid = 3.95, no = "2")
            ),
            table = "A"
        )
        val currentMidRate = 3.55

        // When
        val result = currencyDetailsData.toCurrencyDetails(currentMidRate)

        // Then
        assertEquals(result.code, TEST_CODE)
        assertEquals(result.currency, TEST_CURRENCY)
        assertEquals(result.ratesList.size, 2)
        
        assertEquals(result.ratesList[0].date, "2024-08-17")
        assertEquals(result.ratesList[0].rate, 3.5, 000.1)
        assertEquals(result.ratesList[0].isDeviated, false)
        
        assertEquals(result.ratesList[1].date, "2024-08-18")
        assertEquals(result.ratesList[1].rate, 3.95, 000.1)
        assertEquals(result.ratesList[1].isDeviated, true)
    }

    @Test
    fun `when CurrencyDetailsData is mapped to CurrencyDetails with empty rates list, then correct data is returned`() {
        // Given
        val currencyDetailsData = CurrencyDetailsData(
            code = TEST_CODE,
            currency = TEST_CURRENCY,
            rates = emptyList(),
            table = "A"
        )
        val currentMidRate = 3.55

        // When
        val result = currencyDetailsData.toCurrencyDetails(currentMidRate)

        // Then
        assertEquals(result.ratesList, emptyList<CurrencyRate>())
    }
}