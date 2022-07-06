package com.hrb.holidays.app.presenters.office

import java.time.Duration


class RemainingTimeFactory {
    companion object {
        fun fromDuration(duration: Duration): RemainingTime {
            val days = duration.toDays()
            val hours = duration.toHours() % 24
            val minutes = duration.toMinutes() % 60
            val seconds = duration.seconds % 60
            val millis = duration.toMillis() % 1000

            return RemainingTime(
                days.toInt(),
                hours.toInt(),
                minutes.toInt(),
                seconds.toInt(),
                millis.toInt()
            )
        }
    }
}
