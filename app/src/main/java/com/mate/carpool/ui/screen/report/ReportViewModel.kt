package com.mate.carpool.ui.screen.report

import androidx.lifecycle.viewModelScope
import com.mate.carpool.data.repository.ReportRepository
import com.mate.carpool.ui.base.BaseViewModel
import com.mate.carpool.ui.base.SnackBarMessage
import com.mate.carpool.ui.screen.report.item.ReportReason
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class ReportViewModel @Inject constructor(
    private val reportRepository: ReportRepository
) : BaseViewModel() {

    private lateinit var ticketId: String
    private lateinit var userId: String

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

    fun init(ticketId: String, userId: String) {
        this.ticketId = ticketId
        this.userId = userId
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
            ticketId = ticketId,
            userId = userId,
            content = content
        ).onEach { result ->
            emitSnackbar(SnackBarMessage(headerMessage = "신고가 접수되었습니다."))
        }.catch {
            emitSnackbar(
                SnackBarMessage(
                    headerMessage = "일시적인 장애가 발생하였습니다.",
                    contentMessage = "다시 시도해 주세요."
                )
            )
        }.launchIn(viewModelScope)
    }

    companion object {
        const val EVENT_REPORTED_USER = "EVENT_REPORTED_USER"
    }
}

