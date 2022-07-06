package com.hrb.holidays.app.databases.holidays

import android.content.Context
import androidx.datastore.core.CorruptionException
import androidx.datastore.core.DataStore
import androidx.datastore.core.Serializer
import androidx.datastore.core.handlers.ReplaceFileCorruptionHandler
import androidx.datastore.dataStore
import com.google.protobuf.InvalidProtocolBufferException
import com.hrb.holidays.app.databases.proto.HolidaysTimetableProto
import com.hrb.holidays.app.databases.proto.holidayPeriodProto
import com.hrb.holidays.app.databases.proto.holidaysTimetableProto
import com.hrb.holidays.app.databases.proto.localDateProto
import java.io.InputStream
import java.io.OutputStream

internal object HolidaysTimetableSerializer : Serializer<HolidaysTimetableProto> {
    override val defaultValue: HolidaysTimetableProto = initTimetable()

    private fun initTimetable(): HolidaysTimetableProto {
        return holidaysTimetableProto {
            holidayPeriods.addAll(
                listOf(
                    holidayPeriodProto {
                        name = "vacances de la toussaint"
                        fromDate = localDateProto {
                            year = 2021
                            month = 10
                            day = 23
                        }
                        toDate = localDateProto {
                            year = 2021
                            month = 11
                            day = 8
                        }
                    },
                    holidayPeriodProto {
                        name = "vacances de noel"
                        fromDate = localDateProto {
                            year = 2021
                            month = 12
                            day = 18
                        }
                        toDate = localDateProto {
                            year = 2022
                            month = 1
                            day = 3
                        }
                    },
                    holidayPeriodProto {
                        name = "vacances d'hiver"
                        fromDate = localDateProto {
                            year = 2022
                            month = 2
                            day = 5
                        }
                        toDate = localDateProto {
                            year = 2022
                            month = 2
                            day = 21
                        }
                    },
                    holidayPeriodProto {
                        name = "vacances de printemps"
                        fromDate = localDateProto {
                            year = 2022
                            month = 4
                            day = 9
                        }
                        toDate = localDateProto {
                            year = 2022
                            month = 4
                            day = 25
                        }
                    },
                    holidayPeriodProto {
                        name = "grandes vacances"
                        fromDate = localDateProto {
                            year = 2022
                            month = 7
                            day = 7
                        }
                        toDate = localDateProto {
                            year = 2022
                            month = 9
                            day = 2
                        }
                    }
                )
            )
        }
    }

    override suspend fun readFrom(input: InputStream): HolidaysTimetableProto {
        try {
            return HolidaysTimetableProto.parseFrom(input)
        } catch (exception: InvalidProtocolBufferException) {
            throw CorruptionException("Cannot read proto.", exception)
        }
    }

    override suspend fun writeTo(t: HolidaysTimetableProto, output: OutputStream): Unit =
        t.writeTo(output)
}

val Context.holidaysTimetableDataStore: DataStore<HolidaysTimetableProto> by dataStore(
    fileName = "holidays.pb",
    serializer = HolidaysTimetableSerializer,
    corruptionHandler = ReplaceFileCorruptionHandler(
        produceNewData = { HolidaysTimetableProto.getDefaultInstance() }
    )
)
