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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import kotlinx.coroutines.delay
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ddd.oi.presentation.R
import com.ddd.oi.presentation.core.designsystem.component.common.OiHeader
import com.ddd.oi.presentation.core.designsystem.theme.OiTheme
import com.ddd.oi.presentation.core.designsystem.theme.white
import com.ddd.oi.presentation.core.designsystem.util.rememberThrottledNavigation
import com.ddd.oi.domain.model.Faq

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
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val faqs by viewModel.faqs.collectAsStateWithLifecycle()

    if (uiState.isLoading && faqs.isEmpty()) {
        ContactUsLoadingScreen(onBack = { throttledNavigation(onBack) })
    } else {
        ContactUsContent(
            onBack = { throttledNavigation(onBack) },
            uiState = uiState,
            faqs = faqs,
            onLoadMore = viewModel::loadMoreFaqs,
            onRefresh = viewModel::refresh
        )
    }
}

@Composable
private fun ContactUsContent(
    modifier: Modifier = Modifier,
    onBack: () -> Unit = {},
    uiState: ContactUsUiState = ContactUsUiState(),
    faqs: List<Faq> = emptyList(),
    onLoadMore: () -> Unit = {},
    onRefresh: () -> Unit = {}
) {
    val context = LocalContext.current

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
            
            items(faqs.size) { index ->
                val faq = faqs[index]
                
                // 페이지네이션: 마지막 아이템 근처에서 추가 데이터 로드
                if (index >= faqs.size - 3 && uiState.hasMorePages && !uiState.isLoading) {
                    LaunchedEffect(Unit) {
                        onLoadMore()
                    }
                }
                
                FaqItemView(
                    faqItem = FaqItem(
                        question = faq.question,
                        answer = faq.answer
                    )
                )
            }
            
            // 로딩 인디케이터
            if (uiState.isLoading && faqs.isNotEmpty()) {
                item {
                    ContactUsLoadingIndicator()
                }
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

@Composable
private fun ContactUsLoadingScreen(
    modifier: Modifier = Modifier,
    onBack: () -> Unit = {}
) {
    var currentFrame by remember { mutableIntStateOf(1) }

    LaunchedEffect(Unit) {
        while (true) {
            delay(100)
            currentFrame = if (currentFrame == 8) 1 else currentFrame + 1
        }
    }

    val loadingIcons = listOf(
        R.drawable.ic_loading_1,
        R.drawable.ic_loading_2,
        R.drawable.ic_loading_3,
        R.drawable.ic_loading_4,
        R.drawable.ic_loading_5,
        R.drawable.ic_loading_6,
        R.drawable.ic_loading_7,
        R.drawable.ic_loading_8
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
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(32.dp, Alignment.CenterVertically),
        ) {
            Icon(
                painter = painterResource(loadingIcons[currentFrame - 1]),
                contentDescription = "",
                tint = Color.Unspecified,
            )

            Text(
                text = "화면을 불러오고 있어요",
                style = OiTheme.typography.headlineSmallBold,
                color = OiTheme.colors.textPrimary,
            )
        }
    }
}

@Composable
private fun ContactUsLoadingIndicator(
    modifier: Modifier = Modifier
) {
    var currentFrame by remember { mutableIntStateOf(1) }

    LaunchedEffect(Unit) {
        while (true) {
            delay(100)
            currentFrame = if (currentFrame == 8) 1 else currentFrame + 1
        }
    }

    val loadingIcons = listOf(
        R.drawable.ic_loading_1,
        R.drawable.ic_loading_2,
        R.drawable.ic_loading_3,
        R.drawable.ic_loading_4,
        R.drawable.ic_loading_5,
        R.drawable.ic_loading_6,
        R.drawable.ic_loading_7,
        R.drawable.ic_loading_8
    )

    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            modifier = Modifier.size(24.dp),
            painter = painterResource(loadingIcons[currentFrame - 1]),
            contentDescription = "",
            tint = Color.Unspecified,
        )
        
        Text(
            modifier = Modifier.padding(start = 8.dp),
            text = "로딩 중...",
            style = OiTheme.typography.bodyMediumRegular,
            color = OiTheme.colors.textSecondary
        )
    }
}

@Preview
@Composable
private fun ContactUsScreenPreview() {
    ContactUsContent()
}