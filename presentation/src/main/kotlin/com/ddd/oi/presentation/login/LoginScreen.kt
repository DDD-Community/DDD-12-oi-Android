package com.ddd.oi.presentation.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ddd.oi.presentation.R
import com.ddd.oi.presentation.core.designsystem.theme.OiTheme
import com.ddd.oi.presentation.login.contract.SocialType
import org.orbitmvi.orbit.compose.collectAsState

@Composable
fun LoginScreen(
    modifier: Modifier,
    viewModel: LoginViewModel = hiltViewModel()
) {
    val uiState by viewModel.collectAsState()
    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.fillMaxHeight(0.1461f))
        Image(
            modifier = Modifier
                .fillMaxWidth(0.61f)
                .fillMaxHeight(0.074f),
            imageVector = ImageVector.vectorResource(R.drawable.ic_title),
            contentDescription = "title"
        )
        Spacer(modifier = Modifier.fillMaxHeight(0.077f))
        Image(
            modifier = Modifier
                .fillMaxSize(0.4583f)
                .fillMaxHeight(0.2348f),
            imageVector = ImageVector.vectorResource(R.drawable.ic_oi),
            contentDescription = "oi"
        )
        Spacer(modifier = Modifier.fillMaxHeight(0.0659f))

        SocialType.entries.forEach { social ->
            SocialLoginButton(
                socialType = social
            )
        }
    }
}

@Composable
private fun SocialLoginButton(
    modifier: Modifier = Modifier,
    socialType: SocialType
) {
    Box(modifier) {
        Image(
            modifier = modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 4.dp)
                .clip(RoundedCornerShape(999.dp))
                .clickable {},
            contentScale = ContentScale.Crop,
            imageVector = ImageVector.vectorResource(getSocialImage(socialType)),
            contentDescription = socialType.name
        )
    }
}

private fun getSocialImage(socialType: SocialType): Int {
    return when (socialType) {
        SocialType.NAVER -> R.drawable.ic_login_naver
        SocialType.GOOGLE -> R.drawable.ic_login_google
        SocialType.KAKAO -> R.drawable.ic_login_kakao
    }
}

@Composable
@Preview(showBackground = true)
private fun LoginScreenPreview() {
    OiTheme {
        LoginScreen(modifier = Modifier.fillMaxSize())
    }
}