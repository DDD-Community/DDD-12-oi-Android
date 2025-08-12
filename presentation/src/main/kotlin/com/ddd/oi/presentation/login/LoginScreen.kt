package com.ddd.oi.presentation.login

import android.content.Context
import android.content.Intent
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ddd.oi.domain.model.social.SocialType
import com.ddd.oi.presentation.R
import com.ddd.oi.presentation.core.designsystem.component.snackbar.OiSnackbarData
import com.ddd.oi.presentation.core.designsystem.component.snackbar.SnackbarType
import com.ddd.oi.presentation.core.designsystem.theme.OiTheme
import com.ddd.oi.presentation.login.contract.LoginSideEffect
import org.orbitmvi.orbit.compose.collectAsState
import org.orbitmvi.orbit.compose.collectSideEffect
import androidx.core.net.toUri

@Composable
fun LoginScreen(
    modifier: Modifier,
    viewModel: LoginViewModel = hiltViewModel(),
    onShowSnackbar: (OiSnackbarData) -> Unit = {},
) {
    val context = LocalContext.current
    val uiState by viewModel.collectAsState()
    val currentSocialType by viewModel.currentSocialType.collectAsStateWithLifecycle()

    viewModel.collectSideEffect { sideEffect ->
        when (sideEffect) {
            is LoginSideEffect.LoginFailure -> {
                onShowSnackbar(
                    OiSnackbarData(
                        message = sideEffect.throwable.message.toString(),
                        type = SnackbarType.WARNING
                    )
                )
            }

            LoginSideEffect.LoginSuccess -> {}
        }
    }

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
                socialType = social,
                currentSocialType = currentSocialType,
                onLoginClick = { viewModel.onLoginClicked(social, context) }
            )
        }
        Row(
            modifier = Modifier.padding(top = 12.dp).clickable{ contactToOi(context) },
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                stringResource(R.string.contact_us),
                color = OiTheme.colors.textTertiary,
                style = OiTheme.typography.bodyMediumMedium
            )
            Icon(
                Icons.AutoMirrored.Filled.KeyboardArrowRight,
                contentDescription = "contact us",
                tint = OiTheme.colors.iconTertiary
            )
        }
    }
}

private fun contactToOi(context: Context) {
    val intent = Intent(Intent.ACTION_SENDTO).apply {
        data = "mailto:".toUri()
        putExtra(Intent.EXTRA_EMAIL, arrayOf("testest@gmail.com"))
    }
    context.startActivity(intent)
}

@Composable
private fun SocialLoginButton(
    modifier: Modifier = Modifier,
    socialType: SocialType,
    currentSocialType: SocialType? = null,
    onLoginClick: () -> Unit = {}
) {
    Box(modifier) {
        Image(
            modifier = modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 4.dp)
                .clip(RoundedCornerShape(999.dp))
                .clickable { onLoginClick() },
            contentScale = ContentScale.Crop,
            imageVector = ImageVector.vectorResource(getSocialImage(socialType)),
            contentDescription = socialType.name
        )
        if (currentSocialType == socialType) {
            Image(
                // 아이콘 height = 48이므로 -20만큼 위로
                modifier = modifier
                    .align(Alignment.TopCenter)
                    .offset(y = (-20).dp),
                imageVector = ImageVector.vectorResource(R.drawable.ic_current_login),
                contentDescription = "current_login"
            )
        }
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