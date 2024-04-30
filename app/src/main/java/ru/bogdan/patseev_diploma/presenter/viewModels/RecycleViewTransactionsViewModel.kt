package ru.bogdan.patseev_diploma.presenter.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.launch
import ru.bogdan.patseev_diploma.data.web.ApiFactory
import ru.bogdan.patseev_diploma.data.web.ApiHelperImpl
import ru.bogdan.patseev_diploma.MyApplication
import ru.bogdan.patseev_diploma.R
import ru.bogdan.patseev_diploma.domain.models.enums.Department
import ru.bogdan.patseev_diploma.presenter.states.RecycleVIewTransactionState
import java.lang.RuntimeException
import javax.inject.Inject

class RecycleViewTransactionsViewModel @Inject constructor(
    private val application: MyApplication,
) : ViewModel() {

    private val apiHelperImpl = ApiHelperImpl(ApiFactory.apiService)

    private val searchString: MutableStateFlow<String> =
        MutableStateFlow(BLANK_TOOL_CODE)

    private val _state: MutableStateFlow<RecycleVIewTransactionState> =
        MutableStateFlow(RecycleVIewTransactionState.Loading)

    val state = _state.asStateFlow()

    fun loadTransactions(anotherDepartment: Department, toolCode: String = BLANK_TOOL_CODE) {
        viewModelScope.launch(Dispatchers.IO) {
            _state.value = RecycleVIewTransactionState.Result(
                apiHelperImpl
                    .loadTransactionsWithAnotherDepartment(anotherDepartment, toolCode =  toolCode),
                anotherDepartment.getMessageForTitle()
            )
        }
    }

    private fun Department.getMessageForTitle(): String {
        return when (this) {
            Department.SHARPENING -> application
                .getString(R.string.transactions_with_sharpening)

            Department.MAIN_STORAGE -> application
                .getString(R.string.transaction_with_main_storage)

            Department.STORAGE_OF_DECOMMISSIONED_TOOLS -> application
                .getString(R.string.transactions_with_decommissioned_tools)

            else -> throw RuntimeException("Unknown another department")
        }
    }

    @OptIn(FlowPreview::class)
    fun updateTransactionWithFilter(anotherDepartment: Department, code:String) {
        searchString.value = code
        viewModelScope.launch(Dispatchers.IO) {
            searchString.debounce(500)
                .collect { toolCode ->
                    _state.value = RecycleVIewTransactionState.Loading
                    loadTransactions(anotherDepartment,toolCode)
                }
        }
    }



    companion object {
        private const val BLANK_TOOL_CODE = ""
    }
}