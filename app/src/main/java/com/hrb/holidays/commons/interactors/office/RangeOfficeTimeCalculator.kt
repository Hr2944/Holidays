package com.hrb.holidays.commons.interactors.office

import com.hrb.holidays.commons.databases.office.IOfficeWeekGateway
import com.hrb.holidays.commons.entities.office.OfficeDay
import com.hrb.holidays.commons.entities.office.OfficeWeek
import java.time.Duration
import java.time.LocalDateTime
import java.time.LocalTime


class RangeOfficeTimeCalculator(
    private val officeWeekGateway: IOfficeWeekGateway
) : IRangeOfficeTimeCalculator {

    override fun calculate(from: LocalDateTime, to: LocalDateTime): Duration {
        var remainingTime = Duration.ZERO
        var currentEvaluatedDay = from

        if (isOfficeDayStarted(currentEvaluatedDay)) {
            remainingTime += getPendingOfficeTime(currentEvaluatedDay)
            currentEvaluatedDay = currentEvaluatedDay.plusDays(1).with(LocalTime.MIDNIGHT)
        }

        while (currentEvaluatedDay.isBefore(to) or currentEvaluatedDay.isEqual(to)) {
            remainingTime += getOfficeTimeForDay(currentEvaluatedDay)
            currentEvaluatedDay = currentEvaluatedDay.plusDays(1)
        }

        return remainingTime
    }

    private fun getOfficeDay(day: LocalDateTime, officeWeek: OfficeWeek): OfficeDay {
        for (weekDay in officeWeek.week) {
            if (weekDay.dayOfWeek == day.dayOfWeek) {
                return weekDay
            }
        }
        throw RuntimeException("$day not recognised as a week day")
    }

    private fun getOfficeTimeForDay(day: LocalDateTime): Duration {
        return getOfficeDay(day, officeWeekGateway.fetch()).officeTime()
    }

    private fun getPendingOfficeTime(day: LocalDateTime): Duration {
        val officeDay = getOfficeDay(day, officeWeekGateway.fetch())
        return Duration.between(day.toLocalTime(), officeDay.endAt)
    }

    private fun isOfficeDayStarted(day: LocalDateTime): Boolean {
        val officeDay = getOfficeDay(day, officeWeekGateway.fetch())
        return day.toLocalTime().isBetween(officeDay.startAt, officeDay.endAt)
    }

    private fun LocalTime.isBetween(from: LocalTime, to: LocalTime): Boolean {
        return this.isAfter(from) && this.isBefore(to)
    }
}
