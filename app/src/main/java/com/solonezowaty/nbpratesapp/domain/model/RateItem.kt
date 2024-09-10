package com.solonezowaty.nbpratesapp.domain.model

data class RateItem(
    val tableType: String,
    val code: String,
    val currency: String,
    val midRate: Double
)