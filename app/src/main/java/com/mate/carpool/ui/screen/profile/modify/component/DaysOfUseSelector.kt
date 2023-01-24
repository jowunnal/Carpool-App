package com.mate.carpool.ui.screen.profile.modify.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mate.carpool.ui.composable.VerticalSpacer
import com.mate.carpool.ui.theme.black
import com.mate.carpool.ui.theme.gray
import com.mate.carpool.ui.theme.primary60
import com.mate.carpool.ui.theme.white
import com.mate.carpool.ui.util.DateUtil
import com.mate.carpool.ui.util.displayName
import com.mate.carpool.ui.util.tu
import com.mate.carpool.util.MatePreview
import java.time.DayOfWeek

@Composable
fun DaysOfUseSelector(
    daysOfUse: List<DayOfWeek>,
    modifier: Modifier = Modifier,
    onDayOfUseSelect: (DayOfWeek) -> Unit,
    onDayOfUseDeselect: (DayOfWeek) -> Unit,
) {
    Column(modifier = modifier.fillMaxWidth()) {
        Text(
            text = "이용할 요일을 선택해주세요.",
            color = black,
            fontSize = 22.tu,
            fontWeight = FontWeight.Bold
        )
        VerticalSpacer(height = 30.dp)
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 22.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            DateUtil.availableDay.forEach { dayOfWeek ->
                DayOfWeekCell(
                    dayOfWeek = dayOfWeek,
                    selected = daysOfUse.contains(dayOfWeek),
                    onSelect = onDayOfUseSelect,
                    onDeselect = onDayOfUseDeselect,
                )
            }
        }
    }
}

@Composable
private fun DayOfWeekCell(
    dayOfWeek: DayOfWeek,
    selected: Boolean,
    modifier: Modifier = Modifier,
    onSelect: (DayOfWeek) -> Unit,
    onDeselect: (DayOfWeek) -> Unit
) {
    val backgroundColor = if (selected) primary60 else white
    val textColor = if (selected) white else gray

    Box(
        modifier = modifier
            .size(44.dp)
            .clip(CircleShape)
            .background(backgroundColor)
            .clickable {
                if (selected) {
                    onDeselect(dayOfWeek)
                } else {
                    onSelect(dayOfWeek)
                }
            },
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = dayOfWeek.displayName,
            color = textColor,
            fontSize = 16.tu,
            fontWeight = FontWeight.Normal
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun DaysOfUseSelectorPreview() = MatePreview {
    DaysOfUseSelector(
        daysOfUse = listOf(
            DayOfWeek.MONDAY,
            DayOfWeek.TUESDAY,
            DayOfWeek.THURSDAY
        ),
        onDayOfUseSelect = {},
        onDayOfUseDeselect = {}
    )
}