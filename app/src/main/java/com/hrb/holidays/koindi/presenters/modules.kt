package com.hrb.holidays.koindi.presenters

import com.hrb.holidays.app.presenters.settings.SettingsScreenPresenter
import com.hrb.holidays.app.presenters.holidays.HolidaysTimetableScreenPresenter
import com.hrb.holidays.app.presenters.office.OfficeTimeBeforeHolidaysScreenPresenter
import com.hrb.holidays.app.presenters.office.OfficeWeekScreenPresenter
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val presentersModule = module {
    viewModel { OfficeTimeBeforeHolidaysScreenPresenter(get()) }

    viewModel { HolidaysTimetableScreenPresenter(get()) }

    viewModel { OfficeWeekScreenPresenter(get()) }

    viewModel { SettingsScreenPresenter(get()) }
}
