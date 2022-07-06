package com.hrb.holidays.ui.views.timer

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.hrb.holidays.app.presenters.office.RemainingTime

@Composable
fun MessageTimer(message: String, subtitle: String, modifier: Modifier) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
    ) {
        Text(
            text = message,
            modifier = Modifier
                .border(
                    width = 1.dp,
                    shape = MaterialTheme.shapes.medium,
                    color = MaterialTheme.colors.onSurface
                )
                .padding(16.dp)
                .fillMaxWidth(),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.h5.copy(fontWeight = FontWeight.Bold)
        )
        Text(
            text = subtitle,
            style = MaterialTheme.typography.caption,
            modifier = Modifier.padding(top = 8.dp)
        )
    }
}

@Composable
fun Timer(
    remainingTime: RemainingTime,
    modifier: Modifier = Modifier
) {
    MessageTimer(
        modifier = modifier,
        message = remainingTime.toString(),
        subtitle = "For a total of ${remainingTime.asSecondsString()} seconds"
    )
}
