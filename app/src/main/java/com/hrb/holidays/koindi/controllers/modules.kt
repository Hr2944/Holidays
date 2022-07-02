package com.hrb.holidays.koindi.controllers

import com.hrb.holidays.commons.controllers.holidays.HolidaysTimetableController
import com.hrb.holidays.commons.controllers.holidays.IHolidaysTimetableController
import com.hrb.holidays.commons.controllers.office.IOfficeTimeBeforeHolidaysController
import com.hrb.holidays.commons.controllers.office.IOfficeWeekController
import com.hrb.holidays.commons.controllers.office.OfficeTimeBeforeHolidaysController
import com.hrb.holidays.commons.controllers.office.OfficeWeekController
import org.koin.dsl.module


val controllersModule = module {
    single<IOfficeTimeBeforeHolidaysController> {
        OfficeTimeBeforeHolidaysController(
            get(),
            get()
        )
    }
    single<IHolidaysTimetableController> { HolidaysTimetableController(get()) }
    single<IOfficeWeekController> { OfficeWeekController(get()) }
}
