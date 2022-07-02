package com.hrb.holidays.ui.views.base

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.hrb.holidays.ui.views.timer.FloatingPauseButton


@ExperimentalMaterialApi
@Composable
fun BaseScreen(
    drawerState: BottomDrawerState,
    drawerContent: @Composable ColumnScope.() -> Unit,
    isPaused: Boolean,
    onLaunchTimer: () -> Unit,
    onPauseTimer: () -> Unit,
    onOpenWeekCalendar: () -> Unit,
    onOpenHolidaysCalendar: () -> Unit,
    content: @Composable BoxScope.() -> Unit
) {
    BaseLayout(
        drawerState = drawerState,
        drawerContent = drawerContent,
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
        content = content
    )
}

@ExperimentalMaterialApi
@Composable
fun BaseLayout(
    content: @Composable BoxScope.() -> Unit,
    bottomContent: BottomContent,
    drawerContent: @Composable ColumnScope.() -> Unit,
    drawerState: BottomDrawerState
) {
    BottomDrawer(
        drawerState = drawerState,
        drawerContent = drawerContent,
        gesturesEnabled = drawerState.isOpen,
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
                    content = content
                )
            }
        }
    )
}
