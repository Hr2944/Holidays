package com.hrb.holidays.commons.controllers.office

import java.time.Duration

interface IOfficeTimeBeforeHolidaysController {
    fun getTimeBeforeNextHolidays(): Duration
}
