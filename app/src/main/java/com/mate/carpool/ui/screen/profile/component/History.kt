package com.mate.carpool.ui.screen.profile.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.mate.carpool.data.model.domain.DayStatus
import com.mate.carpool.data.model.domain.StartArea
import com.mate.carpool.data.model.domain.Ticket
import com.mate.carpool.ui.composable.Badge
import com.mate.carpool.ui.composable.Circle
import com.mate.carpool.ui.composable.HorizontalDivider
import com.mate.carpool.ui.composable.HorizontalSpacer
import com.mate.carpool.ui.composable.VerticalSpacer
import com.mate.carpool.ui.theme.black
import com.mate.carpool.ui.theme.gray
import com.mate.carpool.ui.theme.neutral70
import com.mate.carpool.ui.theme.primary10
import com.mate.carpool.ui.theme.primary60
import com.mate.carpool.ui.theme.red60
import com.mate.carpool.ui.util.appendBoldText
import com.mate.carpool.ui.util.hour
import com.mate.carpool.ui.util.minute
import com.mate.carpool.ui.util.tu
import java.util.Calendar

@Suppress("FunctionName")
fun LazyListScope.HistoryHeaderItem(modifier: Modifier = Modifier) {
    item { HistoryHeader(modifier = modifier) }
}

@Composable
private fun HistoryHeader(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.padding(start = 16.dp, end = 16.dp)
    ) {
        androidx.compose.material.Text(
            modifier = Modifier.padding(vertical = 16.dp),
            text = "최근 티켓 목록",
            color = black,
            fontWeight = FontWeight.Bold,
            fontSize = 16.tu
        )
        HorizontalDivider()
    }
}

@Composable
fun HistoryGroup(
    group: Pair<Triple<Int, Int, Int>, List<Ticket>>,
    modifier: Modifier = Modifier
) {
    val (_, month, date) = group.first
    val histories = group.second
    Column(modifier.padding(top = 8.dp, start = 16.dp, end = 16.dp)) {
        Text(
            text = "${month}/${date}",
            color = neutral70,
            fontSize = 12.tu,
            fontWeight = FontWeight.Normal
        )
        VerticalSpacer(5.dp)
        histories.forEach { history ->
            HistoryCell(
                thumbnail = history.thumbnail,
                startArea = history.startArea,
                dayStatus = history.dayStatus,
                startTime = history.startTime,
                maximumNumber = history.maximumNumber,
                currentNumber = history.currentNumber,
                status = history.status,
                costType = history.costType
            )
            VerticalSpacer(2.dp)
        }
        VerticalSpacer(3.dp)
        HorizontalDivider()
    }
}

@Composable
private fun HistoryCell(
    thumbnail: String,
    startArea: StartArea,
    dayStatus: DayStatus,
    startTime: Long,
    maximumNumber: Int,
    currentNumber: Int,
    status: Ticket.Status,
    costType: Ticket.CostType,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        AsyncImage(
            modifier = Modifier
                .size(42.dp)
                .padding(3.dp)
                .clip(CircleShape)
                .offset(x = (-3).dp)
                .background(primary10),
            model = thumbnail,
            contentDescription = "driver profile image"
        )
        VerticalSpacer(5.dp)
        Text(
            modifier = Modifier.weight(1f),
            text = buildAnnotatedString {
                val cal = Calendar.getInstance()
                cal.timeInMillis = startTime
                appendBoldText(startArea.text)
                append(" 출발, ${dayStatus.text} ")
                appendBoldText("${cal.hour}:${cal.minute}")
            },
            color = gray,
            fontSize = 16.tu,
        )
        HorizontalSpacer(4.dp)
        Circle(
            size = 8.dp,
            color = when (costType) {
                Ticket.CostType.FREE -> primary60
                Ticket.CostType.COST -> red60
            }
        )
        HorizontalSpacer(8.dp)
        Badge(
            maximumNumber = maximumNumber,
            currentNumber = currentNumber,
            status = status,
            costType = costType
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun HistoryHeaderPreview() {
    HistoryHeader()
}

@Preview(showBackground = true)
@Composable
private fun HistoryGroupPreview() {
    val histories = List(4) { id ->
        Ticket(
            id = id.toLong(),
            thumbnail = "",
            startArea = StartArea.DEAGU,
            dayStatus = DayStatus.PM,
            startTime = System.currentTimeMillis(),
            maximumNumber = 3,
            currentNumber = 1,
            status = Ticket.Status.AFTER,
            costType = Ticket.CostType.COST
        )
    }
    HistoryGroup(group = Pair(Triple(2023, 1, 3), histories))
}
