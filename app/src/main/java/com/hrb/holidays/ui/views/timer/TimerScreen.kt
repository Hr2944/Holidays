package com.hrb.holidays.ui.views.timer

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.hrb.holidays.app.presenters.office.ProgressInDatesRange
import com.hrb.holidays.app.presenters.office.RemainingTime
import java.time.LocalDate


@Composable
fun TimerScreen(
    progress: ProgressInDatesRange?,
    remainingTime: RemainingTime?
) {
    Column(
        modifier = Modifier.padding(13.dp),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        TimerProgression(
            progress = progress?.progress ?: 0f,
            modifier = Modifier.fillMaxWidth(),
            fromDate = progress?.fromDate ?: LocalDate.of(1, 1, 1),
            toDate = progress?.toDate ?: LocalDate.of(1, 1, 1)
        )
        if (remainingTime != null) {
            Timer(remainingTime = remainingTime, modifier = Modifier.fillMaxHeight())
        } else {
            MessageTimer(
                message = "No next holidays set",
                subtitle = "Add more via the holidays panel",
                modifier = Modifier.fillMaxHeight()
            )
        }
    }
}
