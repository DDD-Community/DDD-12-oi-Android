package com.ddd.oi.presentation.recommendeddetail

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.ddd.oi.presentation.core.designsystem.component.common.OiHeader

@Composable
fun RecommendedDetailScreen(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize(),
    ) {
        OiHeader(
            onLeftClick = {},
            title = "경복궁 철쭉 스팟 총정리",
        )
    }
}