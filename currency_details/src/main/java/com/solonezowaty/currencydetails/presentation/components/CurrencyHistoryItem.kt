package com.solonezowaty.currencydetails.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.solonezowaty.core.ui.theme.Typography
import com.solonezowaty.currencydetails.R
import com.solonezowaty.currencydetails.domain.model.CurrencyRate

@Composable
fun CurrencyHistoryItem(
    item: CurrencyRate
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = item.date,
                style = Typography.titleSmall
            )
            Row(verticalAlignment = Alignment.CenterVertically) {
                if (item.isDeviated) {
                    Image(
                        painter = painterResource(R.drawable.rate_up_icon),
                        contentDescription = null
                    )
                }
                Text(
                    text = item.rate.toString(),
                    color = if (item.isDeviated) {
                        MaterialTheme.colorScheme.error
                    } else {
                        MaterialTheme.colorScheme.onSurfaceVariant
                    }
                )
            }
        }
    }
}

@Preview
@Composable
private fun CurrencyHistoryItemPreview() {
    CurrencyHistoryItem(
        CurrencyRate(
            "2024-09-12",
            0.2341,
            isDeviated = true
        )
    )
}