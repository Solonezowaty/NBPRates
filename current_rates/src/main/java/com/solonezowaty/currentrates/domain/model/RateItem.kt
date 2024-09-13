package com.solonezowaty.currentrates.domain.model

data class RateItem(
    val tableType: String,
    val code: String,
    val currency: String,
    val midRate: String
)