package com.ddd.oi.presentation.core.designsystem.component.common

import androidx.annotation.DrawableRes
import androidx.compose.foundation.border
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import com.ddd.oi.presentation.R
import com.ddd.oi.presentation.core.designsystem.theme.OiTheme
import com.ddd.oi.presentation.core.designsystem.util.OiTextFieldDimens

@Composable
fun OiTextField(
    modifier: Modifier = Modifier,
    text: String = "",
    hint: String = "",
    onTextChanged: (String) -> Unit = {},
) {
    var isFocused by remember { mutableStateOf(false) }
    val isClearButtonVisible by remember { derivedStateOf { text.isNotEmpty() } }

    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(OiTextFieldDimens.height)
            .getOiTextFieldModifier(isFocused),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .weight(1F)
        ) {
            BasicTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.CenterStart)
                    .onFocusChanged { focusState ->
                        isFocused = focusState.isFocused
                    },
                value = text,
                textStyle = OiTheme.typography.bodyLargeRegular,
                onValueChange = {
                    if (it.length <= MAX_LENGTH) {
                        onTextChanged(it)
                    }
                },
                maxLines = 1,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
            )

            if (text.isEmpty()) {
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.CenterStart),
                    text = hint,
                    style = OiTheme.typography.bodyLargeRegular,
                    color = OiTheme.colors.textDisabled,
                )
            }
        }

        if (isClearButtonVisible) {
            Spacer(modifier = Modifier.width(OiTextFieldDimens.componentMargin))

            IconButton(
                modifier = Modifier
                    .size(OiTextFieldDimens.iconSize),
                onClick = {
                    onTextChanged("")
                }
            ) {
                Icon(
                    imageVector = ImageVector.vectorResource(R.drawable.temp_ic_textfield_close),
                    contentDescription = "Close button",
                )
            }
        }
    }
}

@Composable
fun OiDateField(
    modifier: Modifier = Modifier,
    startDate: String = "",
    endDate: String = "",
    hint: String = "",
) {
    val dateText by remember { derivedStateOf { getFormattedDate(startDate, endDate) } }
    val isDateSelected by remember { derivedStateOf { dateText.isNotEmpty() } }
    val isHintVisible by remember { derivedStateOf { startDate.isEmpty() && endDate.isEmpty() } }

    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(OiTextFieldDimens.height)
            .getOiTextFieldModifier(false),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            modifier = Modifier.size(OiTextFieldDimens.iconSize),
            imageVector = ImageVector.vectorResource(getDateIconDrawableRes(isDateSelected)),
            contentDescription = "Close button",
            tint = getDateIconTint(isDateSelected)
        )

        Spacer(modifier = Modifier.width(OiTextFieldDimens.componentMargin))

        Box(modifier = Modifier.weight(1F)) {
            if (isHintVisible) {
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.CenterStart),
                    text = hint,
                    style = OiTheme.typography.bodyLargeRegular,
                    color = OiTheme.colors.textDisabled,
                )
            } else {
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.CenterStart),
                    text = dateText,
                    style = OiTheme.typography.bodyLargeRegular,
                    color = OiTheme.colors.textPrimary,
                )
            }
        }
    }
}

private fun getFormattedDate(startDate: String, endDate: String): String {
    return when {
        startDate.isEmpty() -> ""
        endDate.isEmpty() -> startDate
        else -> "$startDate - $endDate"
    }
}

@DrawableRes
private fun getDateIconDrawableRes(isDateSelected: Boolean): Int {
    return if (isDateSelected) R.drawable.temp_calendar_enabled
    else R.drawable.temp_calendar_disabled
}

@Composable
private fun getDateIconTint(isDateSelected: Boolean): Color {
    return if (isDateSelected) OiTheme.colors.iconPrimary
    else OiTheme.colors.iconDisabled
}

private const val MAX_LENGTH = 30

@Composable
private fun Modifier.getOiTextFieldModifier(isFocused: Boolean): Modifier {
    return this
        .clip(RoundedCornerShape(OiTextFieldDimens.roundedRectRadius))
        .border(
            width = OiTextFieldDimens.stroke,
            color = if (isFocused) OiTheme.colors.borderBrand else OiTheme.colors.textDisabled,
            shape = RoundedCornerShape(OiTextFieldDimens.roundedRectRadius)
        )
        .fillMaxSize()
        .padding(horizontal = OiTextFieldDimens.horizontalPadding)
}

@Preview
@Composable
private fun OiTextFieldPreview() {
    Column(verticalArrangement = Arrangement.spacedBy(OiTextFieldDimens.componentMargin)) {
        OiTextField(hint = "ex) 서울숲 데이트")
        OiTextField(text = "OiTextField")
        OiDateField(startDate = "25.06.06", endDate = "25.06.07")
        OiDateField(startDate = "25.06.06")
        OiDateField(hint = "YY.MM.DD - YY.MM.DD")
    }
}
