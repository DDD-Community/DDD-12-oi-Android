package com.ddd.oi.presentation.core.designsystem.component.oitimepicker

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.snapping.rememberSnapFlingBehavior
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ddd.oi.presentation.R
import com.ddd.oi.presentation.core.designsystem.component.common.OiButton
import com.ddd.oi.presentation.core.designsystem.component.common.OiButtonColorType
import com.ddd.oi.presentation.core.designsystem.component.common.OiButtonStyle
import com.ddd.oi.presentation.core.designsystem.component.dialog.OiDialog
import com.ddd.oi.presentation.core.designsystem.theme.OiTheme
import kotlinx.coroutines.launch
import kotlin.math.abs
import kotlin.math.roundToInt

@Composable
fun OiTimePicker(
    modifier: Modifier = Modifier,
    hour: String? = null,
    minute: String? = null,
    onConfirm: (String, String) -> Unit,
) {
    val hours = (1..12).map { it.toString() }
    val minutes = (0..59).map { "%02d".format(it) }
    val periods = listOf("AM", "PM")

    val initialTime = remember(hour) {
        val defaultTime = "12" to "AM"

        hour?.toIntOrNull()?.let { hour24 ->
            val period = if (hour24 >= 12) "PM" else "AM"
            val hour12 = when {
                hour24 == 0 -> "12"
                hour24 > 12 -> (hour24 - 12).toString()
                else -> hour24.toString()
            }
            hour12 to period
        } ?: defaultTime
    }

    var selectedHour by remember { mutableStateOf(initialTime.first) }
    var selectedMinute by remember { mutableStateOf(minute ?: "00") }
    var selectedPeriod by remember { mutableStateOf(initialTime.second) }

    Column(
        modifier = modifier
            .padding(16.dp)
            .wrapContentHeight()
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                PickerColumn(
                    modifier = Modifier.weight(1f),
                    items = hours,
                    initialValue = selectedHour,
                    onValueChange = { selectedHour = it }
                )
                Spacer(modifier = Modifier.width(12.dp))
                PickerColumn(
                    modifier = Modifier.weight(1f),
                    items = minutes,
                    initialValue = selectedMinute,
                    onValueChange = { selectedMinute = it }
                )
                Spacer(modifier = Modifier.width(12.dp))
                PickerColumn(
                    modifier = Modifier.weight(1f),
                    items = periods,
                    initialValue = selectedPeriod,
                    onValueChange = { selectedPeriod = it },
                    isInfinite = false // 👇 AM/PM은 무한 스크롤을 사용하지 않음
                )
            }
        }
        OiButton(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 12.dp),
            style = OiButtonStyle.Large48Oval,
            colorType = OiButtonColorType.Primary,
            textStringRes = R.string.button_register,
            onClick = {
                val hour12 = selectedHour.toIntOrNull() ?: 12
                val hour24 = when (selectedPeriod) {
                    "AM" -> {
                        if (hour12 == 12) 0 else hour12
                    }

                    "PM" -> {
                        if (hour12 == 12) 12 else hour12 + 12
                    }

                    else -> hour12
                }
                onConfirm(
                    "%02d".format(hour24),
                    selectedMinute
                )
            }
        )
    }
}

@Composable
private fun PickerColumn(
    modifier: Modifier = Modifier,
    items: List<String>,
    initialValue: String,
    onValueChange: (String) -> Unit,
    itemHeight: Dp = 44.dp,
    isInfinite: Boolean = true, // 👇 조건부 무한 스크롤을 위한 파라미터
) {
    val itemHeightPx = with(LocalDensity.current) { itemHeight.toPx() }
    val lazyListState = rememberLazyListState(
        initialFirstVisibleItemIndex = if (isInfinite) 0 else items.indexOf(initialValue)
            .coerceAtLeast(0)
    )
    val scope = rememberCoroutineScope()

    LaunchedEffect(key1 = items, key2 = isInfinite) {
        val initialIndex = items.indexOf(initialValue).coerceAtLeast(0)
        val targetIndex = if (isInfinite) {
            (Int.MAX_VALUE / 2) - ((Int.MAX_VALUE / 2) % items.size) + initialIndex
        } else {
            initialIndex
        }
        lazyListState.scrollToItem(targetIndex)
    }

    LaunchedEffect(lazyListState.isScrollInProgress) {
        if (!lazyListState.isScrollInProgress) {
            val centerIndex = lazyListState.firstVisibleItemIndex +
                    (lazyListState.firstVisibleItemScrollOffset / itemHeightPx).roundToInt()
            val finalIndex = if (isInfinite) centerIndex % items.size else centerIndex
            if (finalIndex < items.size) {
                onValueChange(items[finalIndex])
            }
        }
    }

    Box(
        modifier = modifier.height(220.dp),
        contentAlignment = Alignment.Center
    ) {
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .height(itemHeight),
            shape = RoundedCornerShape(8.dp),
            color = Color.Black.copy(alpha = 0.05f)
        ) {}

        LazyColumn(
            modifier = modifier,
            state = lazyListState,
            flingBehavior = rememberSnapFlingBehavior(lazyListState),
            horizontalAlignment = Alignment.CenterHorizontally,
            contentPadding = PaddingValues(vertical = itemHeight * 2)
        ) {
            val itemCount = if (isInfinite) Int.MAX_VALUE else items.size
            items(itemCount, key = { it }) { index ->
                val itemValue = items[index % items.size]

                val distance by remember {
                    derivedStateOf {
                        val centerIndex = lazyListState.firstVisibleItemIndex +
                                (lazyListState.firstVisibleItemScrollOffset / itemHeightPx).roundToInt()
                        abs(index - centerIndex)
                    }
                }
                val style =
                    if (distance == 0) OiTheme.typography.headlineLargeSemiBold
                    else OiTheme.typography.headlineMediumRegular
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(itemHeight)
                        .clip(RoundedCornerShape(8.dp))
                        .clickable {
                            scope.launch {
                                lazyListState.animateScrollToItem(index)
                                onValueChange(itemValue)
                            }
                        },
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = itemValue,
                        modifier = Modifier
                            .alpha(if (distance > 2) 0f else 1f - (distance * 0.3f))
                            .graphicsLayer {
                                val centerIndex = lazyListState.firstVisibleItemIndex +
                                        (lazyListState.firstVisibleItemScrollOffset / itemHeightPx).roundToInt()

                                val relativeOffset =
                                    (index - centerIndex) * itemHeightPx + lazyListState.firstVisibleItemScrollOffset
                                rotationX = -relativeOffset * 0.12f
                            },
                        style = style,
                        color = if (distance == 0) OiTheme.colors.textPrimary else OiTheme.colors.textTertiary,
                    )
                }
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
private fun OiTimePickerPreview() {
    OiTheme {
        var isVisible by remember { mutableStateOf(false) }
        Column(modifier = Modifier.fillMaxSize()) {
            Button(onClick = { isVisible = true }) {
                Text("complete")
            }
        }
        if (isVisible) {
            OiDialog(onDismiss = { isVisible = false }) {
                OiTimePicker(
                    onConfirm = { hour, minute ->
                        Log.d("value", "$hour $minute")
                    }
                )
            }
        }
    }
}