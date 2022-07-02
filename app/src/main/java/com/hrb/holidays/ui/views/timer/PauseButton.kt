package com.hrb.holidays.ui.views.timer

import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Pause
import androidx.compose.material.icons.rounded.PlayArrow
import androidx.compose.runtime.Composable


@Composable
fun FloatingPauseButton(isPaused: Boolean, onLaunchTimer: () -> Unit, onPauseTimer: () -> Unit) {
    FloatingActionButton(onClick = {
        if (isPaused) {
            onLaunchTimer()
        } else {
            onPauseTimer()
        }
    }) {
        Icon(
            if (isPaused) Icons.Rounded.PlayArrow else Icons.Rounded.Pause,
            if (isPaused) "Start" else "Stop"
        )
    }
}
