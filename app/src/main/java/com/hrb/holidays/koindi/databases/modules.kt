package com.hrb.holidays.koindi.databases

import com.hrb.holidays.commons.databases.holidays.HolidaysTimetableRepository
import com.hrb.holidays.commons.databases.office.OfficeWeekRepository
import com.hrb.holidays.commons.databases.holidays.IHolidaysTimetableGateway
import com.hrb.holidays.commons.databases.office.IOfficeWeekGateway
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.Module
import org.koin.dsl.module

val databasesModule: Module = module {
    single<IHolidaysTimetableGateway> { HolidaysTimetableRepository(androidContext()) }
    single<IOfficeWeekGateway> { OfficeWeekRepository(androidContext()) }
}
