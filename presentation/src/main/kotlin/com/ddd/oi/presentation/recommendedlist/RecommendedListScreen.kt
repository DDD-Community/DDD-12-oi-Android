package com.ddd.oi.presentation.recommendedlist

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.ddd.oi.presentation.core.designsystem.component.common.OiButton
import com.ddd.oi.presentation.core.designsystem.component.common.OiButtonStyle

@Composable
fun RecommendedListScreen(
    modifier: Modifier = Modifier,
    onNavigateToDetail: () -> Unit = {}
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Recommended List Screen")
        
        OiButton(
            title = "Go to Detail",
            style = OiButtonStyle.Large48Oval,
            onClick = onNavigateToDetail,
            modifier = Modifier.padding(top = 16.dp)
        )
    }
}