package com.hrb.holidays.ui.views.timer

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp

@Composable
fun TimerProgression(modifier: Modifier = Modifier) {
    Column(modifier, horizontalAlignment = Alignment.CenterHorizontally) {
        ProgressionBar(.5f, modifier = Modifier.fillMaxWidth(.95f))
        Spacer(modifier = Modifier.height(8.dp))
        Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()) {
            Text(text = "01/01", style = MaterialTheme.typography.caption)
            Text(text = "31/12", style = MaterialTheme.typography.caption)
        }
    }
}

@Composable
fun ProgressionBar(progress: Float, modifier: Modifier = Modifier) {
    LinearProgressIndicator(
        progress = progress,
        modifier = modifier.clip(shape = RoundedCornerShape(8.dp))
    )
}
