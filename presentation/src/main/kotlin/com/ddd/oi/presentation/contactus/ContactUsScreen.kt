package com.ddd.oi.presentation.contactus

import android.content.Intent
import android.net.Uri
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ddd.oi.presentation.R
import com.ddd.oi.presentation.core.designsystem.component.common.OiHeader
import com.ddd.oi.presentation.core.designsystem.theme.OiTheme
import com.ddd.oi.presentation.core.designsystem.theme.white
import com.ddd.oi.presentation.core.designsystem.util.rememberThrottledNavigation

data class FaqItem(
    val question: String,
    val answer: String
)

@Composable
fun ContactUsScreen(
    onBack: () -> Unit = {},
    viewModel: ContactUsViewModel = hiltViewModel()
) {
    val throttledNavigation = rememberThrottledNavigation()

    ContactUsContent(
        onBack = { throttledNavigation(onBack) }
    )
}

@Composable
private fun ContactUsContent(
    modifier: Modifier = Modifier,
    onBack: () -> Unit = {}
) {
    val context = LocalContext.current

    val faqItems = listOf(
        FaqItem(
            question = "회원가입은 어떻게 하나요?",
            answer = "카카오 계정으로 간편하게 회원가입할 수 있습니다. 앱 첫 화면에서 '카카오로 시작하기' 버튼을 눌러주세요."
        ),
        FaqItem(
            question = "일정을 어떻게 등록하나요?",
            answer = "홈 화면의 '+' 버튼을 눌러 새 일정을 등록할 수 있습니다. 날짜, 시간, 장소를 설정하여 일정을 만들어보세요."
        ),
        FaqItem(
            question = "추천 장소는 어떻게 확인하나요?",
            answer = "홈 화면 하단의 '추천 장소' 섹션에서 다양한 관광지를 확인할 수 있습니다. 각 장소를 탭하면 상세 정보를 볼 수 있어요."
        ),
        FaqItem(
            question = "계정을 삭제하고 싶어요",
            answer = "설정 > 프로필 관리에서 계정 삭제를 진행할 수 있습니다. 삭제된 데이터는 복구되지 않으니 신중히 결정해주세요."
        ),
        FaqItem(
            question = "앱이 느려지거나 오류가 발생해요",
            answer = "앱을 완전히 종료 후 재시작해보세요. 문제가 지속되면 아래 메일 문의하기를 통해 문의해주시기 바랍니다."
        )
    )

    Scaffold(
        modifier = modifier
            .fillMaxSize()
            .background(white),
        containerColor = white,
        topBar = {
            OiHeader(
                onLeftClick = onBack,
                title = "문의하기",
                isDividerVisible = true
            )
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            contentPadding = PaddingValues(horizontal = 16.dp)
        ) {
            item {
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 24.dp, bottom = 20.dp),
                    text = "자주하는 질문",
                    style = OiTheme.typography.headlineSmallBold,
                    color = OiTheme.colors.textPrimary
                )
            }
            
            items(faqItems) { faqItem ->
                FaqItemView(
                    faqItem = faqItem
                )
            }

            item {
                // 메일 문의하기 버튼
                Button(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = (56 + 16).dp, bottom = 16.dp),
                    onClick = {
                        val intent = Intent(Intent.ACTION_SENDTO).apply {
                            data = Uri.parse("mailto:oneuluiidong@gmail.com")
                            putExtra(Intent.EXTRA_SUBJECT, "[OI 앱] 문의사항")
                            putExtra(Intent.EXTRA_TEXT, "문의 내용을 입력해주세요.")
                        }
                        context.startActivity(intent)
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = OiTheme.colors.backgroundSecondary
                    ),
                    shape = RoundedCornerShape(50),
                    contentPadding = PaddingValues(16.dp)
                ) {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.ic_mail),
                            contentDescription = "메일",
                            tint = OiTheme.colors.iconBrand,
                            modifier = Modifier.size(20.dp)
                        )
                        Text(
                            text = "메일 문의하기",
                            style = OiTheme.typography.bodyMediumSemibold,
                            color = OiTheme.colors.textBrand
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun FaqItemView(
    modifier: Modifier = Modifier,
    faqItem: FaqItem
) {
    var isExpanded by remember { mutableStateOf(false) }

    Column(
        modifier = modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { isExpanded = !isExpanded }
                .padding(vertical = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                modifier = Modifier.weight(1f),
                text = faqItem.question,
                overflow = TextOverflow.Ellipsis,
                maxLines = 2,
                style = OiTheme.typography.bodyLargeMedium,
                color = OiTheme.colors.textPrimary
            )

            Icon(
                modifier = Modifier.size(20.dp),
                painter = painterResource(
                    if (isExpanded) {
                        R.drawable.ic_chevron_up
                    } else {
                        R.drawable.ic_chevron_down
                    }
                ),
                contentDescription = if (isExpanded) "접기" else "펼치기",
                tint = OiTheme.colors.iconSecondary
            )
        }

        AnimatedVisibility(
            visible = isExpanded,
            enter = fadeIn() + expandVertically(),
            exit = fadeOut() + shrinkVertically()
        ) {
            Text(
                modifier = Modifier
                    .background(color = OiTheme.colors.backgroundContents)
                    .padding(horizontal = 16.dp, vertical = 20.dp),
                text = faqItem.answer,
                style = OiTheme.typography.bodyMediumRegular,
                color = OiTheme.colors.textSecondary
            )
        }
    }
}

@Preview
@Composable
private fun ContactUsScreenPreview() {
    ContactUsContent()
}