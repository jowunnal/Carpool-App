package com.mate.carpool.data.model.dto.dto.request

import com.google.gson.annotations.SerializedName

data class ReportRequest(
    @SerializedName("carpoolId") val ticketId: String,
    @SerializedName("reportedId") val userId: String,
    @SerializedName("content") val content: String
) {
    companion object {
        fun fromDomain(
            ticketId: String,
            userId: String,
            content: String
        ) = ReportRequest(
            ticketId = ticketId,
            userId = userId,
            content = content
        )
    }
}
