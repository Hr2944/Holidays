package com.hrb.holidays.ui.views.office

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.vanpra.composematerialdialogs.MaterialDialog
import com.vanpra.composematerialdialogs.datetime.time.timepicker
import com.vanpra.composematerialdialogs.rememberMaterialDialogState
import java.time.DayOfWeek
import java.time.LocalTime


@Composable
fun Day(
    modifier: Modifier = Modifier,
    day: DayOfWeek,
    startTime: LocalTime,
    endTime: LocalTime,
    onChangeTime: (startTime: LocalTime, endTime: LocalTime, day: DayOfWeek) -> Unit,
) {
    val startTimePickerDialogState = rememberMaterialDialogState()
    val endTimePickerDialogState = rememberMaterialDialogState()

    var tempStartTime by remember { mutableStateOf(LocalTime.MIN) }

    MaterialDialog(
        dialogState = startTimePickerDialogState,
        buttons = {
            positiveButton("OK")
            negativeButton("CANCEL")
        }
    ) {
        timepicker(is24HourClock = true, title = "Select Start Time") { selectedStartTime ->
            tempStartTime = selectedStartTime
            endTimePickerDialogState.show()
        }
    }
    MaterialDialog(
        dialogState = endTimePickerDialogState,
        buttons = {
            positiveButton("OK")
            negativeButton("CANCEL")
        }
    ) {
        timepicker(is24HourClock = true, title = "Select End Time") { selectedEndTime ->
            if (selectedEndTime.isAfter(tempStartTime)) {
                onChangeTime(tempStartTime, selectedEndTime, day)
            } else {
                onChangeTime(selectedEndTime, tempStartTime, day)
            }
        }
    }

    Day(
        modifier = modifier,
        day = day,
        startTime = startTime,
        endTime = endTime,
        onClick = {
            startTimePickerDialogState.show()
        }
    )
}

@Composable
fun Day(
    modifier: Modifier = Modifier,
    day: DayOfWeek,
    startTime: LocalTime,
    endTime: LocalTime,
    onClick: () -> Unit
) {
    Column(
        modifier = modifier.clickable { onClick() },
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceEvenly,
    ) {
        DayIcon(day, modifier = Modifier.height(23.dp))
        Row(
            modifier = Modifier
                .padding(6.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Text(startTime.toString(), style = MaterialTheme.typography.body2)
            Text(text = " - ", style = MaterialTheme.typography.body2)
            Text(endTime.toString(), style = MaterialTheme.typography.body2)
        }
    }
}

@Composable
fun DayIcon(day: DayOfWeek, modifier: Modifier = Modifier) {
    val color = MaterialTheme.colors.onSurface

    Text(
        text = day.name.substring(0, 1).uppercase(),
        style = MaterialTheme.typography.subtitle1,
        textAlign = TextAlign.Center,
        modifier = modifier
            .drawBehind {
                drawCircle(color = color, style = Stroke(3f), radius = size.maxDimension / 2f)
            }
    )
}
