package com.ddd.oi.presentation.withdraw

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
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ddd.oi.presentation.R
import com.ddd.oi.presentation.core.designsystem.component.common.OiButton
import com.ddd.oi.presentation.core.designsystem.component.common.OiButtonColorType
import com.ddd.oi.presentation.core.designsystem.component.common.OiButtonStyle
import com.ddd.oi.presentation.core.designsystem.component.common.OiHeader
import com.ddd.oi.presentation.core.designsystem.component.dialog.OiDialog
import com.ddd.oi.presentation.core.designsystem.component.snackbar.OiSnackbarData
import com.ddd.oi.presentation.core.designsystem.component.snackbar.OiSnackbarHost
import com.ddd.oi.presentation.core.designsystem.component.snackbar.SnackbarType
import com.ddd.oi.presentation.core.designsystem.component.snackbar.rememberSnackbarController
import com.ddd.oi.presentation.core.designsystem.theme.OiTheme
import com.ddd.oi.presentation.core.designsystem.theme.white
import com.ddd.oi.presentation.core.designsystem.util.rememberThrottledNavigation
import kotlinx.coroutines.launch

@Composable
fun WithdrawScreen(
    onBack: () -> Unit = {},
    onWithdraw: () -> Unit = {},
    viewModel: WithdrawViewModel = hiltViewModel()
) {
    val throttledNavigation = rememberThrottledNavigation()
    val uiState by viewModel.uiState.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }
    val snackbarController = rememberSnackbarController(snackbarHostState)
    val coroutineScope = rememberCoroutineScope()

    // 회원탈퇴 성공 시 첫화면으로 이동
    LaunchedEffect(uiState.successMessage) {
        if (uiState.successMessage?.contains("회원탈퇴") == true) {
            onWithdraw() // 첫화면으로 네비게이션
            return@LaunchedEffect
        }
    }
    
    // 에러 메시지 표시
    LaunchedEffect(uiState.error) {
        uiState.error?.let { error ->
            coroutineScope.launch {
                snackbarController.showSnackbar(
                    OiSnackbarData(
                        message = error,
                        type = SnackbarType.WARNING
                    )
                )
            }
            viewModel.clearError()
        }
    }

    WithdrawContent(
        uiState = uiState,
        onBack = { throttledNavigation(onBack) },
        onWithdraw = { viewModel.withdrawUser() },
        onReasonSelected = { viewModel.selectReason(it) },
        onAgreementChanged = { viewModel.setAgreement(it) },
        snackbarHostState = snackbarHostState,
        snackbarController = snackbarController
    )
}

@Composable
private fun WithdrawContent(
    modifier: Modifier = Modifier,
    uiState: WithdrawUiState = WithdrawUiState(),
    onBack: () -> Unit = {},
    onWithdraw: () -> Unit = {},
    onReasonSelected: (String) -> Unit = {},
    onAgreementChanged: (Boolean) -> Unit = {},
    snackbarHostState: SnackbarHostState = remember { SnackbarHostState() },
    snackbarController: com.ddd.oi.presentation.core.designsystem.component.snackbar.SnackbarController = rememberSnackbarController(snackbarHostState)
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
        },
        snackbarHost = {
            OiSnackbarHost(
                hostState = snackbarHostState,
                controller = snackbarController,
                modifier = Modifier.padding(bottom = 16.dp)
            )
        }
    ) { padding ->
        var showReasonDialog by remember { mutableStateOf(false) }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .verticalScroll(rememberScrollState()),
        ) {
            // 첫 번째 영역: 프로필 아이콘과 메시지
            ProfileSection(nickname = uiState.nickname)

            // 두 번째 영역: 탈퇴 사유 선택
            WithdrawReasonSection(
                modifier = Modifier.padding(top = 8.dp),
                selectedReason = uiState.selectedReason,
                onReasonClick = { showReasonDialog = true }
            )

            Spacer(modifier = Modifier.weight(1F))

            // 세 번째 영역: 동의 체크박스
            AgreementSection(
                isAgreed = uiState.isAgreed,
                onAgreementChanged = onAgreementChanged
            )

            // 탈퇴하기 버튼
            OiButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                style = OiButtonStyle.Large48Oval,
                colorType = if (uiState.canWithdraw) OiButtonColorType.Primary else OiButtonColorType.Secondary,
                title = "탈퇴하기",
                enabled = uiState.canWithdraw,
                onClick = onWithdraw
            )
        }

        // 탈퇴 사유 선택 다이얼로그
        if (showReasonDialog) {
            WithdrawReasonDialog(
                onDismiss = { showReasonDialog = false },
                onReasonSelected = { reason ->
                    onReasonSelected(reason)
                    showReasonDialog = false
                }
            )
        }
    }
}

@Composable
private fun ProfileSection(
    nickname: String
) {
    Column(
        modifier = Modifier
            .padding(top = 32.dp)
            .padding(horizontal = 16.dp)
            .fillMaxWidth(),
    ) {
        // 프로필 아이콘
        Icon(
            modifier = Modifier
                .size(80.dp)
                .background(
                    shape = CircleShape,
                    color = OiTheme.colors.backgroundContents
                )
                .border(
                    width = 1.dp,
                    color = OiTheme.colors.borderPrimary,
                    shape = CircleShape
                ),
            painter = painterResource(R.drawable.ic_oi_default_profile),
            contentDescription = "",
            tint = Color.Unspecified,
        )

        // 메시지
        Text(
            modifier = Modifier.padding(top = 16.dp),
            text = buildAnnotatedString {
                withStyle(style = SpanStyle(color = OiTheme.colors.textBrand)) {
                    append(nickname)
                }
                append("님과 이별인가요?\n너무 아쉬워요")
            },
            style = OiTheme.typography.headlineSmallBold,
            color = OiTheme.colors.textPrimary,
        )

        Text(
            modifier = Modifier.padding(top = 8.dp),
            text = "탈퇴하면 내 정보, 내 일정 등 모든 정보가 삭제됩니다.\n삭제된 정보는 다시 복구할 수 없어요.",
            style = OiTheme.typography.bodyMediumRegular,
            color = OiTheme.colors.textSecondary,
        )
    }
}

@Composable
private fun WithdrawReasonSection(
    modifier: Modifier = Modifier,
    selectedReason: String,
    onReasonClick: () -> Unit
) {
    Column(
        modifier = modifier
            .padding(horizontal = 16.dp)
            .fillMaxWidth(),
    ) {
        Text(
            modifier = Modifier.padding(top = 32.dp),
            text = "탈퇴하시려는 이유가 궁금해요",
            style = OiTheme.typography.headlineSmallBold,
            color = OiTheme.colors.textPrimary
        )

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp)
                .height(48.dp)
                .border(
                    width = 1.dp,
                    color = OiTheme.colors.borderPrimary,
                    shape = RoundedCornerShape(8.dp)
                )
                .clickable { onReasonClick() }
                .padding(horizontal = 16.dp),
            contentAlignment = Alignment.CenterStart
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = selectedReason.ifEmpty { "선택해주세요" },
                    style = OiTheme.typography.bodyMediumRegular,
                    color = if (selectedReason.isEmpty()) OiTheme.colors.textDisabled else OiTheme.colors.textPrimary
                )

                Icon(
                    painter = painterResource(R.drawable.ic_chevron_down),
                    contentDescription = "선택",
                    modifier = Modifier.size(20.dp),
                    tint = OiTheme.colors.iconSecondary
                )
            }
        }
    }
}

@Composable
private fun AgreementSection(
    isAgreed: Boolean,
    onAgreementChanged: (Boolean) -> Unit
) {
    Row(
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .fillMaxWidth()
            .clickable { onAgreementChanged(!isAgreed) },
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Checkbox(
            modifier = Modifier.size(16.dp),
            checked = isAgreed,
            onCheckedChange = onAgreementChanged,
            colors = CheckboxDefaults.colors(
                checkedColor = OiTheme.colors.textBrand,
                uncheckedColor = OiTheme.colors.borderPrimary,
                checkmarkColor = Color.White
            )
        )

        Text(
            modifier = Modifier.padding(start = 8.dp),
            text = "위 내용을 모두 확인하였으며, 탈퇴 및 정보 삭제에 동의합니다.",
            style = OiTheme.typography.bodySmallMedium,
            color = OiTheme.colors.textPrimary
        )
    }
}

@Composable
private fun WithdrawReasonDialog(
    onDismiss: () -> Unit,
    onReasonSelected: (String) -> Unit
) {
    val reasons = listOf(
        "더 이상 사용하지 않아요",
        "불편한 기능이 있어요",
        "다른 앱을 사용해요",
        "개인정보가 우려돼요",
        "기타"
    )

    OiDialog(onDismiss = onDismiss) {
        Column(
            modifier = Modifier.padding(vertical = 32.dp, horizontal = 16.dp)
        ) {
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 32.dp),
                text = "탈퇴사유 선택",
                style = OiTheme.typography.headLineSmallSemibold,
                color = OiTheme.colors.textPrimary,
                textAlign = TextAlign.Center
            )

            reasons.forEach { reason ->
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp)
                        .clickable { onReasonSelected(reason) },
                ) {
                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 12.dp)
                            .align(Alignment.CenterStart),
                        text = reason,
                        style = OiTheme.typography.bodyMediumMedium,
                        color = OiTheme.colors.textPrimary,
                        overflow = TextOverflow.Ellipsis,
                    )
                }
            }
        }
    }
}

@Preview
@Composable
private fun WithdrawScreenPreview() {
    WithdrawContent()
}