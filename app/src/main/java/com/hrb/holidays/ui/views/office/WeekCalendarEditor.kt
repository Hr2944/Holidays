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
import com.hrb.holidays.commons.entities.office.OfficeDay
import com.hrb.holidays.app.presenters.office.OfficeWeekScreenPresenter
import com.hrb.holidays.ui.modifiers.frame
import com.hrb.holidays.ui.views.base.DrawerTopBar
import com.vanpra.composematerialdialogs.MaterialDialog
import com.vanpra.composematerialdialogs.datetime.time.timepicker
import com.vanpra.composematerialdialogs.rememberMaterialDialogState
import kotlinx.coroutines.launch
import org.koin.androidx.compose.getViewModel
import java.time.DayOfWeek
import java.time.LocalTime

@Composable
fun WeekCalendarEditorScreenActivity(
    controller: OfficeWeekScreenPresenter = getViewModel()
) {
    val scope = rememberCoroutineScope()
    WeekCalendarEditorScreen(
        days = controller.uiState.weekDays,
        onChangeTime = { startTime: LocalTime, endTime: LocalTime, day: DayOfWeek ->
            scope.launch {
                controller.updateTimeForDay(
                    startTime,
                    endTime,
                    day
                )
            }
        }
    )
}

@Composable
fun WeekCalendarEditorScreen(
    days: Set<OfficeDay>,
    onChangeTime: (startTime: LocalTime, endTime: LocalTime, day: DayOfWeek) -> Unit,
) {
    DrawerTopBar()
    WeekCalendarEditor(days, onChangeTime)
}

@Composable
fun WeekCalendarEditor(
    days: Set<OfficeDay>,
    onChangeTime: (startTime: LocalTime, endTime: LocalTime, day: DayOfWeek) -> Unit,
    daysPerRow: Int = 3
) {
    Column {

        val nbOfRows =
            if (days.size % daysPerRow == 0) (days.size / daysPerRow)
            else (days.size / daysPerRow + 1)

        for (rowIndex in 0 until nbOfRows) {
            Row(
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxWidth()
            ) {
                val rowSize = minOf(daysPerRow, days.size - rowIndex * daysPerRow)
                for (dayIndex in 0 until daysPerRow) {
                    val day = days.elementAtOrNull(rowIndex * daysPerRow + dayIndex)

                    if (day != null) {
                        Day(
                            modifier = Modifier
                                .fillMaxHeight(1f / (6 - rowIndex))
                                .aspectRatio(1f)
                                .frame(
                                    drawLines = getFrameLinesForPlaceInRow(
                                        rowSize = rowSize,
                                        placeInRow = dayIndex + 1,
                                    ).toTypedArray()
                                ),
                            day = day.dayOfWeek,
                            startTime = day.startAt,
                            endTime = day.endAt,
                            onChangeTime = onChangeTime
                        )
                    }
                }
            }
        }
    }
}

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

    Column(
        modifier = modifier.clickable { startTimePickerDialogState.show() },
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
        text = when (day) {
            DayOfWeek.MONDAY -> "M"
            DayOfWeek.TUESDAY -> "T"
            DayOfWeek.WEDNESDAY -> "W"
            DayOfWeek.THURSDAY -> "T"
            DayOfWeek.FRIDAY -> "F"
            DayOfWeek.SATURDAY -> "S"
            DayOfWeek.SUNDAY -> "S"
        },
        style = MaterialTheme.typography.subtitle1,
        textAlign = TextAlign.Center,
        modifier = modifier
            .aspectRatio(1f)
            .drawBehind {
                drawCircle(color = color, style = Stroke(3f))
            }
    )
}
