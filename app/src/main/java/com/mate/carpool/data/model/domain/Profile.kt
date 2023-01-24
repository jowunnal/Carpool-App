package com.mate.carpool.data.model.domain

import android.os.Parcelable
import androidx.compose.runtime.Stable
import kotlinx.parcelize.Parcelize
import java.time.DayOfWeek

@Stable
@Parcelize
data class Profile(
    val profileImage: String,
    val name: String,
    val studentId: String,
    val department: String,
    val phone: String,
    val daysOfUse: List<DayOfWeek>,
    val userRole: UserRole,
    val recentTickets: List<Ticket>
) : Parcelable

@Stable
enum class UserRole(val displayName: String) {

    DRIVER(displayName = "드라이버"),
    PASSENGER(displayName = "패신저"),
    ADMIN(displayName = "관리자")
}

@Stable
enum class StartArea(val displayName: String) {

    INDONG(displayName = "인동"),
    OKGYE(displayName = "옥계"),
    DEAGU(displayName = "대구"),
    ETC("기타");

    companion object {

        fun findByText(text: String) = values().first { it.displayName == text }
    }
}

@Stable
enum class DayStatus(val displayName: String) {

    AM(displayName = "오전"),
    PM(displayName = "오후")
}

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
    val status: Status,
    val costType: CostType
) : Parcelable {

    @Stable
    enum class Status(val displayName: String) {

        BEFORE(displayName = "BEFORE"),
        ING(displayName = "ING"),
        CANCEL(displayName = "CANCEL"),
        AFTER(displayName = "AFTER")
    }

    @Stable
    enum class CostType(val displayName: String) {

        FREE(displayName = "무료"),
        COST(displayName = "유료")
    }
}

