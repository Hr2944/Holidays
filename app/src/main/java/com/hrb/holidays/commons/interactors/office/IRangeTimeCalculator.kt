package com.hrb.holidays.commons.interactors.office

import java.time.Duration
import java.time.LocalDateTime

interface IRangeTimeCalculator {
    fun calculate(from: LocalDateTime, to: LocalDateTime): Duration
}
