package com.ddd.oi.presentation.scheduledetail.content

import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.awaitVerticalDragOrCancellation
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.pointer.PointerEventType
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ddd.oi.domain.model.schedule.Place
import com.ddd.oi.presentation.R
import com.ddd.oi.presentation.core.designsystem.component.common.rippleOrFallbackImplementation
import com.ddd.oi.presentation.core.designsystem.component.mapper.formatToScheduleDetailActiveDate
import com.ddd.oi.presentation.core.designsystem.component.mapper.getPlaceCategoryColor
import com.ddd.oi.presentation.core.designsystem.theme.OiTheme
import com.ddd.oi.presentation.core.designsystem.theme.white
import com.ddd.oi.presentation.scheduledetail.contract.ScheduleDay
import kotlinx.collections.immutable.ImmutableList
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.mapNotNull
import kotlinx.coroutines.launch
import kotlinx.datetime.LocalDate

private sealed interface SheetListItem {
    val date: LocalDate

    data class Header(val day: Int, override val date: LocalDate) : SheetListItem
    data class PlaceItem(val place: Place, override val date: LocalDate) : SheetListItem
    data class EmptyPlace(override val date: LocalDate) : SheetListItem
}

@Composable
internal fun ScheduleDetailSheetContent(
    modifier: Modifier = Modifier,
    lazyListState: LazyListState,
    scheduleDays: ImmutableList<ScheduleDay>,
    activeDate: LocalDate,
    isAutoSelectionLocked: Boolean,
    selectedPlace: Place? = null,
    onActiveDateChanged: (LocalDate) -> Unit,
    onScrollPlaceChange: (Place) -> Unit,
    onClickPlace: (Place, LocalDate) -> Unit,
    onUserScroll: () -> Unit,
    editTimeClick: () -> Unit,
    onMemoEdit: (Place) -> Unit,
) {
    val scope = rememberCoroutineScope()

    /**
     *  각 날짜(Day)가 LazyColumn 시작하는 인덱스를 계산
     */
    val dayStartIndices = remember(scheduleDays) {
        val indices = mutableListOf<Int>()
        var currentIndex = 0
        scheduleDays.forEach { day ->
            indices.add(currentIndex) // Day의 시작 인덱스를 추가
            currentIndex += 1 + if (day.places.isNotEmpty()) day.places.size else 1
        }
        indices
    }

    LaunchedEffect(activeDate) {
        val dayIndex = scheduleDays.indexOfFirst { it.date == activeDate }
        if (dayIndex == -1) return@LaunchedEffect

        val targetIndex = dayStartIndices.getOrNull(dayIndex)
        if (targetIndex != null && lazyListState.firstVisibleItemIndex != targetIndex) {
            scope.launch {
                lazyListState.animateScrollToItem(index = targetIndex)
            }
        }
    }

    val flatListItems = remember(scheduleDays) {
        buildList<SheetListItem> {
            scheduleDays.forEach { day ->
                add(SheetListItem.Header(day.day, day.date))
                if (day.places.isNotEmpty()) {
                    day.places.forEach { place -> add(SheetListItem.PlaceItem(place, day.date)) }
                } else {
                    add(SheetListItem.EmptyPlace(day.date))
                }
            }
        }
    }

    LaunchedEffect(lazyListState, flatListItems) {
        snapshotFlow { lazyListState.firstVisibleItemIndex }
            .mapNotNull { index ->
                flatListItems.getOrNull(index)
            }
            .collect { topVisibleItem ->
                if (!isAutoSelectionLocked) {
                    if (activeDate != topVisibleItem.date) {
                        Log.d("날짜바뀌기", "날짜바뀌기")
                        onActiveDateChanged(topVisibleItem.date)
                    }
                    if (topVisibleItem is SheetListItem.PlaceItem) {
                        onScrollPlaceChange(topVisibleItem.place)
                    }
                }
            }
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(OiTheme.colors.backgroundContents)
            .pointerInput(Unit) {
                awaitPointerEventScope {
                    while (true) {
                        val event = awaitPointerEvent()
                        if (event.type == PointerEventType.Press) {
                            val dragStartEvent =
                                awaitVerticalDragOrCancellation(event.changes.first().id)
                            if (dragStartEvent != null) {
                                onUserScroll()
                            }
                        }
                    }
                }
            },
        state = lazyListState,
    ) {
        items(
            count = flatListItems.size,
            key = { index ->
                when (val item = flatListItems[index]) {
                    is SheetListItem.Header -> "header_${item.date}"
                    is SheetListItem.PlaceItem -> "place_${item.place.id}"
                    is SheetListItem.EmptyPlace -> "empty_${item.date}"
                }
            },
            contentType = { index ->
                when (flatListItems[index]) {
                    is SheetListItem.Header -> "HEADER"
                    is SheetListItem.PlaceItem -> "PLACE"
                    is SheetListItem.EmptyPlace -> "EMPTY"
                }
            }
        ) { index ->
            val item = flatListItems[index]
            val date = item.date

            when (item) {
                is SheetListItem.Header -> {
                    AnimatedVisibility(
                        visible = activeDate != date,
                    ) {
                        Text(
                            modifier = Modifier.padding(start = 16.dp),
                            text = "Day${item.day} (${
                                formatToScheduleDetailActiveDate(
                                    item.date
                                )
                            })",
                            style = OiTheme.typography.bodyLargeSemibold
                        )
                    }
                }

                is SheetListItem.PlaceItem -> {
                    val place = item.place
                    val day = scheduleDays.find { it.date == date }
                    Box(modifier = Modifier.fillMaxWidth()) {
                        SwipePlaceCard(
                            place = place,
                            order = (day?.places?.indexOf(place) ?: 0) + 1,
                            isSelected = place.id == selectedPlace?.id,
                            onClick = {
                                onClickPlace(place, item.date)
                                scope.launch {
                                    lazyListState.animateScrollToItem(index = index)
                                }
                            },
                            editTimeClick = {
                                onClickPlace(place, item.date)
                                editTimeClick()
                            },
                            onMemoClick = {
                                onMemoEdit(place)
                            },
                            onEditClick = {},
                            onDeleteClick = {}
                        )
                    }
                }

                is SheetListItem.EmptyPlace -> {
                    EmptyPlace()
                }
            }
        }
        item {
            Spacer(modifier = Modifier.height(800.dp))
        }
    }
}


@Composable
fun PlaceCard(
    modifier: Modifier = Modifier,
    place: Place,
    order: Int,
    isSelected: Boolean,
    onClick: () -> Unit,
    editTimeClick: () -> Unit,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(if (isSelected) Color(0xFF262626).copy(alpha = 0.04f) else Color.Transparent),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // 왼쪽 숫자 아이콘
        Box(
            modifier = Modifier
                .padding(start = 32.dp, end = 24.dp)
                .clip(CircleShape)
                .background(getPlaceCategoryColor(place.category))
                .size(16.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "$order",
                style = OiTheme.typography.bodyXSmallSemibold,
                color = white
            )
        }

        // 오른쪽 내용 카드
        Surface(
            shape = RoundedCornerShape(16.dp),
            modifier = Modifier
                .weight(1f)
                .height(90.dp)
                .padding(end = 16.dp, top = 8.dp, bottom = 8.dp),
            color = white,
            shadowElevation = 2.dp,
            onClick = onClick
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = place.startTime ?: "-- : --",
                    style = OiTheme.typography.bodyMediumSemibold
                )
                Box(
                    modifier = Modifier
                        .clickable(
                            onClick = {
                                onClick()
                                editTimeClick()
                            },
                            role = Role.Button,
                            interactionSource = null,
                            indication = rippleOrFallbackImplementation(
                                bounded = false,
                                radius = 8.dp
                            )
                        )
                ) {
                    Icon(
                        imageVector = ImageVector.vectorResource(R.drawable.ic_calendar_dropdown),
                        contentDescription = "시간 선택",
                        tint = Color(0xFFA1A1A1)
                    )
                }
                VerticalDivider(
                    modifier = Modifier.height(16.dp),
                    color = Color(0xFFF6F6F6)
                )
                // 장소 이름 및 추가 정보
                Column(
                    modifier = Modifier.padding(start = 12.dp),
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Text(
                        text = place.spotName,
                        style = OiTheme.typography.bodyMediumSemibold
                    )
                    if (place.memo.isNotEmpty()) {
                        Text(
                            text = place.memo,
                            style = OiTheme.typography.bodySmallRegular,
                            color = OiTheme.colors.textTertiary
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun EmptyPlace(modifier: Modifier = Modifier) {
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Surface(
            modifier = Modifier
                .padding(start = 32.dp, end = 24.dp)
                .size(16.dp),
            shape = CircleShape,
            color = OiTheme.colors.backgroundDisabled
        ) {
            Box(contentAlignment = Alignment.Center) {
                Text(
                    text = "1",
                    style = OiTheme.typography.bodyXSmallSemibold,
                    color = white
                )
            }
        }
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .padding(end = 16.dp, top = 8.dp, bottom = 8.dp),
            shape = RoundedCornerShape(16.dp),
            color = white,
        ) {
            Row(
                modifier = Modifier.height(70.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    modifier = Modifier.padding(start = 16.dp),
                    text = buildAnnotatedString {
                        append("- -")
                        withStyle(style = SpanStyle(color = OiTheme.colors.textPrimary)) {
                            append(" : ")
                        }
                        append("- -")
                    },
                    style = OiTheme.typography.bodyMediumSemibold,
                    color = OiTheme.colors.textDisabled
                )
                Box {
                    Icon(
                        imageVector = ImageVector.vectorResource(R.drawable.ic_calendar_dropdown),
                        contentDescription = null,
                        tint = OiTheme.colors.iconTertiary
                    )
                }
                VerticalDivider(
                    modifier = Modifier
                        .padding(horizontal = 12.dp)
                        .height(16.dp),
                    color = Color(0xFFF6F6F6)
                )
                Text(
                    text = "장소를 추가해주세요",
                    style = OiTheme.typography.bodyMediumSemibold,
                    color = OiTheme.colors.textDisabled
                )
            }
        }
    }
}

@Composable
private fun RouteButton(
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier
            .size(width = 48.dp, height = 28.dp),
        shape = RoundedCornerShape(999.dp),
        border = BorderStroke(1.dp, OiTheme.colors.borderPrimary),
        color = white,
        onClick = {}
    ) {
        Row(
            modifier = Modifier.fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Icon(
                modifier = Modifier.size(12.dp),
                imageVector = ImageVector.vectorResource(R.drawable.ic_route),
                contentDescription = null
            )
        }
    }
}

@Composable
@Preview(showBackground = true)
private fun PlaceCardPreview() {
    OiTheme {
        val place1 = Place(
            id = 0,
            spotName = "부산",
            memo = "오후 4시부터 체크인",
            startTime = "15:00",
            targetDate = "2025-07-20",
            latitude = 34.2132,
            longitude = 124.123,
            category = "카페"
        )
        val place2 = Place(
            id = 0,
            spotName = "부산",
            memo = "",
            startTime = "15:00",
            targetDate = "2025-07-20",
            latitude = 34.2132,
            longitude = 124.123,
            category = "숙박시설"
        )
        Column {
            PlaceCard(
                place = place1,
                order = 1,
                isSelected = true,
                onClick = {},
                editTimeClick = {}
            )
        }
    }
}