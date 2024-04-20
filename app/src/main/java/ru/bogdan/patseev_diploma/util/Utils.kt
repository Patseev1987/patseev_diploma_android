package ru.bogdan.patseev_diploma.util

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import ru.bogdan.patseev_diploma.domain.models.Tool
import ru.bogdan.patseev_diploma.domain.models.Transaction
import ru.bogdan.patseev_diploma.domain.models.Worker
import java.time.LocalDate
import java.time.LocalDateTime


fun Fragment.transaction(
    id: Long,
    from: Worker,
    destination: Worker,
    date: LocalDate,
    tool: Tool,
    amount: Int,
    note: String? = null
): Transaction {
    return Transaction( id, from, destination, date, tool, amount, note)
}

fun ViewModel.transaction(
    id: Long,
    from: Worker,
    destination: Worker,
    date: LocalDate,
    tool: Tool,
    amount: Int,
    note: String? = null
): Transaction {
    return Transaction( id, from, destination, date, tool, amount, note)
}




