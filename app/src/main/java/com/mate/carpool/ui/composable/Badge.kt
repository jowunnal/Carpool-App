package com.mate.carpool.ui.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mate.carpool.data.model.item.TicketType
import com.mate.carpool.data.model.item.TicketStatus
import com.mate.carpool.ui.theme.neutral30
import com.mate.carpool.ui.theme.primary60
import com.mate.carpool.ui.theme.red60
import com.mate.carpool.ui.theme.white
import com.mate.carpool.ui.util.tu

@Composable
fun Badge(
    maximumNumber: Int,   // 정원
    currentNumber: Int,   // 현재 카풀에 참여한 인원 수
    status: TicketStatus,
    costType: TicketType,
    modifier: Modifier = Modifier
) {
    // TODO 정확한 조건 확인 필요
    val backgroundColor = when {
        status != TicketStatus.After && costType == TicketType.Cost -> primary60
        status != TicketStatus.After && costType == TicketType.Free -> red60
        else -> neutral30
    }

    Box(
        modifier = modifier.size(44.dp, 24.dp).background(backgroundColor, RoundedCornerShape(100.dp)),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "${currentNumber}/${maximumNumber}",
            color = white,
            fontSize = 12.tu,
            fontWeight = FontWeight.Normal
        )
    }
}

@Preview
@Composable
private fun BadgePreview1() {
    Badge(
        maximumNumber = 4,
        currentNumber = 1,
        status = TicketStatus.Before,
        costType = TicketType.Free
    )
}

@Preview
@Composable
private fun BadgePreview2() {
    Badge(
        maximumNumber = 4,
        currentNumber = 1,
        status = TicketStatus.Before,
        costType = TicketType.Cost
    )
}

@Preview
@Composable
private fun BadgePreview3() {
    Badge(
        maximumNumber = 4,
        currentNumber = 1,
        status = TicketStatus.After,
        costType = TicketType.Free
    )
}
