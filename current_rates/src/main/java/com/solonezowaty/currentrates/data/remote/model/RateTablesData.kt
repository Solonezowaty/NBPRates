package com.solonezowaty.currentrates.data.remote.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class RateTablesData(
    @Json(name = "effectiveDate")
    val effectiveDate: String,
    @Json(name = "no")
    val no: String,
    @Json(name = "rates")
    val rates: List<TableRate>,
    @Json(name = "table")
    val table: String
)

@JsonClass(generateAdapter = true)
data class TableRate(
    @Json(name = "code")
    val code: String,
    @Json(name = "currency")
    val currency: String,
    @Json(name = "mid")
    val mid: Double
)