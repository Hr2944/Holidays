package com.hrb.holidays.commons.interactors.holidays

import com.hrb.holidays.commons.databases.holidays.IHolidaysTimetableGateway
import com.hrb.holidays.commons.entities.holidays.HolidayPeriod
import java.time.LocalDate

class Holidays(private val holidaysGateway: IHolidaysTimetableGateway) : IHolidays {

    override fun nextHolidaysAfterDate(date: LocalDate): HolidayPeriod {
        val sortedHolidays = sortHolidays(holidaysGateway.fetch().timetable)
        // When there are no holidays after the given date, it means the next holidays are the first
        // of the list plus one year (assuming the list is sorted and holidays are periodic)
        val nextHolidays = sortedHolidays.firstOrNull { it.fromDate.isAfter(date) }
        return if (nextHolidays == null) {
            val firstHoliday = sortedHolidays.first()
            firstHoliday.copy(
                fromDate = firstHoliday.fromDate.plusYears(1),
                toDate = firstHoliday.toDate.plusYears(1)
            )
        } else {
            nextHolidays
        }
    }

    override fun getHolidaysAtDate(date: LocalDate): HolidayPeriod? {
        for (holidays in holidaysGateway.fetch().timetable) {
            if (date.isAfter(holidays.fromDate) && date.isBefore(holidays.toDate)) {
                return holidays
            }
        }
        return null
    }

    private fun sortHolidays(holidays: Set<HolidayPeriod>): Array<HolidayPeriod> {
        return holidays.sortedBy { it.fromDate }
            .toTypedArray()
    }
}
