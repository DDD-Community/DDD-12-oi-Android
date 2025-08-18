package com.ddd.oi.presentation.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
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
import com.ddd.oi.presentation.core.designsystem.theme.OiTheme
import com.ddd.oi.presentation.core.designsystem.theme.white
import com.ddd.oi.presentation.core.designsystem.util.Dimens
import com.ddd.oi.presentation.core.designsystem.util.rememberThrottledNavigation

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
    onChangeNickname: () -> Unit = {},
    onLogout: () -> Unit = {},
    onWithdrawAccount: () -> Unit = {},
    viewModel: ProfileViewModel = hiltViewModel()
) {
    val throttledNavigation = rememberThrottledNavigation()
    
    ProfileContent(
        onBack = { throttledNavigation(onBack) },
        onChangeNickname = { throttledNavigation(onChangeNickname) },
        onLogout = { throttledNavigation(onLogout) },
        onWithdrawAccount = { throttledNavigation(onWithdrawAccount) }
    )
}

@Composable
private fun ProfileContent(
    modifier: Modifier = Modifier,
    nickname: String = "오늘의이동",
    onBack: () -> Unit = {},
    onChangeNickname: () -> Unit = {},
    onLogout: () -> Unit = {},
    onWithdrawAccount: () -> Unit = {}
) {
    var showChangeNicknameDialog by remember { mutableStateOf(false) }
    var showLogoutDialog by remember { mutableStateOf(false) }
    var newNickname by remember { mutableStateOf(nickname) }
    
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
                    onChangeNickname()
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
                    text = "${newNickname.length}/15",
                    style = OiTheme.typography.bodySmallMedium,
                    color = OiTheme.colors.textSecondary
                )
            }
            
            OiTextField(
                modifier = Modifier.fillMaxWidth(),
                text = newNickname,
                onTextChanged = { if (it.length <= 15) newNickname = it },
                hint = currentNickname
            )
            
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp, bottom = Dimens.paddingLarge),
                text = "닉네임은 한글, 숫자, 영어만 사용할 수 있어요",
                style = OiTheme.typography.bodySmallRegular,
                color = OiTheme.colors.textSecondary
            )
            
            OiButton(
                modifier = Modifier.fillMaxWidth(),
                style = OiButtonStyle.Large48Oval,
                colorType = OiButtonColorType.Primary,
                title = "변경하기",
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

@Preview
@Composable
private fun ProfileScreenPreview() {
    ProfileContent()
}