package com.ddd.oi.presentation.splash

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.ddd.oi.presentation.R
import com.ddd.oi.presentation.core.designsystem.theme.OiTheme
import com.ddd.oi.presentation.splash.contract.SplashSideEffect
import org.orbitmvi.orbit.compose.collectSideEffect

@Composable
fun SplashScreen(
    modifier: Modifier = Modifier,
    viewModel: SplashViewModel = hiltViewModel(),
    navigateToLogin: () -> Unit = {},
    navigateToHome: () -> Unit = {}
) {
    viewModel.collectSideEffect { sideEffect ->
        when (sideEffect) {
            SplashSideEffect.NavigateToLogin -> {
                Log.d("NavigateToLogin", "NavigateToLogin")
                navigateToLogin() }
            SplashSideEffect.NavigateToMain -> {
                Log.d("NavigateToMain", "NavigateToMain")
                navigateToHome()
            }
            is SplashSideEffect.ShowToast -> { Log.d("ShowToast", "ShowToast") }
        }
    }
    Box(modifier = modifier.fillMaxSize()) {
        Image(
            modifier = Modifier.fillMaxSize(),
            painter = painterResource(R.drawable.ic_splash),
            contentDescription = "splash",
            contentScale = ContentScale.Crop
        )
    }
}

@Composable
@Preview(showBackground = true)
private fun SplashScreenPreview() {
    OiTheme {
        SplashScreen()
    }
}