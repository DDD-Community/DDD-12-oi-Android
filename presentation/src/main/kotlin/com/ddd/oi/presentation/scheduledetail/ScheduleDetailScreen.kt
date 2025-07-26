package com.ddd.oi.presentation.scheduledetail

import android.util.Log
import androidx.compose.ui.res.stringResource

import android.view.Gravity
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCompositionContext
import androidx.compose.runtime.setValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ddd.oi.domain.model.schedule.SchedulePlace
import com.ddd.oi.presentation.R
import com.ddd.oi.presentation.core.designsystem.component.common.OiBasicTextField
import com.ddd.oi.presentation.core.designsystem.component.common.OiButton
import com.ddd.oi.presentation.core.designsystem.component.common.OiButtonColorType
import com.ddd.oi.presentation.core.designsystem.component.common.OiButtonStyle
import com.ddd.oi.presentation.core.designsystem.component.dialog.OiDialog
import com.ddd.oi.presentation.core.designsystem.component.mapper.formatToScheduleDetailActiveDate
import com.ddd.oi.presentation.core.designsystem.component.mapper.getPlaceCategoryColor
import com.ddd.oi.presentation.core.designsystem.component.oibottomsheetscaffold.OiBottomSheetScaffold
import com.ddd.oi.presentation.core.designsystem.component.oitimepicker.OiTimePicker
import com.ddd.oi.presentation.core.designsystem.theme.OiTheme
import com.ddd.oi.presentation.scheduledetail.content.ScheduleDetailContent
import com.ddd.oi.presentation.scheduledetail.content.ScheduleDetailDragHandle
import com.ddd.oi.presentation.scheduledetail.content.ScheduleDetailSheetContent
import com.ddd.oi.presentation.scheduledetail.content.ScheduleDetailTopBar
import com.ddd.oi.presentation.util.createBitmapFromComposable
import com.naver.maps.geometry.LatLng
import com.naver.maps.geometry.LatLngBounds
import com.naver.maps.map.CameraUpdate
import com.naver.maps.map.compose.ExperimentalNaverMapApi
import com.naver.maps.map.compose.MapUiSettings
import com.naver.maps.map.compose.Marker
import com.naver.maps.map.compose.NaverMap
import com.naver.maps.map.compose.PathOverlay
import com.naver.maps.map.compose.rememberCameraPositionState
import com.naver.maps.map.compose.rememberMarkerState
import com.naver.maps.map.overlay.OverlayImage
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toPersistentSet
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.datetime.LocalDate
import kotlinx.datetime.daysUntil
import org.orbitmvi.orbit.compose.collectAsState
import kotlin.math.roundToInt

@Composable
fun ScheduleDetailScreen(
    modifier: Modifier = Modifier,
    viewModel: ScheduleDetailViewModel = hiltViewModel(),
    navigateToSearchPlace: (Long, String) -> Unit,
    onBackClick: () -> Unit,
    navigateToEditPlace: (Long, SchedulePlace) -> Unit,
    isRefresh: Boolean = true
) {
    val uiState by viewModel.collectAsState()
    var isMapVisible by remember { mutableStateOf(true) }
    val scope = rememberCoroutineScope()
    val lazyListState = rememberLazyListState()
    var isAutoSelectionLocked by remember { mutableStateOf(false) }
    var selectedPlace by remember { mutableStateOf<SchedulePlace?>(uiState.scheduleDays.firstOrNull()?.places?.firstOrNull()) }
    var activeLocalDate by remember { mutableStateOf(uiState.schedule.startedAt) }
    var isMoreDateDialogVisible by remember { mutableStateOf(false) }
    var isTimePickerVisible by remember { mutableStateOf(false) }
    var isPlaceMemoVisible by remember { mutableStateOf(false) }
    var swipeSelectedPlace by remember { mutableStateOf<SchedulePlace?>(null) }

    BackHandler {
        scope.launch {
            isMapVisible = false
            delay(100L)
            onBackClick()
        }
    }

    LaunchedEffect(Unit) {
        viewModel.getSchedulePlaces()
    }

    OiBottomSheetScaffold(
        modifier = modifier,
        sheetDragHandle = {
            ScheduleDetailDragHandle(
                navigateToSearchPlace = {
                    navigateToSearchPlace(
                        uiState.schedule.id,
                        activeLocalDate.toString()
                    )
                },
                isMoreDateVisible = uiState.isMoreDateVisible,
                activeDate = "Day${uiState.schedule.startedAt.daysUntil(activeLocalDate) + 1} (${
                    formatToScheduleDetailActiveDate(activeLocalDate)
                })",
                onMoreDateClick = { isMoreDateDialogVisible = true },
            )
        },
        topBar = {
            ScheduleDetailTopBar(
                title = uiState.schedule.title,
                onBackClick = {
                    scope.launch {
                        isMapVisible = false
                        delay(100L)
                        onBackClick()
                    }
                }
            )
        },
        titleContent = {
            ScheduleDetailContent(
                startDate = uiState.schedule.startedAt,
                endDate = uiState.schedule.endedAt,
                transportation = uiState.schedule.transportation,
                partySet = uiState.schedule.partySet.toPersistentSet()
            )
        },
        mapContent = {
            if (isMapVisible) {
                MapContent(
                    placesList = uiState.placesForDate(date = activeLocalDate)
                )
            }
        },
        sheetContent = {
            ScheduleDetailSheetContent(
                lazyListState = lazyListState,
                flatListItems = uiState.lazyColumnList,
                activeDate = activeLocalDate,
                isAutoSelectionLocked = isAutoSelectionLocked,
                selectedPlace = selectedPlace,
                onActiveDateChanged = { newDate ->
                    if (activeLocalDate != newDate)
                        activeLocalDate = newDate
                },
                onScrollPlaceChange = { place, date ->
                    selectedPlace = place
                    if (activeLocalDate != date) activeLocalDate = date
                },
                onClickPlace = { place, date, index ->
                    scope.launch {
                        selectedPlace = place
                        activeLocalDate = date
                        isAutoSelectionLocked = true
                        lazyListState.animateScrollToItem(index)
                        isAutoSelectionLocked = false
                    }
                },
                onUserScroll = {
                    isAutoSelectionLocked = false
                },
                editTimeClick = {
                    isTimePickerVisible = true
                },
                onMemoEdit = { place ->
                    swipeSelectedPlace = place
                    isPlaceMemoVisible = true
                },
                onDelete = { place ->
                    viewModel.deleteScheduleDetail(place)
                },
                onEdit = { place ->
                    Log.d("ScheduleDetailScreen", uiState.schedule.id.toString())
                    Log.d("ScheduleDetailScreen", "onEdit: $place")
                    navigateToEditPlace(uiState.schedule.id, place)
                }
            )
        }
    )
    if (isMoreDateDialogVisible) {
        MoreDateDialog(
            activeDate = activeLocalDate,
            dateList = uiState.dateList,
            onDismiss = { isMoreDateDialogVisible = false },
            onDateClick = { selectedDate ->
                scope.launch {
                    isAutoSelectionLocked = true
                    val index = uiState.lazyColumnList.indexOfFirst { it.date == selectedDate } + 1
                    if (index != -1) {
                        lazyListState.animateScrollToItem(index)
                    }
                    activeLocalDate = selectedDate
                    isMoreDateDialogVisible = false
                }
            }
        )
    }

    if (isTimePickerVisible) {
        val time = selectedPlace?.startTime?.split(":")
        OiDialog(onDismiss = { isTimePickerVisible = false }) {
            OiTimePicker(
                hour = time?.get(0),
                minute = time?.get(1),
                onConfirm = { hour, minute ->
                    selectedPlace?.let { place ->
                        val startTime = "$hour:$minute"
                        viewModel.updateSchedulePlaceTime(place, startTime)
                        isTimePickerVisible = false
                    }
                }
            )
        }
    }

    if (isPlaceMemoVisible) {
        swipeSelectedPlace?.let { place ->
            OiDialog(onDismiss = { isPlaceMemoVisible = false }) {
                Column(modifier = Modifier.padding(vertical = 32.dp, horizontal = 16.dp)) {
                    EditMemoDialog(
                        onDismiss = { isPlaceMemoVisible = false },
                        spotName = place.spotName,
                        memo = place.memo,
                        updateMemo = { memo ->
                            viewModel.updateSchedulePlaceMemo(place, memo)
                            isPlaceMemoVisible = false
                        }
                    )
                }
            }
        }
    }
}


@Composable
private fun MoreDateDialog(
    activeDate: LocalDate,
    dateList: ImmutableList<LocalDate>,
    onDismiss: () -> Unit,
    onDateClick: (LocalDate) -> Unit
) {
    OiDialog(
        onDismiss = onDismiss
    ) {
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 24.dp, start = 16.dp), text = "날짜 선택",
            style = OiTheme.typography.bodyLargeSemibold,
            color = OiTheme.colors.textPrimary,
            textAlign = TextAlign.Start
        )
        LazyColumn(
            modifier = Modifier
                .padding(top = 16.dp)
                .heightIn(max = 277.dp)
        ) {
            items(dateList.size, key = { index -> dateList[index].toString() }) { index ->
                val selected = activeDate == dateList[index]
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { onDateClick(dateList[index]) },
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        modifier = Modifier.padding(vertical = 20.dp, horizontal = 16.dp),
                        text = "Day${index + 1} (${formatToScheduleDetailActiveDate(dateList[index])})",
                        color = if (selected) OiTheme.colors.textBrand else OiTheme.colors.textSecondary,
                        style = if (selected) OiTheme.typography.bodyLargeSemibold else OiTheme.typography.bodyLargeRegular
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    if (selected) Icon(
                        modifier = Modifier.padding(end = 16.dp),
                        imageVector = ImageVector.vectorResource(R.drawable.ic_check_circle),
                        contentDescription = "check",
                        tint = OiTheme.colors.iconBrand
                    )
                }
            }
        }
    }
}

@Composable
private fun EditMemoDialog(
    onDismiss: () -> Unit,
    spotName: String,
    memo: String,
    updateMemo: (String) -> Unit
) {
    var memoText by remember { mutableStateOf(memo) }
    OiDialog(onDismiss = onDismiss) {
        Column(modifier = Modifier.padding(vertical = 32.dp, horizontal = 16.dp)) {
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                Icon(
                    imageVector = ImageVector.vectorResource(R.drawable.ic_gps),
                    contentDescription = null,
                    tint = OiTheme.colors.iconBrand
                )
                Text(
                    modifier = Modifier.fillMaxWidth(0.7f),
                    text = spotName, style = OiTheme.typography.bodyLargeSemibold,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.weight(1f))
                Text(
                    text =
                        buildAnnotatedString {
                            withStyle(style = SpanStyle(color = OiTheme.colors.textBrand)) {
                                append(memoText.length.toString())
                            }
                            withStyle(style = SpanStyle(color = OiTheme.colors.textTertiary)) {
                                append("/100")
                            }
                        },
                    style = OiTheme.typography.bodySmallMedium
                )
            }
            OiBasicTextField(
                modifier = Modifier.padding(top = 16.dp, bottom = 20.dp),
                memo = memoText,
                onValueChange = { memoText = it }
            )
            OiButton(
                modifier = Modifier.fillMaxWidth(),
                style = OiButtonStyle.Large48Oval,
                colorType = OiButtonColorType.Primary,
                title = stringResource(R.string.save),
                onClick = { updateMemo(memoText) }
            )
        }
    }
}

@OptIn(ExperimentalNaverMapApi::class)
@Composable
private fun MapContent(
    placesList: ImmutableList<SchedulePlace>
) {
    val context = LocalContext.current
    val compositionContext = rememberCompositionContext()
    val density = LocalDensity.current // 현재 화면 밀도(Density) 가져오기

    val markerWidthPx = with(density) { 40.dp.toPx().roundToInt() }
    val markerHeightPx = with(density) { 40.dp.toPx().roundToInt() }

    var markerOverlays by remember { mutableStateOf<List<OverlayImage>>(emptyList()) }

    val latLngList = placesList.map { LatLng(it.latitude, it.longitude) }
    val cameraPositionState = rememberCameraPositionState()
    val mapUiSetting = MapUiSettings(
        isZoomControlEnabled = false,
        logoGravity = Gravity.TOP

    )
    LaunchedEffect(placesList, density) {
        val markerWidthPx = with(density) { 40.dp.toPx().roundToInt() }
        val markerHeightPx = with(density) { 40.dp.toPx().roundToInt() }

        val overlays = placesList.mapIndexed { index, place ->
            val bitmap = createBitmapFromComposable(
                context,
                compositionContext,
                markerWidthPx,
                markerHeightPx
            ) {
                NumberedMarker(
                    number = index + 1,
                    color = getPlaceCategoryColor(place.category)
                )
            }
            OverlayImage.fromBitmap(bitmap)
        }
        markerOverlays = overlays
    }

    LaunchedEffect(latLngList) {
        if (latLngList.isNotEmpty()) {
            val bounds = LatLngBounds.Builder().apply {
                latLngList.forEach { include(it) }
            }.build()

            val cameraUpdate = CameraUpdate.fitBounds(bounds, 100)

            cameraPositionState.move(cameraUpdate)
        }
    }

    NaverMap(
        uiSettings = mapUiSetting,
        cameraPositionState = cameraPositionState
    ) {
        markerOverlays.forEachIndexed { index, overlay ->
            // placesList는 항상 최신 상태이므로 안전하게 위치를 가져올 수 있습니다.
            val item = placesList.getOrNull(index) ?: return@forEachIndexed
            Marker(
                state = rememberMarkerState(position = LatLng(item.latitude, item.longitude)),
                icon = overlay,
            )
        }
        if (placesList.size >= 2) {
            PathOverlay(
                coords = latLngList,
                patternImage = OverlayImage.fromResource(R.drawable.ic_map_route),
                patternInterval = 15.dp,
                width = 80.dp,
                color = Color.Transparent,
                outlineColor = Color.Transparent
            )
        }
    }
}

@Composable
fun NumberedMarker(number: Int, color: Color) {
    // 마커의 배경 (원형)
    Box(
        modifier = Modifier
            .clip(CircleShape) // 원 모양으로 자르기
            .background(color) // 카테고리별 색상 적용
            .border(1.dp, color = Color.White, shape = CircleShape)
            .size(40.dp),
        contentAlignment = Alignment.Center
    ) {
        // 순서 텍스트
        Text(
            modifier = Modifier.padding(horizontal = 6.dp),
            text = number.toString(),
            color = Color.White,
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold
        )
    }
}