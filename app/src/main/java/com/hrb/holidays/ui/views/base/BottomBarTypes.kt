package com.hrb.holidays.ui.views.base

import androidx.compose.material.FabPosition
import androidx.compose.runtime.Composable

data class BottomContent(
    val bottomBar: @Composable () -> Unit = {},
    val floatingActionButton: @Composable () -> Unit = {},
    val floatingActionButtonPosition: FabPosition = FabPosition.End,
    val isFloatingActionButtonDocked: Boolean = false
)
