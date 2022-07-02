package com.hrb.holidays.commons.controllers.holidays

import com.hrb.holidays.commons.entities.holidays.HolidayPeriod
import com.hrb.holidays.commons.entities.holidays.HolidaysTimetable

interface IHolidaysTimetableController {
    fun getTimetable(): HolidaysTimetable
    suspend fun deleteHoliday(holiday: HolidayPeriod): HolidaysTimetable
    suspend fun addHoliday(holiday: HolidayPeriod): HolidaysTimetable
}
