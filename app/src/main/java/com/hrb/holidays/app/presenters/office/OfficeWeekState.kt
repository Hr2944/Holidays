package com.hrb.holidays.app.presenters.office

import com.hrb.holidays.commons.entities.office.OfficeDay

data class OfficeWeekState(
    val weekDays: Set<OfficeDay>
)
