package com.hrb.holidays.commons.presenters.office

data class RemainingTime(
    val days: Int,
    val hours: Int,
    val minutes: Int,
    val seconds: Int,
    val millis: Int
) {

    fun asSeconds(): Long {
        return days * 24 * 60 * 60 + hours * 60 * 60 + minutes * 60 + seconds + ((millis).toLong() / 1000)
    }

    fun asSecondsString(): String {
        return String.format("%,d", asSeconds())
    }

    fun millisToString(): String {
        return String.format("%03d", millis)
    }

    fun secondsToString(): String {
        return String.format("%02d", seconds)
    }

    fun minutesToString(): String {
        return String.format("%02d", minutes)
    }

    fun hoursToString(): String {
        return String.format("%02d", hours)
    }

    fun daysToString(): String {
        return String.format("%02d", days)
    }

    private fun isAllPropertiesEqualTo(
        days: Int? = null,
        hours: Int? = null,
        minutes: Int? = null,
        seconds: Int? = null,
        millis: Int? = null
    ): Boolean {
        return ((this.days == days) and
                (this.hours == hours) and
                (this.minutes == minutes) and
                (this.seconds == seconds) and
                (this.millis == millis))
    }

    override fun toString(): String {
        return "${daysToString()}d:${hoursToString()}h:${minutesToString()}mn:${secondsToString()}.${millisToString()}s"
    }

    override fun equals(other: Any?): Boolean {
        return if (other is RemainingTime) {
            other.isAllPropertiesEqualTo(
                this.days,
                this.hours,
                this.minutes,
                this.seconds,
                this.millis
            )
        } else false
    }

    override fun hashCode(): Int {
        var result = days
        result = 31 * result + hours
        result = 31 * result + minutes
        result = 31 * result + seconds
        result = 31 * result + millis
        return result
    }
}
