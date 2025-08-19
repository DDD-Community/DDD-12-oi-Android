package com.ddd.oi.presentation.core.designsystem.component.snackbar

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ddd.oi.presentation.R
import com.ddd.oi.presentation.core.designsystem.theme.OiTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.UUID

/**
 * 고급 스낵바 시스템 - 현업에서 사용하는 패턴
 * 
 * 특징:
 * 1. Sealed Interface로 타입 안전성 보장
 * 2. Builder Pattern으로 유연한 생성
 * 3. Queue 시스템으로 다중 스낵바 관리
 * 4. Composition으로 재사용성 극대화
 * 5. State Management 분리
 */

/**
 * 스낵바 변형들을 정의하는 Sealed Interface
 * 각 변형은 고유한 UI와 동작을 가짐
 */
sealed interface SnackbarVariant {
    val id: String
    val message: String
    val duration: SnackbarDuration
    
    data class Success(
        override val id: String = UUID.randomUUID().toString(),
        override val message: String,
        override val duration: SnackbarDuration = SnackbarDuration.Short,
        val icon: ImageVector? = null
    ) : SnackbarVariant
    
    data class Warning(
        override val id: String = UUID.randomUUID().toString(),
        override val message: String,
        override val duration: SnackbarDuration = SnackbarDuration.Long,
        val icon: Int,
    ) : SnackbarVariant

    data class ActionSnackbar(
        override val id: String = UUID.randomUUID().toString(),
        override val message: String,
        override val duration: SnackbarDuration = SnackbarDuration.Indefinite,
        val primaryActionLabel: String,
        val primaryAction: () -> Unit,
        val secondaryActionLabel: String? = null,
        val secondaryAction: (() -> Unit)? = null,
        val icon: ImageVector? = null
    ) : SnackbarVariant
}

/**
 * 스낵바 지속 시간 정의
 */
enum class SnackbarDuration(val millis: Long) {
    Short(4000L),
    Medium(6000L),
    Long(10000L),
    Indefinite(-1L)
}

/**
 * 스낵바 액션 정의
 */
data class SnackbarAction(
    val label: String,
    val action: () -> Unit,
    val style: ActionStyle = ActionStyle.Primary
)

enum class ActionStyle {
    Primary, Secondary, Destructive
}

/**
 * 스낵바 매니저 - 상태 관리 및 Queue 처리
 */
@Stable
class SnackbarManager(
    private val scope: CoroutineScope
) {
    private var _currentSnackbar by mutableStateOf<SnackbarVariant?>(null)
    private val _queue = mutableListOf<SnackbarVariant>()
    
    val currentSnackbar: SnackbarVariant? get() = _currentSnackbar
    
    fun showSnackbar(snackbar: SnackbarVariant) {
        scope.launch {
            if (_currentSnackbar != null) {
                _queue.add(snackbar)
                return@launch
            }
            
            _currentSnackbar = snackbar
            
            if (snackbar.duration != SnackbarDuration.Indefinite) {
                delay(snackbar.duration.millis)
                dismissCurrent()
            }
        }
    }
    
    fun dismissCurrent() {
        _currentSnackbar = null
        processQueue()
    }
    
    fun dismissById(id: String) {
        if (_currentSnackbar?.id == id) {
            dismissCurrent()
        } else {
            _queue.removeAll { it.id == id }
        }
    }
    
    private fun processQueue() {
        if (_queue.isNotEmpty()) {
            val next = _queue.removeAt(0)
            showSnackbar(next)
        }
    }
    
    fun clear() {
        _currentSnackbar = null
        _queue.clear()
    }
}

/**
 * 스낵바 빌더 - Fluent API로 쉬운 생성
 */
class SnackbarBuilder {
    
    fun success(
        message: String,
        duration: SnackbarDuration = SnackbarDuration.Short,
        icon: ImageVector? = null
    ): SnackbarVariant.Success = SnackbarVariant.Success(
        message = message,
        duration = duration,
        icon = icon
    )
    
    fun warning(
        message: String,
        duration: SnackbarDuration = SnackbarDuration.Medium,
        icon: Int
    ): SnackbarVariant.Warning = SnackbarVariant.Warning(
        message = message,
        duration = duration,
        icon = icon
    )

    fun action(
        message: String,
        primaryActionLabel: String,
        primaryAction: () -> Unit,
        secondaryActionLabel: String? = null,
        secondaryAction: (() -> Unit)? = null,
        duration: SnackbarDuration = SnackbarDuration.Indefinite,
        icon: ImageVector? = null
    ): SnackbarVariant.ActionSnackbar = SnackbarVariant.ActionSnackbar(
        message = message,
        primaryActionLabel = primaryActionLabel,
        primaryAction = primaryAction,
        secondaryActionLabel = secondaryActionLabel,
        secondaryAction = secondaryAction,
        duration = duration,
        icon = icon
    )
}

/**
 * 스낵바 매니저 생성 헬퍼
 */
@Composable
fun rememberSnackbarManager(): SnackbarManager {
    val scope = rememberCoroutineScope()
    return remember { SnackbarManager(scope) }
}

/**
 * 스낵바 빌더 인스턴스
 */
val Snackbar = SnackbarBuilder()

/**
 * 고급 스낵바 호스트 컴포넌트
 */
@Composable
fun AdvancedSnackbarHost(
    manager: SnackbarManager,
    modifier: Modifier = Modifier
) {
    val currentSnackbar = manager.currentSnackbar
    
    AnimatedVisibility(
        visible = currentSnackbar != null,
        enter = slideInVertically(initialOffsetY = { it }) + fadeIn(),
        exit = slideOutVertically(targetOffsetY = { it }) + fadeOut(),
        modifier = modifier
    ) {
        currentSnackbar?.let { snackbar ->
            SnackbarContent(
                snackbar = snackbar,
                onDismiss = { manager.dismissCurrent() }
            )
        }
    }
}

/**
 * 스낵바 컨텐츠 렌더링
 */
@Composable
private fun SnackbarContent(
    snackbar: SnackbarVariant,
    onDismiss: () -> Unit
) {
    when (snackbar) {
        is SnackbarVariant.Success -> SuccessSnackbar(snackbar)
        is SnackbarVariant.Warning -> WarningSnackbar(snackbar)
        is SnackbarVariant.ActionSnackbar -> ActionSnackbarContent(snackbar, onDismiss = onDismiss)
    }
}