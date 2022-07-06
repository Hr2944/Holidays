package com.hrb.holidays.koindi.databases

import com.hrb.holidays.app.databases.settings.ISettingsRepository
import com.hrb.holidays.app.databases.settings.SettingsRepository
import com.hrb.holidays.app.databases.holidays.HolidaysTimetableRepository
import com.hrb.holidays.app.databases.holidays.IHolidaysTimetableGateway
import com.hrb.holidays.app.databases.office.IOfficeWeekGateway
import com.hrb.holidays.app.databases.office.OfficeWeekRepository
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.Module
import org.koin.dsl.module

val databasesModule: Module = module {
    single<IHolidaysTimetableGateway> { HolidaysTimetableRepository(androidContext()) }
    single<IOfficeWeekGateway> { OfficeWeekRepository(androidContext()) }
    single<ISettingsRepository> { SettingsRepository(androidContext()) }
}
