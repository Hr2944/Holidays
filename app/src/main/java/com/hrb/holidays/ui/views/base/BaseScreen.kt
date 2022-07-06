package com.hrb.holidays.ui.views.base

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.hrb.holidays.app.presenters.office.OfficeTimeBeforeHolidaysScreenPresenter
import com.hrb.holidays.ui.layouts.*
import com.hrb.holidays.ui.views.settings.SettingsScreenActivity
import com.hrb.holidays.ui.views.timer.FloatingPauseButton
import com.hrb.holidays.ui.views.timer.TimerScreen
import kotlinx.coroutines.launch
import org.koin.androidx.compose.getViewModel

@ExperimentalMaterialApi
@Composable
private fun onCloseBottomDrawer(drawerState: BottomDrawerState, block: () -> Unit) {
    var lastKnownDrawerValue by rememberSaveable { mutableStateOf(BottomDrawerValue.Closed) }
    if (drawerState.currentValue != lastKnownDrawerValue) {
        // Drawer was open and now it's closed, meaning the calendars were probably updated
        if (
            (lastKnownDrawerValue == BottomDrawerValue.Open ||
                    lastKnownDrawerValue == BottomDrawerValue.Expanded)
            && drawerState.currentValue == BottomDrawerValue.Closed
        ) {
            block()
        }
        @Suppress("UNUSED_VALUE")
        lastKnownDrawerValue = drawerState.currentValue
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun BaseScreenActivity(
    timerPresenter: OfficeTimeBeforeHolidaysScreenPresenter = getViewModel(),
) {
    val scope = rememberCoroutineScope()
    val bottomDrawerState = rememberBottomDrawerState(initialValue = BottomDrawerValue.Closed)
    var drawerType by rememberSaveable { mutableStateOf(CalendarScreens.WEEK_CALENDAR_EDITOR) }
    val isPaused = timerPresenter.uiState.isPaused
    val remainingTime = timerPresenter.uiState.remainingTime
    val progress = timerPresenter.uiState.progressInRemainingTime
    val sideBackdropState = rememberSideBackdropScaffoldState(SideBackdropValue.Concealed)

    onCloseBottomDrawer(drawerState = bottomDrawerState) {
        timerPresenter.updateRemainingTime()
        timerPresenter.updateProgressInRemainingTime()
    }

    LaunchedEffect(remainingTime) {
        if (!isPaused) {
            timerPresenter.updateRemainingTime()
        }
    }

    LaunchedEffect(progress) {
        if (!isPaused) {
            timerPresenter.updateProgressInRemainingTime()
        }
    }

    BaseScreen(
        bottomDrawerState = bottomDrawerState,
        bottomDrawerContent = { GetCalendarScreen(type = drawerType) },
        isPaused = isPaused,
        onLaunchTimer = { timerPresenter.setPause(false); timerPresenter.updateRemainingTime() },
        onPauseTimer = { timerPresenter.setPause(true) },
        onOpenWeekCalendar = {
            drawerType = CalendarScreens.WEEK_CALENDAR_EDITOR
            scope.launch { bottomDrawerState.open() }
        },
        onOpenHolidaysCalendar = {
            drawerType = CalendarScreens.HOLIDAYS_CALENDAR_EDITOR
            scope.launch { bottomDrawerState.open() }
        },
        mainContent = {
            TimerScreen(remainingTime = remainingTime, progress = progress)
        },
        sideDrawerContent = {
            SettingsScreenActivity()
        },
        sideBackdropState = sideBackdropState
    )
}

@ExperimentalMaterialApi
@Composable
fun BaseScreen(
    bottomDrawerState: BottomDrawerState,
    bottomDrawerContent: @Composable ColumnScope.() -> Unit,
    isPaused: Boolean,
    onLaunchTimer: () -> Unit,
    onPauseTimer: () -> Unit,
    onOpenWeekCalendar: () -> Unit,
    onOpenHolidaysCalendar: () -> Unit,
    mainContent: @Composable BoxScope.() -> Unit,
    sideDrawerContent: @Composable () -> Unit,
    sideBackdropState: SideBackdropScaffoldState
) {
    BaseLayout(
        bottomDrawerState = bottomDrawerState,
        bottomDrawerContent = bottomDrawerContent,
        bottomContent = BottomContent(
            isFloatingActionButtonDocked = true,
            floatingActionButtonPosition = FabPosition.Center,
            floatingActionButton = {
                FloatingPauseButton(
                    isPaused = isPaused,
                    onLaunchTimer = onLaunchTimer,
                    onPauseTimer = onPauseTimer
                )
            },
            bottomBar = {
                BottomBar(
                    onOpenWeekCalendar = onOpenWeekCalendar,
                    onOpenHolidaysCalendar = onOpenHolidaysCalendar
                )
            }
        ),
        mainContent = mainContent,
        sideBackdropState = sideBackdropState,
        sideDrawerContent = sideDrawerContent
    )
}

@ExperimentalMaterialApi
@Composable
fun BaseLayout(
    mainContent: @Composable BoxScope.() -> Unit,
    bottomContent: BottomContent,
    bottomDrawerContent: @Composable ColumnScope.() -> Unit,
    bottomDrawerState: BottomDrawerState,
    sideDrawerContent: @Composable () -> Unit,
    sideBackdropState: SideBackdropScaffoldState
) {
    SideBackdrop(
        scaffoldState = sideBackdropState,
        directions = setOf(RevealDirection.Left),
        backLayerContent = sideDrawerContent,
        frontLayerContent = {
            BottomDrawer(
                drawerState = bottomDrawerState,
                drawerContent = bottomDrawerContent,
                gesturesEnabled = bottomDrawerState.isOpen,
                drawerShape = RoundedCornerShape(32.dp, 32.dp, 0.dp, 0.dp),
                content = {
                    Scaffold(
                        floatingActionButton = bottomContent.floatingActionButton,
                        floatingActionButtonPosition = bottomContent.floatingActionButtonPosition,
                        isFloatingActionButtonDocked = bottomContent.isFloatingActionButtonDocked,
                        bottomBar = bottomContent.bottomBar
                    ) { innerPadding ->
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(innerPadding),
                            content = mainContent
                        )
                    }
                }
            )
        }
    )
}
