package com.hrb.holidays.app.databases.office

import android.content.Context
import androidx.datastore.core.CorruptionException
import androidx.datastore.core.DataStore
import androidx.datastore.core.Serializer
import androidx.datastore.core.handlers.ReplaceFileCorruptionHandler
import androidx.datastore.dataStore
import com.google.protobuf.InvalidProtocolBufferException
import com.hrb.holidays.app.databases.proto.*
import java.io.InputStream
import java.io.OutputStream

internal object OfficeWeekSerializer : Serializer<OfficeWeekProto> {
    override val defaultValue: OfficeWeekProto = initOfficeWeek()

    override suspend fun readFrom(input: InputStream): OfficeWeekProto {
        try {
            return OfficeWeekProto.parseFrom(input)
        } catch (exception: InvalidProtocolBufferException) {
            throw CorruptionException("Cannot read proto.", exception)
        }
    }

    override suspend fun writeTo(t: OfficeWeekProto, output: OutputStream) = t.writeTo(output)

    private fun initOfficeWeek(): OfficeWeekProto {
        val nineHour = localTimeProto {
            hours = 9
            minutes = 10
            seconds = 0
            nanos = 0
        }
        val heightHour = localTimeProto {
            hours = 8
            minutes = 15
            seconds = 0
            nanos = 0
        }
        val seventeenHour = localTimeProto {
            hours = 17
            minutes = 5
            seconds = 0
            nanos = 0
        }
        val sixteenHour = localTimeProto {
            hours = 15
            minutes = 55
            seconds = 0
            nanos = 0
        }
        val thirteenHour = localTimeProto {
            hours = 13
            minutes = 10
            seconds = 0
            nanos = 0
        }
        val noHour = localTimeProto {
            hours = 0
            minutes = 0
            seconds = 0
            nanos = 0
        }

        return officeWeekProto {
            weekDays.addAll(
                listOf(
                    officeDayProto {
                        dayOfWeek = DayOfWeekProto.DAY_OF_WEEK_MONDAY
                        startAt = nineHour
                        endAt = seventeenHour
                    },
                    officeDayProto {
                        dayOfWeek = DayOfWeekProto.DAY_OF_WEEK_TUESDAY
                        startAt = heightHour
                        endAt = sixteenHour
                    },
                    officeDayProto {
                        dayOfWeek = DayOfWeekProto.DAY_OF_WEEK_WEDNESDAY
                        startAt = heightHour
                        endAt = thirteenHour
                    },
                    officeDayProto {
                        dayOfWeek = DayOfWeekProto.DAY_OF_WEEK_THURSDAY
                        startAt = heightHour
                        endAt = sixteenHour
                    },
                    officeDayProto {
                        dayOfWeek = DayOfWeekProto.DAY_OF_WEEK_FRIDAY
                        startAt = nineHour
                        endAt = sixteenHour
                    },
                    officeDayProto {
                        dayOfWeek = DayOfWeekProto.DAY_OF_WEEK_SATURDAY
                        startAt = noHour
                        endAt = noHour
                    },
                    officeDayProto {
                        dayOfWeek = DayOfWeekProto.DAY_OF_WEEK_SUNDAY
                        startAt = noHour
                        endAt = noHour
                    }
                )
            )
        }
    }
}

val Context.officeWeekDataStore: DataStore<OfficeWeekProto> by dataStore(
    fileName = "office.pb",
    serializer = OfficeWeekSerializer,
    corruptionHandler = ReplaceFileCorruptionHandler(
        produceNewData = { OfficeWeekProto.getDefaultInstance() }
    )
)
