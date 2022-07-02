package com.hrb.holidays.ui.views.base

import androidx.compose.runtime.Composable
import com.hrb.holidays.ui.views.holidays.HolidaysCalendarEditorScreenActivity
import com.hrb.holidays.ui.views.office.WeekCalendarEditorScreenActivity

@Composable
fun GetCalendarScreen(type: CalendarScreens) {
    return when (type) {
        CalendarScreens.WEEK_CALENDAR_EDITOR -> {
            WeekCalendarEditorScreenActivity()
        }

        CalendarScreens.HOLIDAYS_CALENDAR_EDITOR -> {
            HolidaysCalendarEditorScreenActivity()
        }
    }
}
