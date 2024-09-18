package com.solonezowaty.core.presentation.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.solonezowaty.core.R
import com.solonezowaty.core.ui.theme.Typography

@Composable
fun NbpRatesTopBar(
    navController: NavController,
    screenTitle: String,
    screenSubTitle: String? = null,
    isBackAvailable: Boolean = false
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (isBackAvailable) {
            Image(
                modifier = Modifier
                    .size(36.dp)
                    .clickable {
                        navController.popBackStack()
                    },
                painter = painterResource(R.drawable.top_bar_back_arrow),
                colorFilter = ColorFilter.tint(color = MaterialTheme.colorScheme.onSurfaceVariant),
                contentDescription = null,
            )
        }
        Spacer(modifier = Modifier
            .width(8.dp))
        Image(
            painter = painterResource(R.drawable.nbp_logo) ,
            contentDescription = null
        )
        Spacer(modifier = Modifier
            .width(8.dp))
        Column {
            Text(
                text = screenTitle,
                style = Typography.titleLarge
            )
            screenSubTitle?.let {
                Spacer(modifier = Modifier
                    .width(8.dp))
                Text(
                    text = screenSubTitle,
                    style = Typography.labelSmall
                )
            }
        }
    }
}

@Preview
@Composable
private fun NbpRatesTopBarPreview() {
    NbpRatesTopBar(
        navController = rememberNavController(),
        screenTitle = "Current rates",
        screenSubTitle = "Code: PLN",
        isBackAvailable = true
    )
}