package com.ddd.oi.presentation.core.designsystem.component.common

import androidx.annotation.DrawableRes
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
import androidx.compose.ui.unit.dp
import com.ddd.oi.presentation.R
import com.ddd.oi.presentation.core.designsystem.theme.OiTheme
import com.ddd.oi.presentation.core.designsystem.util.OiTextFieldDimens
import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toJavaLocalDateTime
import kotlinx.datetime.toLocalDateTime
import java.time.format.DateTimeFormatter

@Composable
fun OiTextField(
    modifier: Modifier = Modifier,
    text: String = "",
    hint: String = "",
    onTextChanged: (String) -> Unit = {},
) {
    var isFocused by remember { mutableStateOf(false) }

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
                        .padding(start = 1.dp)
                        .align(Alignment.CenterStart),
                    text = hint,
                    style = OiTheme.typography.bodyLargeRegular,
                    color = OiTheme.colors.textDisabled,
                )
            }
        }

        if (text.isNotEmpty()) {
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
    onClickDateField: () -> Unit = {},
    startDate: Long = -1L,
    endDate: Long = -1L,
    hint: String = "",
) {
    val dateText = remember(startDate, endDate) {
        getFormattedDate(startDate, endDate)
    }
    val isDateSelected = dateText.isNotEmpty()
    val isHintVisible = dateText.isEmpty()

    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(OiTextFieldDimens.height)
            .getOiTextFieldModifier(false)
            .clickable(
                indication = null,
                interactionSource = remember { MutableInteractionSource() }
            ) {
                onClickDateField()
            },
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

private fun getFormattedDate(
    startDate: Long,
    endDate: Long
): String {
    val startDateInstant = Instant
        .fromEpochMilliseconds(startDate)
    val endDateInstant = Instant
        .fromEpochMilliseconds(endDate)

    val formattedStartDate = startDateInstant.toLocalDateTime(TimeZone.currentSystemDefault())
        .toJavaLocalDateTime()
        .format(DateTimeFormatter.ofPattern("yy.MM.dd"))
    val formattedEndDate = endDateInstant
        .toLocalDateTime(TimeZone.currentSystemDefault())
        .toJavaLocalDateTime()
        .format(DateTimeFormatter.ofPattern("yy.MM.dd"))

    return when {
        startDate < 0L -> {
            ""
        }

        else -> {
            if (endDate < 0L || startDate == endDate) formattedStartDate
            else "$formattedStartDate - $formattedEndDate"
        }
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

@Preview(showBackground = true)
@Composable
private fun OiTextFieldPreview() {
    Column(verticalArrangement = Arrangement.spacedBy(OiTextFieldDimens.componentMargin)) {
        OiTextField(hint = "ex) 서울숲 데이트")
        OiTextField(text = "OiTextField")
        OiDateField(
            startDate = System.currentTimeMillis()
        )
        OiDateField(
            startDate = System.currentTimeMillis(),
            endDate = System.currentTimeMillis()
        )
        OiDateField(hint = "YY.MM.DD - YY.MM.DD")
    }
}
