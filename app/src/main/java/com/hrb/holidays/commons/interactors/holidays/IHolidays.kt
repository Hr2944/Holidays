package com.hrb.holidays.commons.interactors.holidays

import com.hrb.holidays.commons.entities.holidays.HolidayPeriod
import java.time.LocalDate

interface IHolidays {
    fun nextHolidaysAfterDate(date: LocalDate): HolidayPeriod
    fun getHolidaysAtDate(date: LocalDate): HolidayPeriod?
}
