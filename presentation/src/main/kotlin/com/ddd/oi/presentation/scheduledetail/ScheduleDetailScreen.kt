package com.ddd.oi.presentation.scheduledetail

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.naver.maps.map.compose.ExperimentalNaverMapApi
import com.naver.maps.map.compose.NaverMap

@OptIn(ExperimentalNaverMapApi::class)
@Composable
fun ScheduleDetailScreen(modifier: Modifier = Modifier) {
    NaverMap()
}