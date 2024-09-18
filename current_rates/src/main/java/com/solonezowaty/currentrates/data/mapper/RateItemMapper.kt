package com.solonezowaty.currentrates.data.mapper

import com.solonezowaty.currentrates.data.remote.model.TableRate
import com.solonezowaty.currentrates.domain.model.RateItem

fun TableRate.toRateItem(tableType: String) = RateItem(
    tableType = tableType,
    code =  code,
    currency = currency.uppercase(),
    midRate = mid.toBigDecimal().toPlainString()
)