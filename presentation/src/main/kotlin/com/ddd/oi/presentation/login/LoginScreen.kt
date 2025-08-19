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
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextDecoration
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
import org.orbitmvi.orbit.compose.collectSideEffect
import androidx.core.net.toUri
import okio.IOException

@Composable
fun LoginScreen(
    modifier: Modifier,
    viewModel: LoginViewModel = hiltViewModel(),
    onShowSnackbar: (OiSnackbarData) -> Unit = {},
    onNavigateToHome: () -> Unit = {},
    onNavigateToWebView: (String, String) -> Unit = { _, _ -> }
) {
    val context = LocalContext.current
    val currentSocialType by viewModel.currentSocialType.collectAsStateWithLifecycle()

    viewModel.collectSideEffect { sideEffect ->
        when (sideEffect) {
            is LoginSideEffect.LoginFailure -> {
                val message = when (sideEffect.throwable) {
                    is IOException -> "네트워크가 불안정해요. 잠시 후 다시 시도해주세요!"
                    else -> "로그인에 실패했어요. 다른 방법으로 시도해주세요."
                }
                onShowSnackbar(
                    OiSnackbarData(
                        message = message,
                        type = SnackbarType.WARNING
                    )
                )
            }

            LoginSideEffect.LoginSuccess -> {
                onNavigateToHome()
            }
        }
    }

    LoginScreen(
        modifier = modifier.fillMaxSize(),
        currentSocialType = currentSocialType,
        onLoginClick = { viewModel.onLoginClicked(it, context) },
        onContactClick = { contactToOi(context) },
        onNavigateToWebView = onNavigateToWebView
    )
}

@Composable
private fun LoginScreen(
    modifier: Modifier = Modifier,
    currentSocialType: SocialType?,
    onLoginClick: (SocialType) -> Unit,
    onContactClick: () -> Unit,
    onNavigateToWebView: (String, String) -> Unit = { _, _ -> }
) {
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
                onLoginClick = { onLoginClick(social) }
            )
        }
        Row(
            modifier = Modifier
                .padding(top = 12.dp)
                .clickable { onContactClick() },
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
        PolicyAndTermsText(
            modifier = Modifier.padding(top = 16.dp),
            onPrivacyPolicyClick = {
                onNavigateToWebView(
                    "https://steel-eocursor-051.notion.site/1feb2308626180588f3ce2f1d0a294c2?source=copy_link",
                    "개인정보 처리방침"
                )
            },
            onTermsOfServiceClick = {
                onNavigateToWebView(
                    "https://steel-eocursor-051.notion.site/1feb2308626180f28734ea42a9284029?source=copy_link",
                    "이용약관"
                )
            }
        )
    }
}

@Composable
fun PolicyAndTermsText(
    modifier: Modifier = Modifier,
    onPrivacyPolicyClick: () -> Unit,
    onTermsOfServiceClick: () -> Unit
) {
    CompositionLocalProvider(
        LocalTextStyle provides OiTheme.typography.bodyXSmallRegular.copy(
            color = OiTheme.colors.textTertiary
        )
    ) {
        Row(
            modifier = modifier,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("회원가입 시 오이(Oi)의 ")

            Text(
                text = "개인정보 처리방침",
                modifier = Modifier.clickable(
                    onClick = onPrivacyPolicyClick
                ),
                style = OiTheme.typography.bodyXSmallRegular.copy(
                    textDecoration = TextDecoration.Underline,
                    color = OiTheme.colors.textPrimary
                )
            )

            Text(" 및 ")

            Text(
                text = "이용약관",
                modifier = Modifier.clickable(
                    onClick = onTermsOfServiceClick
                ),
                style = OiTheme.typography.bodyXSmallRegular.copy(
                    textDecoration = TextDecoration.Underline,
                    color = OiTheme.colors.textPrimary
                )
            )
            Text("에 동의합니다.")
        }
    }
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

private fun contactToOi(context: Context) {
    val intent = Intent(Intent.ACTION_SENDTO).apply {
        data = "mailto:".toUri()
        putExtra(Intent.EXTRA_EMAIL, arrayOf("oneuluiidong@gmail.com"))
    }
    context.startActivity(intent)
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
        LoginScreen(
            modifier = Modifier.fillMaxSize(),
            currentSocialType = SocialType.KAKAO,
            onLoginClick = {},
            onContactClick = {}
        )
    }
}