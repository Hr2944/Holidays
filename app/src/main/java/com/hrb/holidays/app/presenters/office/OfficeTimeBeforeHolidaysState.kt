package com.hrb.holidays.app.presenters.office

data class OfficeTimeBeforeHolidaysState(
    var remainingTime: RemainingTime?,
    var progressInRemainingTime: ProgressInDatesRange?,
    var isPaused: Boolean
)
