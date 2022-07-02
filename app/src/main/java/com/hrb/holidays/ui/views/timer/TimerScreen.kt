package com.hrb.holidays.ui.views.timer

import androidx.compose.foundation.layout.*
import androidx.compose.material.BottomDrawerState
import androidx.compose.material.BottomDrawerValue
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.rememberBottomDrawerState
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.hrb.holidays.commons.presenters.office.OfficeTimeBeforeHolidaysScreenPresenter
import com.hrb.holidays.ui.views.base.BaseScreen
import com.hrb.holidays.ui.views.base.CalendarScreens
import com.hrb.holidays.ui.views.base.GetCalendarScreen
import kotlinx.coroutines.launch
import org.koin.androidx.compose.getViewModel

@ExperimentalMaterialApi
@Composable
private fun onCloseDrawer(drawerState: BottomDrawerState, block: () -> Unit) {
    var lastKnownDrawerValue by rememberSaveable { mutableStateOf(BottomDrawerValue.Closed) }
    if (drawerState.currentValue != lastKnownDrawerValue) {
        // Drawer was open and now it's closed, meaning the calendars were likely updated
        if (
            (lastKnownDrawerValue == BottomDrawerValue.Open ||
                    lastKnownDrawerValue == BottomDrawerValue.Expanded)
            && drawerState.currentValue == BottomDrawerValue.Closed
        ) {
            block()
        }
        lastKnownDrawerValue = drawerState.currentValue
    }
}

@ExperimentalMaterialApi
@Composable
fun TimerScreenActivity(
    presenter: OfficeTimeBeforeHolidaysScreenPresenter = getViewModel()
) {
    var isPaused by rememberSaveable { mutableStateOf(false) }
    val remainingTime = presenter.uiState.remainingTime

    val scope = rememberCoroutineScope()
    val drawerState = rememberBottomDrawerState(initialValue = BottomDrawerValue.Closed)
    var drawerType by rememberSaveable { mutableStateOf(CalendarScreens.WEEK_CALENDAR_EDITOR) }

    onCloseDrawer(drawerState = drawerState) {
        presenter.updateRemainingTime()
    }

    LaunchedEffect(remainingTime) {
        if (!isPaused) {
            presenter.updateRemainingTime()
        }
    }

    BaseScreen(
        isPaused = isPaused,

        drawerState = drawerState,
        drawerContent = { GetCalendarScreen(type = drawerType) },

        onOpenWeekCalendar = {
            drawerType = CalendarScreens.WEEK_CALENDAR_EDITOR
            scope.launch { drawerState.open() }
        },
        onOpenHolidaysCalendar = {
            drawerType = CalendarScreens.HOLIDAYS_CALENDAR_EDITOR
            scope.launch { drawerState.open() }
        },

        onLaunchTimer = { isPaused = false; presenter.updateRemainingTime() },
        onPauseTimer = { isPaused = true },
    ) {
        Column(
            modifier = Modifier.padding(13.dp),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            TimerProgression(Modifier.fillMaxWidth())
            Timer(remainingTime = remainingTime, modifier = Modifier.fillMaxHeight())
        }
    }
}
