package com.solonezowaty.currentrates.data.mapper

import com.solonezowaty.currentrates.domain.model.RateItem

fun com.solonezowaty.currentrates.data.remote.model.TableRate.toRateItem(tableType: String) = RateItem(
    tableType = tableType,
    code =  code,
    currency = currency.uppercase(),
    midRate = mid.toBigDecimal().toPlainString()
)