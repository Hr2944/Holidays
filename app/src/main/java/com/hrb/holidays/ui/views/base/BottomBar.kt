package com.hrb.holidays.ui.views.base

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material.BottomAppBar
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.CalendarToday
import androidx.compose.material.icons.rounded.WbSunny
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun BottomBar(onOpenWeekCalendar: () -> Unit, onOpenHolidaysCalendar: () -> Unit) {
    BottomAppBar(
        cutoutShape = MaterialTheme.shapes.small.copy(
            CornerSize(percent = 50)
        )
    ) {
        Row(
            Modifier
                .fillMaxWidth()
                .selectableGroup(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            BottomNavigationItem(selected = false, onClick = onOpenWeekCalendar, icon = {
                Icon(
                    Icons.Rounded.CalendarToday,
                    "Edit week calendar"
                )
            })
            BottomNavigationItem(selected = false, onClick = onOpenHolidaysCalendar, icon = {
                Icon(
                    Icons.Rounded.WbSunny,
                    "Edit holidays calendar"
                )
            })
        }
    }
}
