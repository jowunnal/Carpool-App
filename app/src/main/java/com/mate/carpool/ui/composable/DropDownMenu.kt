package com.mate.carpool.ui.composable

import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mate.carpool.data.model.domain.StartArea
import com.mate.carpool.ui.theme.black
import com.mate.carpool.ui.util.tu
import com.mate.carpool.util.MatePreview

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DropDownMenuCustom(
    @DrawableRes iconHeader: Int,
    @DrawableRes iconTail: Int,
    label: String,
    text: String,
    items: List<StartArea>,
    expandedState: Boolean,
    onExpandedChange: (Boolean) -> Unit,
    onDismissRequest: () -> Unit,
    setTextChanged: (String) -> Unit
) {
    ExposedDropdownMenuBox(
        expanded = expandedState,
        onExpandedChange = onExpandedChange
    ) {
        Column() {
            Text(
                text = label,
                color = black,
                fontWeight = FontWeight.Normal,
                fontSize = 13.tu
            )
            VerticalSpacer(height = 1.dp)
            Row(modifier = Modifier.padding(horizontal = 12.dp, vertical = 16.dp)) {
                Icon(
                    painter = painterResource(id = iconHeader),
                    contentDescription = "DropDownMenuIconHeader"
                )
                HorizontalSpacer(width = 12.dp)
                Text(
                    text = text,
                    fontSize = 18.tu,
                    fontWeight = FontWeight.W400,
                    color = black,
                    modifier = Modifier.weight(1f)
                )
                IconButton(
                    onClick = { /*TODO*/ },
                    modifier = Modifier.size(24.dp)
                ) {
                    Icon(
                        painter = painterResource(id = iconTail),
                        contentDescription = "DropDownMenuIconTail",
                        modifier = Modifier.fillMaxSize()
                    )
                }
            }
            DropdownMenu(
                expanded = expandedState,
                onDismissRequest = onDismissRequest
            ) {
                items.forEach {
                    androidx.compose.material.DropdownMenuItem(onClick = { setTextChanged(it.displayName) }) {
                        Text(text = it.displayName)
                    }
                }
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
private fun PreviewDropDownMenuCustom() {
    MatePreview {
        DropDownMenuCustom(
            iconHeader = com.mate.carpool.R.drawable.ic_home_location,
            iconTail = com.mate.carpool.R.drawable.ic_arrow_down_small,
            label = "출발 지역",
            text = "인동",
            expandedState = false,
            onExpandedChange = {},
            onDismissRequest = {},
            setTextChanged = {},
            items = emptyList()
        )
    }
}