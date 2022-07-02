package com.hrb.holidays.commons.databases.proto

import com.hrb.holidays.proto.day.DayOfWeek
import com.hrb.holidays.proto.holiday.*
import com.hrb.holidays.proto.office.*
import com.hrb.holidays.proto.time.*


inline fun officeWeekProto(block: OfficeWeekKt.Dsl.() -> Unit): OfficeWeekProto = officeWeek(block)
typealias OfficeWeekProto = OfficeWeek

inline fun officeDayProto(block: OfficeDayKt.Dsl.() -> Unit): OfficeDayProto = officeDay(block)
typealias OfficeDayProto = OfficeDay

typealias DayOfWeekProto = DayOfWeek

inline fun localTimeProto(block: LocalTimeKt.Dsl.() -> Unit): LocalTimeProto = localTime(block)
typealias LocalTimeProto = LocalTime

inline fun holidayPeriodProto(block: HolidayPeriodKt.Dsl.() -> Unit): HolidayPeriodProto =
    holidayPeriod(block)
typealias HolidayPeriodProto = HolidayPeriod

inline fun holidaysTimetableProto(block: HolidaysTimetableKt.Dsl.() -> Unit): HolidaysTimetableProto =
    holidaysTimetable(block)
typealias HolidaysTimetableProto = HolidaysTimetable

inline fun localDateProto(block: LocalDateKt.Dsl.() -> Unit): LocalDateProto = localDate(block)
typealias LocalDateProto = LocalDate
