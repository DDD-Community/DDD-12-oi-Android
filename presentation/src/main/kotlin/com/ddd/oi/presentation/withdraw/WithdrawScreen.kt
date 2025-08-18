package com.ddd.oi.presentation.withdraw

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ddd.oi.presentation.core.designsystem.component.common.OiButton
import com.ddd.oi.presentation.core.designsystem.component.common.OiButtonColorType
import com.ddd.oi.presentation.core.designsystem.component.common.OiButtonStyle
import com.ddd.oi.presentation.core.designsystem.component.common.OiHeader
import com.ddd.oi.presentation.core.designsystem.theme.OiTheme
import com.ddd.oi.presentation.core.designsystem.theme.white
import com.ddd.oi.presentation.core.designsystem.util.Dimens
import com.ddd.oi.presentation.core.designsystem.util.rememberThrottledNavigation

@Composable
fun WithdrawScreen(
    onBack: () -> Unit = {},
    onWithdraw: () -> Unit = {},
    viewModel: WithdrawViewModel = hiltViewModel()
) {
    val throttledNavigation = rememberThrottledNavigation()
    
    WithdrawContent(
        onBack = { throttledNavigation(onBack) },
        onWithdraw = { throttledNavigation(onWithdraw) }
    )
}

@Composable
private fun WithdrawContent(
    modifier: Modifier = Modifier,
    onBack: () -> Unit = {},
    onWithdraw: () -> Unit = {}
) {
    Scaffold(
        modifier = modifier
            .fillMaxSize()
            .background(white),
        containerColor = white,
        topBar = {
            OiHeader(
                onLeftClick = onBack,
                title = "회원탈퇴",
                isDividerVisible = true
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = Dimens.paddingMedium),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "정말 탈퇴하시겠습니까?",
                style = OiTheme.typography.headlineMediumBold,
                color = OiTheme.colors.textPrimary,
                textAlign = TextAlign.Center
            )
            
            Text(
                modifier = Modifier.padding(top = Dimens.paddingMedium),
                text = "탈퇴하시면 모든 데이터가 삭제되며\n복구할 수 없습니다.",
                style = OiTheme.typography.bodyLargeMedium,
                color = OiTheme.colors.textSecondary,
                textAlign = TextAlign.Center
            )
            
            OiButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 48.dp),
                style = OiButtonStyle.Large48Oval,
                colorType = OiButtonColorType.Danger,
                title = "탈퇴하기",
                onClick = onWithdraw
            )
            
            OiButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = Dimens.paddingMediumSmall),
                style = OiButtonStyle.Large48Oval,
                colorType = OiButtonColorType.Secondary,
                title = "취소",
                onClick = onBack
            )
        }
    }
}

@Preview
@Composable
private fun WithdrawScreenPreview() {
    WithdrawContent()
}