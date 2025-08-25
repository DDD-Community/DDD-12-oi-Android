import android.window.SplashScreen
import androidx.compose.runtime.Composable

//package com.ddd.oi.presentation.scheduledetail
//
//import android.util.Log
//import android.view.Gravity
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.LaunchedEffect
//import androidx.compose.runtime.remember
//import androidx.compose.ui.geometry.Offset
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.platform.LocalContext
//import androidx.compose.ui.platform.LocalDensity
//import androidx.compose.ui.unit.dp
//import com.ddd.oi.domain.model.schedule.SchedulePlace
//import com.ddd.oi.presentation.R
//import com.ddd.oi.presentation.core.designsystem.component.mapper.getPlaceCategoryColor
//import com.ddd.oi.presentation.util.pinMarkerBitmap
//import com.naver.maps.geometry.LatLng
//import com.naver.maps.geometry.LatLngBounds
//import com.naver.maps.map.CameraUpdate
//import com.naver.maps.map.compose.ExperimentalNaverMapApi
//import com.naver.maps.map.compose.MapUiSettings
//import com.naver.maps.map.compose.Marker
//import com.naver.maps.map.compose.NaverMap
//import com.naver.maps.map.compose.PathOverlay
//import com.naver.maps.map.compose.rememberCameraPositionState
//import com.naver.maps.map.compose.rememberMarkerState
//import com.naver.maps.map.overlay.OverlayImage
//
//@OptIn(ExperimentalNaverMapApi::class)
//@Composable
//fun NaverMapTest() {
//    val context = LocalContext.current
//    val placesList = mockData
//    val density = LocalDensity.current
//
//    val latLngList = placesList.map { LatLng(it.latitude, it.longitude) }
//    val cameraPositionState = rememberCameraPositionState()
//    val mapUiSetting = MapUiSettings(
//        isZoomControlEnabled = false,
//        logoGravity = Gravity.TOP
//    )
//
//    LaunchedEffect(latLngList) {
//        if (latLngList.isNotEmpty()) {
//            val bounds = LatLngBounds.Builder().apply {
//                latLngList.forEach { include(it) }
//            }.build()
//
//            val cameraUpdate = CameraUpdate.fitBounds(bounds, 100)
//
//            cameraPositionState.move(cameraUpdate)
//        }
//    }
//
//    NaverMap(
//        uiSettings = mapUiSetting,
//        cameraPositionState = cameraPositionState
//    ) {
//        placesList.forEachIndexed { index, place ->
//            Log.d("ScheduleDetailScreen", place.toString())
//            val markerSizePx = with(density) { 40.dp.toPx() }.toInt()
//            val markerBitmap = remember(place) {
//                pinMarkerBitmap(
//                    sizePx = markerSizePx,
//                    number = index + 1,
//                    backgroundColor = getPlaceCategoryColor(place.category)
//                )
//            }
//
//            Marker(
//                state = rememberMarkerState(position = LatLng(place.latitude, place.longitude)),
//                icon = OverlayImage.fromBitmap(markerBitmap),
//                anchor = Offset(0.5f, 0.5f)
//            )
//        }
//        if (placesList.size >= 2) {
//            PathOverlay(
//                coords = latLngList,
//                patternImage = OverlayImage.fromResource(R.drawable.ic_map_route),
//                patternInterval = 15.dp,
//                width = 80.dp,
//                color = Color.Transparent,
//                outlineColor = Color.Transparent
//            )
//        }
//    }
//}
//
//val mockData = listOf<SchedulePlace>(
//    SchedulePlace(
//        id = 1,
//        startTime = "12:60",
//        targetDate = "",
//        spotName = "공덕시장",
//        memo = "test",
//        latitude = 37.545649,
//        longitude = 126.953403,
//        category = "음식점"
//    ),
//    SchedulePlace(
//        id = 2,
//        startTime = "12:60",
//        targetDate = "",
//        spotName = "서울역",
//        memo = "test",
//        latitude = 37.555429,
//        longitude = 126.971738,
//        category = "카페"
//    ),
//    SchedulePlace(
//        id = 3,
//        startTime = "12:60",
//        targetDate = "",
//        spotName = "홍대거리",
//        memo = "test",
//        latitude = 37.557278,
//        longitude = 126.925111,
//        category = "숙박시설"
//    ),
//)
//

