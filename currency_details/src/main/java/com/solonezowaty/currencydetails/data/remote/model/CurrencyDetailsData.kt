package com.solonezowaty.currencydetails.data.remote.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CurrencyDetailsData(
    @Json(name = "code")
    val code: String,
    @Json(name = "currency")
    val currency: String,
    @Json(name = "rates")
    val rates: List<Rate>,
    @Json(name = "table")
    val table: String
)

@JsonClass(generateAdapter = true)
data class Rate(
    @Json(name = "effectiveDate")
    val effectiveDate: String,
    @Json(name = "mid")
    val mid: Double,
    @Json(name = "no")
    val no: String
)