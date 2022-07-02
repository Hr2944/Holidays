package com.hrb.holidays.commons.controllers.office

import com.hrb.holidays.commons.entities.holidays.HolidayPeriod
import com.hrb.holidays.commons.interactors.holidays.IHolidays
import com.hrb.holidays.commons.interactors.office.IRangeOfficeTimeCalculator
import java.time.Duration
import java.time.LocalDate
import java.time.LocalDateTime

class OfficeTimeBeforeHolidaysController(
    private val officeCalculator: IRangeOfficeTimeCalculator,
    private val holidays: IHolidays,
) : IOfficeTimeBeforeHolidaysController {
    override fun getTimeBeforeNextHolidays(): Duration {
        val now = LocalDateTime.now()
        return this.officeCalculator.calculate(
            from = getStartingDate(now),
            to = getEndingDate(now.toLocalDate())
        )
    }

    private fun getStartingDate(now: LocalDateTime): LocalDateTime {
        val potentialCurrentHolidays = holidays.getHolidaysAtDate(now.toLocalDate())

        return if (potentialCurrentHolidays is HolidayPeriod) {
            potentialCurrentHolidays.toDate.atStartOfDay()
        } else {
            now
        }
    }

    private fun getEndingDate(now: LocalDate): LocalDateTime {
        return this.holidays.nextHolidaysAfterDate(now).fromDate.atStartOfDay()
    }
}
