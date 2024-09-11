package com.solonezowaty.nbpratesapp.presentation.rateslist.components

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.solonezowaty.nbpratesapp.domain.model.RateItem

@Composable
fun NbpRatesList(
    ratesList: List<RateItem>,
    onDetailsClicked: (Pair<String, String>) -> Unit,
    onPageScrolled: () -> Unit
) {
    LazyColumn(
        modifier = Modifier.padding(8.dp)
    ) {
        itemsIndexed(
            ratesList,
            key = { _, item -> item.code }
        ) { index, rate ->
            NbpRatesListItem(
                item = rate,
                onItemClicked = {
                    onDetailsClicked(
                        Pair(
                            rate.tableType,
                            rate.code
                        )
                    )
                }
            )
            if (index >= ratesList.size - 1) {
                onPageScrolled()
            }
        }
    }
}