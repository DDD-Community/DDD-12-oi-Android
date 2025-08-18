package com.ddd.oi.presentation.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ddd.oi.presentation.R
import com.ddd.oi.presentation.core.designsystem.component.common.OiButton
import com.ddd.oi.presentation.core.designsystem.component.common.OiButtonColorType
import com.ddd.oi.presentation.core.designsystem.component.common.OiButtonStyle
import com.ddd.oi.presentation.core.designsystem.component.common.OiHeader
import com.ddd.oi.presentation.core.designsystem.component.common.OiTextField
import com.ddd.oi.presentation.core.designsystem.component.dialog.OiDialog
import com.ddd.oi.presentation.core.designsystem.component.snackbar.OiSnackbarData
import com.ddd.oi.presentation.core.designsystem.component.snackbar.OiSnackbarHost
import com.ddd.oi.presentation.core.designsystem.component.snackbar.SnackbarType
import com.ddd.oi.presentation.core.designsystem.component.snackbar.rememberSnackbarController
import com.ddd.oi.presentation.core.designsystem.theme.OiTheme
import com.ddd.oi.presentation.core.designsystem.theme.white
import com.ddd.oi.presentation.core.designsystem.util.Dimens
import com.ddd.oi.presentation.core.designsystem.util.OiTextFieldDimens
import com.ddd.oi.presentation.core.designsystem.util.rememberThrottledNavigation
import kotlinx.coroutines.launch

sealed class ProfileMenuItem(
    val title: String,
    val rightText: String? = null,
    val onClick: () -> Unit
) {
    class ChangeNickname(nickname: String, onClick: () -> Unit) : ProfileMenuItem("닉네임 변경", nickname, onClick)
    class Logout(onClick: () -> Unit) : ProfileMenuItem("로그아웃", null, onClick)
    class WithdrawAccount(onClick: () -> Unit) : ProfileMenuItem("회원탈퇴", null, onClick)
}

@Composable
fun ProfileScreen(
    onBack: () -> Unit = {},
    onLogout: () -> Unit = {},
    onWithdrawAccount: () -> Unit = {},
    viewModel: ProfileViewModel = hiltViewModel()
) {
    val throttledNavigation = rememberThrottledNavigation()
    val uiState by viewModel.uiState.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }
    val snackbarController = rememberSnackbarController(snackbarHostState)
    val coroutineScope = rememberCoroutineScope()
    
    // 성공 메시지 표시
    LaunchedEffect(uiState.successMessage) {
        uiState.successMessage?.let { message ->
            coroutineScope.launch {
                snackbarController.showSnackbar(
                    OiSnackbarData(
                        message = message,
                        type = SnackbarType.SUCCESS
                    )
                )
            }
            viewModel.clearSuccessMessage()
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
    
    ProfileContent(
        uiState = uiState,
        onBack = { throttledNavigation(onBack) },
        onLogout = { throttledNavigation(onLogout) },
        onWithdrawAccount = { throttledNavigation(onWithdrawAccount) },
        onUpdateNickname = { nickname -> viewModel.updateNickname(nickname) },
        snackbarHostState = snackbarHostState,
        snackbarController = snackbarController
    )
}

@Composable
private fun ProfileContent(
    modifier: Modifier = Modifier,
    uiState: ProfileUiState = ProfileUiState(),
    onBack: () -> Unit = {},
    onLogout: () -> Unit = {},
    onWithdrawAccount: () -> Unit = {},
    onUpdateNickname: (String) -> Unit = {},
    snackbarHostState: SnackbarHostState = remember { SnackbarHostState() },
    snackbarController: com.ddd.oi.presentation.core.designsystem.component.snackbar.SnackbarController = rememberSnackbarController(snackbarHostState)
) {
    var showChangeNicknameDialog by remember { mutableStateOf(false) }
    var showLogoutDialog by remember { mutableStateOf(false) }
    
    val nickname = uiState.userInfo?.name ?: "오늘의이동"
    
    Scaffold(
        modifier = modifier
            .fillMaxSize()
            .background(white),
        containerColor = white,
        topBar = {
            OiHeader(
                onLeftClick = onBack,
                title = "프로필 관리",
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
        ProfileMenuList(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            nickname = nickname,
            onChangeNickname = { showChangeNicknameDialog = true },
            onLogout = { showLogoutDialog = true },
            onWithdrawAccount = onWithdrawAccount
        )
        
        // 닉네임 변경 다이얼로그
        if (showChangeNicknameDialog) {
            ChangeNicknameDialog(
                currentNickname = nickname,
                onDismiss = { showChangeNicknameDialog = false },
                onConfirm = { newName ->
                    onUpdateNickname(newName)
                    showChangeNicknameDialog = false
                }
            )
        }
        
        // 로그아웃 다이얼로그
        if (showLogoutDialog) {
            LogoutDialog(
                onDismiss = { showLogoutDialog = false },
                onConfirm = {
                    onLogout()
                    showLogoutDialog = false
                }
            )
        }
    }
}

@Composable
private fun ProfileMenuList(
    modifier: Modifier = Modifier,
    nickname: String = "오늘의이동",
    onChangeNickname: () -> Unit = {},
    onLogout: () -> Unit = {},
    onWithdrawAccount: () -> Unit = {}
) {
    LazyColumn(
        modifier = modifier
            .fillMaxWidth(),
    ) {
        items(
            listOf(
                ProfileMenuItem.ChangeNickname(nickname = nickname, onClick = onChangeNickname),
                ProfileMenuItem.Logout(onClick = onLogout),
                ProfileMenuItem.WithdrawAccount(onClick = onWithdrawAccount)
            )
        ) { item ->
            ProfileMenuItemView(
                menuItem = item
            )
        }
    }
}

@Composable
private fun ProfileMenuItemView(
    modifier: Modifier = Modifier,
    menuItem: ProfileMenuItem
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .height(56.dp)
            .clickable { menuItem.onClick() },
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        Text(
            text = menuItem.title,
            style = OiTheme.typography.bodyLargeMedium,
            color = OiTheme.colors.textPrimary
        )

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            menuItem.rightText?.let { rightText ->
                Text(
                    text = rightText,
                    style = OiTheme.typography.bodyLargeSemibold,
                    color = OiTheme.colors.textBrand
                )
            }
            
            Icon(
                modifier = Modifier.size(20.dp),
                painter = painterResource(R.drawable.ic_chevron_right),
                contentDescription = "이동",
                tint = OiTheme.colors.iconSecondary
            )
        }
    }
}

@Composable
private fun ChangeNicknameDialog(
    currentNickname: String,
    onDismiss: () -> Unit,
    onConfirm: (String) -> Unit
) {
    var newNickname by remember { mutableStateOf("") }
    
    // 한글, 영어, 숫자만 허용하는 정규식
    val validPattern = Regex("^[가-힣a-zA-Z0-9]*$")
    val isValidNickname = newNickname.matches(validPattern) && newNickname.isNotBlank()
    
    OiDialog(onDismiss = onDismiss) {
        Column(
            modifier = Modifier.padding(horizontal = Dimens.paddingMedium)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = Dimens.paddingLarge, bottom = Dimens.paddingMedium),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    modifier = Modifier.size(16.dp),
                    painter = painterResource(R.drawable.ic_edit),
                    contentDescription = "닉네임 변경 아이콘",
                    tint = OiTheme.colors.backgroundSelected
                )
                
                Text(
                    modifier = Modifier
                        .padding(start = 8.dp)
                        .weight(1f),
                    text = "닉네임 변경",
                    style = OiTheme.typography.bodyLargeSemibold,
                    color = OiTheme.colors.textPrimary
                )
                
                Text(
                    text = buildAnnotatedString {
                        withStyle(style = SpanStyle(color = OiTheme.colors.textBrand)) {
                            append("${newNickname.length}")
                        }
                        withStyle(style = SpanStyle(color = OiTheme.colors.textSecondary)) {
                            append("/15")
                        }
                    },
                    style = OiTheme.typography.bodySmallMedium
                )
            }
            
            ErrorSupportTextField(
                modifier = Modifier.fillMaxWidth(),
                text = newNickname,
                onTextChanged = { if (it.length <= 15) newNickname = it },
                hint = currentNickname,
                isError = !isValidNickname && newNickname.isNotEmpty()
            )
            
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp, bottom = 20.dp),
                text = "닉네임은 한글, 숫자, 영어만 사용할 수 있어요",
                style = OiTheme.typography.bodySmallRegular,
                color = if (isValidNickname) OiTheme.colors.textSecondary else Color(0xFFFB2C36)
            )
            
            OiButton(
                modifier = Modifier.fillMaxWidth(),
                style = OiButtonStyle.Large48Oval,
                colorType = if (isValidNickname) OiButtonColorType.Primary else OiButtonColorType.Secondary,
                title = "변경하기",
                enabled = isValidNickname,
                onClick = { onConfirm(newNickname) }
            )
            
            OiButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        top = Dimens.paddingMediumSmall,
                        bottom = Dimens.paddingLarge
                    ),
                style = OiButtonStyle.Large48Oval,
                colorType = OiButtonColorType.Secondary,
                title = "취소하기",
                onClick = onDismiss
            )
        }
    }
}

@Composable
private fun LogoutDialog(
    onDismiss: () -> Unit,
    onConfirm: () -> Unit
) {
    OiDialog(onDismiss = onDismiss) {
        Column(
            modifier = Modifier.padding(horizontal = Dimens.paddingMedium),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(
                modifier = Modifier.padding(vertical = Dimens.paddingLarge),
                text = "로그아웃 하시겠습니까?",
                style = OiTheme.typography.headlineSmallBold,
                color = OiTheme.colors.textPrimary,
                textAlign = TextAlign.Center
            )
            
            OiButton(
                modifier = Modifier.fillMaxWidth(),
                style = OiButtonStyle.Large48Oval,
                colorType = OiButtonColorType.Primary,
                title = "로그아웃",
                onClick = onConfirm
            )
            
            OiButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        top = Dimens.paddingMediumSmall,
                        bottom = Dimens.paddingLarge
                    ),
                style = OiButtonStyle.Large48Oval,
                colorType = OiButtonColorType.Secondary,
                title = "취소",
                onClick = onDismiss
            )
        }
    }
}

@Composable
private fun ErrorSupportTextField(
    modifier: Modifier = Modifier,
    text: String = "",
    hint: String = "",
    onTextChanged: (String) -> Unit = {},
    isError: Boolean = false
) {
    var isFocused by remember { mutableStateOf(false) }

    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(OiTextFieldDimens.height)
            .clip(RoundedCornerShape(OiTextFieldDimens.roundedRectRadius))
            .border(
                width = OiTextFieldDimens.stroke,
                color = when {
                    isError -> Color(0xFFFB2C36)
                    isFocused -> OiTheme.colors.borderFocus
                    else -> OiTheme.colors.textDisabled
                },
                shape = RoundedCornerShape(OiTextFieldDimens.roundedRectRadius)
            )
            .padding(horizontal = OiTextFieldDimens.horizontalPadding),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier.weight(1F)
        ) {
            SelectionContainer {
                BasicTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.CenterStart)
                        .onFocusChanged { focusState ->
                            isFocused = focusState.isFocused
                        },
                    value = text,
                    textStyle = OiTheme.typography.bodyLargeRegular,
                    onValueChange = onTextChanged,
                    maxLines = 1,
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
                )
            }

            if (text.isEmpty()) {
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 1.dp)
                        .align(Alignment.CenterStart),
                    text = hint,
                    style = OiTheme.typography.bodyLargeRegular,
                    color = OiTheme.colors.textDisabled
                )
            }
        }

        if (text.isNotEmpty()) {
            Spacer(modifier = Modifier.width(OiTextFieldDimens.componentMargin))

            IconButton(
                modifier = Modifier.size(OiTextFieldDimens.iconSize),
                onClick = { onTextChanged("") }
            ) {
                Icon(
                    imageVector = ImageVector.vectorResource(R.drawable.temp_ic_textfield_close),
                    contentDescription = "Close button",
                    tint = Color.Unspecified
                )
            }
        }
    }
}

@Preview
@Composable
private fun ProfileScreenPreview() {
    ProfileContent()
}