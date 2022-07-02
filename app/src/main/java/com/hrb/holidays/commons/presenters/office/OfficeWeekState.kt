package com.hrb.holidays.commons.presenters.office

import com.hrb.holidays.commons.entities.office.OfficeDay

data class OfficeWeekState(
    val weekDays: Set<OfficeDay>
)
