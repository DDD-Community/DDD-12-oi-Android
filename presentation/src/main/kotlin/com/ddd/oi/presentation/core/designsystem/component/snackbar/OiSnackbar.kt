package com.ddd.oi.presentation.core.designsystem.component.snackbar

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.ddd.oi.presentation.core.designsystem.theme.OiTheme
import com.ddd.oi.presentation.core.designsystem.theme.snackbarBackground
import com.ddd.oi.presentation.core.designsystem.util.Dimens
import com.ddd.oi.presentation.core.designsystem.util.OiSnackBarDimen

@Composable
fun OiSnackbar(
    message: String,
    modifier: Modifier = Modifier,
    icon: Int? = null,
    backgroundColor: Color = snackbarBackground,
    contentColor: Color = Color.White,
    shape: Shape = RoundedCornerShape(OiSnackBarDimen.cornerRadius),
    textStyle: TextStyle = OiTheme.typography.bodyMediumRegular
) {
    CompositionLocalProvider(LocalContentColor provides contentColor) {
        Box(
            modifier = modifier
                .fillMaxWidth()
                .height(OiSnackBarDimen.snackbarHeight)
                .padding(horizontal = Dimens.paddingMedium)
                .clip(shape)
                .background(backgroundColor),
            contentAlignment = Alignment.Center
        ) {
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                icon?.let { iconVector ->
                    Image(
                        painter = painterResource(iconVector),
                        contentDescription = null,
                        modifier = Modifier.padding(end = Dimens.paddingSmall)
                    )
                }

                Text(
                    text = message,
                    style = textStyle,
                    color = contentColor,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}

@Composable
fun OiSnackbarHost(
    hostState: SnackbarHostState,
    controller: SnackbarController,
    modifier: Modifier = Modifier,
    shape: Shape = RoundedCornerShape(12.dp),
) {
    val icon = controller.currentSnackbarData?.getIcon()
    SnackbarHost(
        hostState = hostState,
        modifier = modifier
    ) { snackbarData ->
        OiSnackbar(
            message = snackbarData.visuals.message,
            icon = icon,
            shape = shape,
        )
    }
}