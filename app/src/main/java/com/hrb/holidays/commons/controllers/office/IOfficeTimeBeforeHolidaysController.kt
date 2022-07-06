package com.hrb.holidays.commons.controllers.office

import com.hrb.holidays.app.presenters.office.ProgressInDatesRange
import java.time.Duration

interface IOfficeTimeBeforeHolidaysController {
    fun getTimeBeforeNextHolidays(): Duration?
    fun getTimeProgressBeforeNextHolidays(): ProgressInDatesRange?
}
