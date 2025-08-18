package com.ddd.oi.presentation.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ddd.oi.presentation.R
import com.ddd.oi.presentation.core.designsystem.component.common.OiHeader
import com.ddd.oi.presentation.core.designsystem.theme.OiTheme
import com.ddd.oi.presentation.core.designsystem.theme.white
import com.ddd.oi.presentation.core.designsystem.util.rememberThrottledNavigation

sealed class ProfileMenuItem(
    val title: String,
    val onClick: () -> Unit
) {
    class ChangeNickname(onClick: () -> Unit) : ProfileMenuItem("닉네임 변경", onClick)
    class Logout(onClick: () -> Unit) : ProfileMenuItem("로그아웃", onClick)
    class WithdrawAccount(onClick: () -> Unit) : ProfileMenuItem("회원탈퇴", onClick)
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
    onBack: () -> Unit = {},
    onChangeNickname: () -> Unit = {},
    onLogout: () -> Unit = {},
    onWithdrawAccount: () -> Unit = {}
) {
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
            onChangeNickname = onChangeNickname,
            onLogout = onLogout,
            onWithdrawAccount = onWithdrawAccount
        )
    }
}

@Composable
private fun ProfileMenuList(
    modifier: Modifier = Modifier,
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
                ProfileMenuItem.ChangeNickname(onClick = onChangeNickname),
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

        Icon(
            modifier = Modifier.size(20.dp),
            painter = painterResource(R.drawable.ic_chevron_right),
            contentDescription = "이동",
            tint = OiTheme.colors.iconSecondary
        )
    }
}

@Preview
@Composable
private fun ProfileScreenPreview() {
    ProfileContent()
}