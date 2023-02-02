package com.mate.carpool.data.model.domain

import android.os.Parcelable
import androidx.compose.runtime.Stable
import com.mate.carpool.data.model.item.DayStatus
import com.mate.carpool.data.model.item.TicketType
import com.mate.carpool.data.model.item.TicketStatus
import kotlinx.parcelize.Parcelize

@Stable
@Parcelize
data class Ticket(
    val id: Long,
    val thumbnail: String,
    val startArea: StartArea,
    val dayStatus: DayStatus,
    val startTime: Long,
    val maximumNumber: Int,   // 정원
    val currentNumber: Int,   // 현재 카풀에 참여한 인원 수
    val status: TicketStatus,
    val costType: TicketType
) : Parcelable 