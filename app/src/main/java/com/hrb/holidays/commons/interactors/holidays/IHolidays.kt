package com.hrb.holidays.commons.interactors.holidays

import com.hrb.holidays.commons.entities.holidays.HolidayPeriod
import java.time.LocalDate

interface IHolidays {
    fun nextAfter(date: LocalDate): HolidayPeriod?
    fun previousBefore(date: LocalDate): HolidayPeriod?
    fun at(date: LocalDate): HolidayPeriod?
}
