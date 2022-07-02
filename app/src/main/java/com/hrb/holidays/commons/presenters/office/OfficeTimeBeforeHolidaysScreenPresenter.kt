package com.hrb.holidays.commons.presenters.office

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.hrb.holidays.commons.controllers.office.IOfficeTimeBeforeHolidaysController
import java.time.Duration


class OfficeTimeBeforeHolidaysScreenPresenter(
    private val controller: IOfficeTimeBeforeHolidaysController
) : ViewModel() {

    var uiState: OfficeTimeBeforeHolidaysState by mutableStateOf(initState())
        private set

    fun updateRemainingTime() {
        this.updateUiState(
            remainingTime = this.getRemainingTime()
        )
    }

    private fun initState(): OfficeTimeBeforeHolidaysState {
        return OfficeTimeBeforeHolidaysState(
            remainingTime = this.getRemainingTime()
        )
    }

    private fun getRemainingTime(): RemainingTime {
        return RemainingTimeFactory.fromDuration(this.getRawRemainingTime())
    }

    private fun getRawRemainingTime(): Duration {
        return this.controller.getTimeBeforeNextHolidays()
    }

    private fun updateUiState(
        remainingTime: RemainingTime = uiState.remainingTime
    ) {
        if (remainingTime != uiState.remainingTime) {
            this.uiState = this.uiState.copy(
                remainingTime = remainingTime
            )
        }
    }
}
