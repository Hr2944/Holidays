package com.hrb.holidays.commons.controllers.office

import com.hrb.holidays.commons.entities.office.OfficeDay
import com.hrb.holidays.commons.entities.office.OfficeWeek

interface IOfficeWeekController {
    fun getWeek(): OfficeWeek
    suspend fun updateDay(day: OfficeDay): OfficeWeek
}
