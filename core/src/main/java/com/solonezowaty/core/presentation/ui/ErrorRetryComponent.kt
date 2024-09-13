package com.solonezowaty.core.presentation.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun ErrorRetryComponent(
    modifier: Modifier = Modifier,
    errorMessage: String? = null,
    onRetryClicked: () -> Unit
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(
            onClick = {
                onRetryClicked()
            }
        ) {
            Text(text = "Elo elo ")
        }
        Spacer(modifier = Modifier.height(2.dp))
        Text(
            text = "trzy dwa zero"
        )
    }
}

@Preview
@Composable
private fun ErrorRetryComponentPreview() {
    ErrorRetryComponent(
        onRetryClicked = { }
    )
}