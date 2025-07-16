package com.ddd.oi.presentation.scheduledetail

import android.view.Gravity
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ddd.oi.domain.model.schedule.Place
import com.ddd.oi.presentation.R
import com.ddd.oi.presentation.core.designsystem.component.common.OiBasicTextField
import com.ddd.oi.presentation.core.designsystem.component.common.OiButton
import com.ddd.oi.presentation.core.designsystem.component.common.OiButtonColorType
import com.ddd.oi.presentation.core.designsystem.component.common.OiButtonStyle
import com.ddd.oi.presentation.core.designsystem.component.dialog.OiDialog
import com.ddd.oi.presentation.core.designsystem.component.mapper.formatToScheduleDetailActiveDate
import com.ddd.oi.presentation.core.designsystem.component.oibottomsheetscaffold.OiBottomSheetScaffold
import com.ddd.oi.presentation.core.designsystem.component.oitimepicker.OiTimePicker
import com.ddd.oi.presentation.core.designsystem.theme.OiTheme
import com.ddd.oi.presentation.scheduledetail.content.ScheduleDetailContent
import com.ddd.oi.presentation.scheduledetail.content.ScheduleDetailDragHandle
import com.ddd.oi.presentation.scheduledetail.content.ScheduleDetailSheetContent
import com.ddd.oi.presentation.scheduledetail.content.ScheduleDetailTopBar
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

@Composable
fun ScheduleDetailScreen(
    modifier: Modifier = Modifier,
    viewModel: ScheduleDetailViewModel = hiltViewModel(),
    onBackClick: () -> Unit
) {
    val uiState by viewModel.collectAsState()
    var isMapVisible by remember { mutableStateOf(true) }
    val scope = rememberCoroutineScope()
    val lazyListState = rememberLazyListState()
    var isAutoSelectionLocked by remember { mutableStateOf(false) }
    var selectedPlace by remember { mutableStateOf<Place?>(uiState.scheduleDays.firstOrNull()?.places?.firstOrNull()) }
    var activeLocalDate by remember { mutableStateOf(uiState.schedule.startedAt) }
    var isMoreDateDialogVisible by remember { mutableStateOf(false) }
    var isTimePickerVisible by remember { mutableStateOf(false) }
    var isPlaceMemoVisible by remember { mutableStateOf(false) }

    BackHandler {
        scope.launch {
            isMapVisible = false
            delay(100L)
            onBackClick()
        }
    }

    LaunchedEffect(activeLocalDate) {
        selectedPlace = uiState.placesForDate(activeLocalDate).firstOrNull()

    }

    OiBottomSheetScaffold(
        modifier = modifier,
        sheetDragHandle = {
            ScheduleDetailDragHandle(
                navigateToSearchPlace = {},
                isMoreDateVisible = uiState.isMoreDateVisible,
                activeDate = "Day${uiState.schedule.startedAt.daysUntil(activeLocalDate) + 1} (${
                    formatToScheduleDetailActiveDate(
                        activeLocalDate
                    )
                })",
                onMoreDateClick = { isMoreDateDialogVisible = true }
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
                scheduleDays = uiState.scheduleDays,
                activeDate = activeLocalDate,
                isAutoSelectionLocked = isAutoSelectionLocked,
                selectedPlace = selectedPlace,
                onActiveDateChanged = { newDate ->
                    if (!isAutoSelectionLocked)
                        activeLocalDate = newDate
                },
                onScrollPlaceChange = { place ->
                    selectedPlace = place
                },
                onClickPlace = { place, date ->
                    isAutoSelectionLocked = true
                    selectedPlace = place
                    activeLocalDate = date
                },
                onUserScroll = {
                    isAutoSelectionLocked = false
                },
                editTimeClick = {
                    isTimePickerVisible = true
                },
                onMemoEdit = { place ->
                    selectedPlace = place
                    isPlaceMemoVisible = true
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
                isAutoSelectionLocked = true
                activeLocalDate = selectedDate
                isMoreDateDialogVisible = false
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
                    selectedPlace?.let {

                    }
                }
            )
        }
    }

    if (isPlaceMemoVisible) {
        OiDialog(onDismiss = { isPlaceMemoVisible = false }) {
            Column(modifier = Modifier.padding(vertical = 32.dp, horizontal = 16.dp)) {
                EditMemoDialog(
                    onDismiss = { isPlaceMemoVisible = false },
                    spotName = selectedPlace?.spotName ?: "",
                    memo = selectedPlace?.memo ?: ""
                )
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
                Text(text = spotName, style = OiTheme.typography.bodyLargeSemibold)
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
                textStringRes = R.string.save,
                onClick = {

                }
            )
        }
    }
}

@OptIn(ExperimentalNaverMapApi::class)
@Composable
private fun MapContent(
    placesList: ImmutableList<Place>
) {
    val latLngList = placesList.map { LatLng(it.latitude, it.longitude) }
    val cameraPositionState = rememberCameraPositionState()
    val mapUiSetting = MapUiSettings(
        isZoomControlEnabled = false,
        logoGravity = Gravity.TOP

    )
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
        latLngList.forEach {
            Marker(
                state = rememberMarkerState(position = LatLng(it.latitude, it.longitude)),
                icon = OverlayImage.fromResource(R.drawable.ic_gps),
                iconTintColor = Color.Blue
            )
        }
        if (latLngList.size >= 2) {
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