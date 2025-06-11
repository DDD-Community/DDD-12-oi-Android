package com.ddd.oi.presentation.upsertschedule

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.ddd.oi.presentation.core.designsystem.theme.OiTheme

@Composable
fun UpsertScheduleScreen(modifier: Modifier = Modifier) {
    Column(modifier = modifier) {

    }
}

//@Preview
//@Composable
//fun PreviewUpsertScheduleScreen() {
//    UpsertScheduleScreen()
//}

@Preview(showBackground = true)
@Composable
fun Test() {
    OiTheme {
        Column {
            Text("semin")
        }
    }
}