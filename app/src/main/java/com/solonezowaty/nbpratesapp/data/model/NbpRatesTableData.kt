package com.solonezowaty.nbpratesapp.data.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class NbpRatesTableData(
    @Json(name = "effectiveDate")
    val effectiveDate: String,
    @Json(name = "no")
    val no: String,
    @Json(name = "rates")
    val rates: List<Rate>,
    @Json(name = "table")
    val table: String
)

@JsonClass(generateAdapter = true)
data class Rate(
    @Json(name = "code")
    val code: String,
    @Json(name = "currency")
    val currency: String,
    @Json(name = "mid")
    val mid: Double
)