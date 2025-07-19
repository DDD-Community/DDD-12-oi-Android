package com.ddd.oi.presentation.core.designsystem.component.common

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ddd.oi.presentation.core.designsystem.theme.OiTheme

@Composable
fun OiBasicTextField(
    modifier: Modifier = Modifier,
    memo: String,
    onValueChange: (String) -> Unit = {},
) {

    BasicTextField(
        modifier = modifier
            .fillMaxWidth()
            .height(140.dp),
        value = memo,
        textStyle = OiTheme.typography.bodySmallRegular,
        onValueChange = { value ->
            if (value.length <= 100) onValueChange(value)
        },
    ) { innerTextField ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .clip(RoundedCornerShape(8.dp))
                .border(1.dp, OiTheme.colors.borderPrimary, RoundedCornerShape(8.dp))
                .padding(16.dp)
        ) {
            AnimatedVisibility(visible = memo.isEmpty(),
                enter = fadeIn(),
                exit = fadeOut()
            ) {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = "장소에 대해 기억할 정보를 메모해보세요. (최대 100자)",
                    style = OiTheme.typography.bodySmallRegular,
                    color = OiTheme.colors.textDisabled,
                    textAlign = TextAlign.Center
                )
            }
            innerTextField()

        }
    }
}

@Composable
@Preview(showBackground = true)
private fun OiBasicTextFieldPreview() {
    OiTheme {
        Column(modifier = Modifier.fillMaxSize()) {
            Text(text = "TEst")
            OiBasicTextField(memo = "", onValueChange = {})
        }
    }
}