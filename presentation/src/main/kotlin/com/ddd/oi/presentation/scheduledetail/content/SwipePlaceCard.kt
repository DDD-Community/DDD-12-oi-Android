package com.ddd.oi.presentation.scheduledetail.content

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.AnchoredDraggableDefaults
import androidx.compose.foundation.gestures.AnchoredDraggableState
import androidx.compose.foundation.gestures.DraggableAnchors
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.anchoredDraggable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.ddd.oi.domain.model.schedule.Place
import com.ddd.oi.presentation.core.designsystem.theme.OiTheme
import com.ddd.oi.presentation.core.designsystem.theme.white
import kotlin.math.roundToInt

private enum class SwipeState { DEFAULT, REVEALED }


@Composable
fun SwipePlaceCard(
    place: Place,
    order: Int,
    isSelected: Boolean,
    onClick: () -> Unit,
    editTimeClick: () -> Unit,
    onMemoClick: () -> Unit,
    onEditClick: () -> Unit,
    onDeleteClick: () -> Unit,
) {
    val density = LocalDensity.current

    val anchors = DraggableAnchors {
        SwipeState.DEFAULT at 0f
        SwipeState.REVEALED at with(density) { -226.dp.toPx() }
    }

    val anchorDraggableState = remember {
        AnchoredDraggableState(
            initialValue = SwipeState.DEFAULT,
            anchors = anchors,
        )
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(IntrinsicSize.Min)
    ) {
        ActionButtons(
            modifier = Modifier.align(Alignment.CenterEnd).padding(end = 16.dp).height(50.dp),
            onMemoClick = onMemoClick,
            onEditClick = onEditClick,
            onDeleteClick = onDeleteClick
        )
        PlaceCard(
            place = place,
            order = order,
            isSelected = isSelected,
            onClick = onClick,
            editTimeClick = editTimeClick,
            modifier = Modifier
                .offset { IntOffset(anchorDraggableState.requireOffset().roundToInt(), 0) }
                .anchoredDraggable(
                    state = anchorDraggableState,
                    orientation = Orientation.Horizontal,
                    flingBehavior = AnchoredDraggableDefaults.flingBehavior(anchorDraggableState)
                )
        )
    }

}

@Composable
private fun ActionButtons(
    modifier: Modifier = Modifier,
    onMemoClick: () -> Unit,
    onEditClick: () -> Unit,
    onDeleteClick: () -> Unit
) {
    Row(modifier = modifier) {
        ActionButton(text = "메모", color = Color(0xFF585858), shape = RoundedCornerShape(topStart = 16.dp, bottomStart = 16.dp), onClick = onMemoClick)
        ActionButton(text = "수정", color = Color(0xFF4A9DFF), shape = RectangleShape, onClick = onEditClick)
        ActionButton(text = "삭제", color = Color(0xFFFF4545), shape = RoundedCornerShape(topEnd = 16.dp, bottomEnd = 16.dp), onClick = onDeleteClick)
    }
}

@Composable
private fun ActionButton(
    modifier: Modifier = Modifier,
    shape: Shape,
    text: String,
    color: Color,
    onClick: () -> Unit,
) {
    Box(
        modifier = modifier
            .fillMaxHeight()
            .clip(shape)
            .width(70.dp)
            .background(color = color)
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            color = white,
            style = OiTheme.typography.bodyMediumSemibold
        )
    }
}

@Composable
@Preview(showBackground = true)
private fun SwipePlaceCardPreview() {

}