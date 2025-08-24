package com.ddd.oi.presentation.core.designsystem.component.common

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.ddd.oi.presentation.core.designsystem.theme.OiTheme

@Composable
fun OiSpotCard(
    modifier: Modifier = Modifier,
    placeName: String,
    category: String,
    address: String,
    imageUrl: String
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(2.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp, vertical = 7.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Card(
                modifier = Modifier,
                shape = RoundedCornerShape(8.dp),
                elevation = CardDefaults.cardElevation(1.dp),
            ) {
                AsyncImage(
                    modifier = Modifier
                        .size(56.dp)
                        .clip(RoundedCornerShape(8.dp)),
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(imageUrl)
                        .crossfade(true)
                        .build(),
                    contentDescription = placeName,
                    contentScale = ContentScale.Crop
                )
            }


            // 오른쪽 텍스트 정보 (2열)
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        text = placeName,
                        style = OiTheme.typography.bodyMediumSemibold,
                        color = OiTheme.colors.textPrimary
                    )

                    Text(
                        text = category,
                        style = OiTheme.typography.bodyXSmallMedium,
                        color = OiTheme.colors.textBrand
                    )
                }

                Text(
                    text = address,
                    style = OiTheme.typography.bodySmallRegular,
                    color = OiTheme.colors.textTertiary
                )
            }
        }
    }
}

@Composable
@Preview
private fun OiSpotCardPreview() {
    OiSpotCard(
        placeName = "경복궁",
        category = "궁궐",
        address = "서울 종로구 사직로 161",
        imageUrl = "https://picsum.photos/64/64"
    )
}