package com.hrb.holidays.commons.presenters.holidays

import com.hrb.holidays.commons.entities.holidays.HolidayPeriod

data class HolidaysTimetableState(
    val holidays: MutableSet<HolidayPeriod>
)
