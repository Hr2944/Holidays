package com.hrb.holidays.app.presenters.office

import java.time.LocalDate
import java.time.LocalDateTime

data class ProgressInDatesRange(
    val fromDate: LocalDate,
    val toDate: LocalDate,
    val atTime: LocalDateTime,
    val progress: Float
)
