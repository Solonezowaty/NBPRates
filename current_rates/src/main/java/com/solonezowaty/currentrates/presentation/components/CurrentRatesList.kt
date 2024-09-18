package com.solonezowaty.currentrates.presentation.components

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.solonezowaty.currentrates.domain.model.RateItem

@Composable
fun CurrentRatesList(
    ratesList: List<RateItem>,
    onDetailsClicked: (String, String) -> Unit,
    onPageScrolled: () -> Unit
) {
    LazyColumn(
        modifier = Modifier.padding(start = 8.dp, end = 8.dp)
    ) {
        itemsIndexed(
            ratesList,
            key = { _, item -> item.code }
        ) { index, rate ->
            CurrentRatesItem(
                item = rate,
                onItemClicked = {
                    onDetailsClicked(
                        rate.tableType,
                        rate.code
                    )
                }
            )
            if (index >= ratesList.size - 1) {
                onPageScrolled()
            }
        }
    }
}