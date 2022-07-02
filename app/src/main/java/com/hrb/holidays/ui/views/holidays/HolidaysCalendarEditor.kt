package com.hrb.holidays.ui.views.holidays

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.SnackbarDefaults.backgroundColor
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color.Companion.Red
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.hrb.holidays.commons.entities.holidays.HolidayPeriod
import com.hrb.holidays.commons.presenters.holidays.HolidaysTimetableScreenPresenter
import com.hrb.holidays.ui.views.base.DrawerTopBar
import com.vanpra.composematerialdialogs.MaterialDialog
import com.vanpra.composematerialdialogs.datetime.date.datepicker
import com.vanpra.composematerialdialogs.rememberMaterialDialogState
import kotlinx.coroutines.launch
import org.koin.androidx.compose.getViewModel
import java.time.LocalDate
import java.util.*


@OptIn(ExperimentalMaterialApi::class, ExperimentalFoundationApi::class)
@Composable
fun HolidaysCalendarEditorScreenActivity(presenter: HolidaysTimetableScreenPresenter = getViewModel()) {
    val holidays = presenter.uiState.holidays
    val scope = rememberCoroutineScope()

    HolidaysCalendarEditorScreen(
        holidays = holidays,
        onAddHoliday = {
            scope.launch {
                presenter.addHoliday(it)
            }
        },
        onDismissHoliday = {
            scope.launch {
                presenter.deleteHoliday(it)
            }
        }
    )
}

@ExperimentalMaterialApi
@ExperimentalFoundationApi
@Composable
fun HolidaysCalendarEditorScreen(
    holidays: Set<HolidayPeriod>,
    onAddHoliday: (HolidayPeriod) -> Unit,
    onDismissHoliday: (HolidayPeriod) -> Unit
) {
    DrawerTopBar()
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        AddHolidayButton(onAddHoliday)
        if (holidays.isEmpty()) {
            EmptyHolidaysList()
        } else {
            HolidaysList(
                holidays = holidays.toTypedArray(),
                onDismissHoliday = onDismissHoliday,
            )
        }
    }
}

@ExperimentalFoundationApi
@ExperimentalMaterialApi
@Composable
fun HolidaysList(
    holidays: Array<HolidayPeriod>,
    onDismissHoliday: (HolidayPeriod) -> Unit
) {
    LazyColumn(modifier = Modifier.fillMaxSize()) {
        items(items = holidays, key = { it.hashCode() }) { holiday ->
            HolidayPeriodSwappableItem(
                modifier = Modifier.animateItemPlacement(),
                holiday = holiday,
                onDelete = onDismissHoliday
            )
        }
    }
}

@ExperimentalMaterialApi
@Composable
fun HolidayPeriodSwappableItem(
    modifier: Modifier = Modifier,
    holiday: HolidayPeriod,
    onDelete: (HolidayPeriod) -> Unit
) {
    val dismissState = rememberDismissState()
    val confirmationDialogIsOpen = dismissState.isDismissed(DismissDirection.EndToStart) ||
            dismissState.isDismissed(DismissDirection.StartToEnd)
    val coroutineScope = rememberCoroutineScope()

    DeleteHolidayDialog(
        isOpen = confirmationDialogIsOpen,
        onDelete = { onDelete(holiday) },
        onCancel = {
            coroutineScope.launch {
                dismissState.reset()
            }
        })

    SwipeToDismiss(
        state = dismissState,
        modifier = modifier.padding(vertical = 4.dp),
        background = {
            val direction = dismissState.dismissDirection ?: return@SwipeToDismiss
            val color by animateColorAsState(Red)
            val alignment = when (direction) {
                DismissDirection.StartToEnd -> Alignment.CenterStart
                DismissDirection.EndToStart -> Alignment.CenterEnd
            }
            val icon = Icons.Default.Delete

            Box(
                Modifier
                    .fillMaxSize()
                    .background(color)
                    .padding(horizontal = 20.dp),
                contentAlignment = alignment
            ) {
                Icon(
                    icon,
                    contentDescription = "Delete holiday",
                    modifier = Modifier.scale(1f)
                )
            }
        },
        dismissContent = {
            Card(
                elevation = animateDpAsState(
                    if (dismissState.dismissDirection != null) 4.dp else 0.dp
                ).value,
                modifier = Modifier.clickable { }
            ) {
                HolidayPeriodItem(holiday = holiday)
            }
        }
    )
}

@ExperimentalMaterialApi
@Composable
fun HolidayPeriodItem(holiday: HolidayPeriod) {
    ListItem(
        text = {
            Text(
                holiday.name.replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.ROOT) else it.toString() },
                fontWeight = FontWeight.Bold
            )
        },
        secondaryText = { Text("From ${holiday.fromDate} to ${holiday.toDate}") }
    )
}

@Composable
fun EmptyHolidaysList() {
    Column(
        modifier = Modifier
            .fillMaxHeight(.5f)
            .fillMaxWidth()
            .padding(16.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(32.dp))
        Text(
            text = "No holidays",
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.h5,
            color = MaterialTheme.typography.h5.color.copy(alpha = .5f)
        )
    }
}

@Composable
fun AddHolidayButton(onAddHoliday: (HolidayPeriod) -> Unit) {
    var addDialogIsOpen by remember {
        mutableStateOf(false)
    }

    ExtendedFloatingActionButton(
        text = { Text("ADD") },
        onClick = { addDialogIsOpen = true },
        elevation = FloatingActionButtonDefaults.elevation(0.dp, 0.dp, 0.dp, 0.dp)
    )

    AddHolidayDialog(
        isOpen = addDialogIsOpen,
        onComplete = { addDialogIsOpen = false },
        onAdd = onAddHoliday
    )
}

@Composable
fun DeleteHolidayDialog(
    isOpen: Boolean,
    onDelete: () -> Unit = {},
    onCancel: () -> Unit = {},
    onComplete: () -> Unit = {}
) {
    if (isOpen) {
        AlertDialog(
            onDismissRequest = { onCancel(); onComplete() },
            confirmButton = {
                TextButton(
                    onClick = { onDelete(); onComplete() },
                    colors = ButtonDefaults.buttonColors(backgroundColor = Red)
                ) {
                    Text("CONFIRM")
                }
            },
            dismissButton = {
                OutlinedButton(onClick = { onCancel(); onComplete() }) {
                    Text("CANCEL")
                }
            },
            title = {
                Text(text = "Delete holiday")
            },
            text = {
                Text(
                    "Are you sure you want to delete this holiday? This action cannot be undone."
                )
            }
        )
    }
}

@Composable
fun AddHolidayDialog(
    isOpen: Boolean,
    onAdd: (HolidayPeriod) -> Unit = { _ -> },
    onCancel: () -> Unit = {},
    onComplete: () -> Unit = {}
) {
    if (isOpen) {
        Dialog(onDismissRequest = { onCancel(); onComplete() }) {
            Surface(
                shape = MaterialTheme.shapes.medium,
                color = MaterialTheme.colors.surface,
                contentColor = contentColorFor(backgroundColor)
            ) {
                var name by remember { mutableStateOf("New Holiday") }
                var fromDate by remember { mutableStateOf(LocalDate.now()) }
                var toDate by remember { mutableStateOf(LocalDate.now()) }

                val fromDatePickerState = rememberMaterialDialogState()
                val toDatePickerState = rememberMaterialDialogState()

                MaterialDialog(dialogState = fromDatePickerState,
                    buttons = {
                        positiveButton("OK")
                    }
                ) {
                    datepicker { date ->
                        fromDate = date
                    }
                }
                MaterialDialog(dialogState = toDatePickerState,
                    buttons = {
                        positiveButton("OK")
                    }
                ) {
                    datepicker { date ->
                        toDate = date
                    }
                }

                Column(modifier = Modifier.padding(16.dp)) {
                    OutlinedTextField(
                        value = name,
                        onValueChange = { name = it },
                        singleLine = true,
                        placeholder = { Text("Type holiday name") }
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text("From:")
                        Spacer(modifier = Modifier.width(8.dp))
                        OutlinedButton(onClick = { fromDatePickerState.show() }) {
                            Text(fromDate.toString())
                        }
                    }
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text("To:")
                        Spacer(modifier = Modifier.width(8.dp))
                        OutlinedButton(onClick = { toDatePickerState.show() }) {
                            Text(toDate.toString())
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))
                    Row(
                        horizontalArrangement = Arrangement.End,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        OutlinedButton(onClick = { onCancel(); onComplete() }) {
                            Text("CANCEL")
                        }
                        Spacer(modifier = Modifier.width(8.dp))
                        Button(
                            onClick = {
                                onAdd(
                                    HolidayPeriod(
                                        name,
                                        fromDate,
                                        toDate
                                    )
                                ); onComplete()
                            },
                            elevation = ButtonDefaults.elevation(0.dp, 0.dp, 0.dp, 0.dp)
                        ) {
                            Text("ADD")
                        }
                    }
                }
            }
        }
    }
}
