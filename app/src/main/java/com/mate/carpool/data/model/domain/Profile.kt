package com.mate.carpool.data.model.domain

data class Profile(
    val profileImage: String,
    val name: String,
    val studentId: String,
    val department: String,
    val phone: String,
    val daysOfUse: List<String>,
    val userRole: UserRole,
    val recentTickets: List<Ticket>
)

enum class UserRole(val text: String) {
    DRIVER(text = "드라이버"),
    PASSENGER(text = "패신저"),
    ADMIN(text = "관리자")
}

enum class StartArea(val text: String) {
    INDONG(text = "인동"),
    OKGYE(text = "옥계"),
    DEAGU(text = "대구"),
    ETC("기타");

    companion object {
        fun findByText(text: String) = values().first { it.text == text }
    }
}

enum class DayStatus(val text: String) {
    AM(text = "오전"),
    PM(text = "오후")
}

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
) {
    enum class Status(val text: String) {
        BEFORE(text = "BEFORE"),
        ING(text = "ING"),
        CANCEL(text = "CANCEL"),
        AFTER(text = "AFTER")
    }

    enum class CostType(val text: String) {
        FREE(text = "무료"),
        COST(text = "유료")
    }
}

