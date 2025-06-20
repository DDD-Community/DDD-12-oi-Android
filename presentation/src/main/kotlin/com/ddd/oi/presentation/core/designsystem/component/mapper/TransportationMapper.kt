package com.ddd.oi.presentation.core.designsystem.component.mapper

import com.ddd.oi.domain.model.Transportation
import com.ddd.oi.presentation.R

internal fun Transportation.getText(): Int = when (this) {
    Transportation.Car -> R.string.car
    Transportation.Public -> R.string.public_transportation
    Transportation.Bicycle -> R.string.bike
    Transportation.Walk -> R.string.walk
}