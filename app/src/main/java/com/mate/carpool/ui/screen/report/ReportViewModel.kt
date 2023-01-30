package com.mate.carpool.ui.screen.report

import androidx.lifecycle.viewModelScope
import com.mate.carpool.data.Result
import com.mate.carpool.data.repository.ReportRepository
import com.mate.carpool.ui.base.BaseViewModel
import com.mate.carpool.ui.base.SnackBarMessage
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class ReportViewModel @Inject constructor(
    private val reportRepository: ReportRepository
) : BaseViewModel() {

    private var studentId: Long? = null

    private val _reason = MutableStateFlow<ReportReason?>(null)
    val reason = _reason.asStateFlow()

    private val _description = MutableStateFlow("")
    val description = _description.asStateFlow()

    val enableReport = _reason.combine(_description) { a, b -> Pair(a, b) }.map {
        val reason = it.first
        val description = it.second

        reason != null && (reason != ReportReason.ETC || description.isNotBlank())
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(0L),
        initialValue = false
    )

    fun init(studentId: Long) {
        this.studentId = studentId
    }

    fun selectReason(reason: ReportReason) {
        _reason.update { reason }
    }

    fun deselectReason() {
        _reason.update { null }
    }

    fun setDescription(description: String) {
        _description.update { description }
    }

    fun report() {
        val content = if (reason.value == ReportReason.ETC) {
            description.value
        } else {
            reason.value!!.description
        }
        reportRepository.report(
            studentId = studentId!!, content = content
        ).onEach { result ->
            when (result) {
                is Result.Loading -> {

                }

                is Result.Success -> {
                    emitSnackbar(SnackBarMessage(headerMessage = "신고가 접수되었습니다."))
                    emitEvent(EVENT_FINISH)
                }

                is Result.Error -> {
                    emitSnackbar(SnackBarMessage(headerMessage = result.message))
                }
            }
        }.launchIn(viewModelScope)
    }

    companion object {

        const val EVENT_FINISH = "EVENT_FINISH"
    }
}

