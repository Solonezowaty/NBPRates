package com.solonezowaty.core.presentation.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.solonezowaty.core.R

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
            Text(text = stringResource(R.string.retry_button_label))
        }
        Spacer(modifier = Modifier.height(2.dp))
        Text(
            text = errorMessage ?: stringResource(R.string.error_message_text)
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