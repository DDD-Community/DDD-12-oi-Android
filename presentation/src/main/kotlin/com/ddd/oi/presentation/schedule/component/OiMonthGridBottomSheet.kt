package com.ddd.oi.presentation.schedule.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.ddd.oi.presentation.R
import com.ddd.oi.presentation.core.designsystem.component.common.OiButton
import com.ddd.oi.presentation.core.designsystem.component.common.OiButtonStyle
import com.ddd.oi.presentation.core.designsystem.component.oidaterangebottomsheet.OiMonthGrid
import com.ddd.oi.presentation.core.designsystem.theme.OiTheme
import com.ddd.oi.presentation.core.designsystem.util.Dimens
import kotlinx.datetime.LocalDate

@Composable
fun OiMonthGridBottomSheet(
    modifier: Modifier = Modifier,
    selectedYear: Int,
    selectedMonth: Int,
    onMonthSelected: (LocalDate) -> Unit
) {
    var year by remember { mutableIntStateOf(selectedYear) }
    var month by remember { mutableIntStateOf(selectedMonth) }

    Column(modifier = modifier) {
        OiMonthGrid(
            selectedYear = year,
            selectedMonth = month,
            onMonthSelected = {
                year = it.year
                month = it.monthNumber
            }
        )
        OiButton(
            modifier = Modifier.padding(horizontal = Dimens.paddingMedium),
            textStringRes = R.string.complete,
            style = OiButtonStyle.Large48Oval,
            enabled = true,
            onClick = {
                val date = LocalDate(year, month, 1)
                onMonthSelected(date)
            }
        )
    }

}

@Composable
@Preview(showBackground = true)
private fun OiMonthGridBottomSheetPreview() {
    OiTheme {
        OiMonthGridBottomSheet(
            selectedYear = 2025,
            selectedMonth = 6,
            onMonthSelected = { }
        )
    }
}
