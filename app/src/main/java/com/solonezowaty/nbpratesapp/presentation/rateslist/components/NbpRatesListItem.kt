package com.solonezowaty.nbpratesapp.presentation.rateslist.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.solonezowaty.nbpratesapp.domain.model.RateItem
import com.solonezowaty.nbpratesapp.ui.theme.NbpRatesAppTheme
import com.solonezowaty.nbpratesapp.ui.theme.Typography

@Composable
fun NbpRatesListItem(
    item: RateItem,
    onItemClicked: (Pair<String, String>) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 8.dp)
            .clickable {
                onItemClicked(Pair(item.tableType, item.code))
            },
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = item.currency,
                    style = Typography.titleSmall,
                    maxLines = 3
                )
                Spacer(
                    modifier = Modifier.height(4.dp)
                )
                Text(
                    text = "Code: ${item.code}",
                    style = Typography.labelSmall
                )
            }
            Text(
                text = item.midRate
            )
        }
    }
}

@Preview
@Composable
private fun RatesListItemPreview() {
    NbpRatesAppTheme {
        NbpRatesListItem(
            RateItem(
                tableType = "A",
                code = "PLN",
                currency = "Polski Złoty",
                midRate = "0.25"
            ),
            onItemClicked = { }
        )
    }
}