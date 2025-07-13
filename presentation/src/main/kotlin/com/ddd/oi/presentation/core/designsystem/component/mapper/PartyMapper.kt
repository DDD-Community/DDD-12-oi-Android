package com.ddd.oi.presentation.core.designsystem.component.mapper

import androidx.annotation.StringRes
import com.ddd.oi.domain.model.schedule.Party
import com.ddd.oi.presentation.R

@StringRes
internal fun Party.toStringResource(): Int {
    return when (this) {
        Party.Alone -> R.string.party_alone
        Party.Friend -> R.string.party_friend
        Party.OtherHalf -> R.string.party_other_half
        Party.Parent -> R.string.party_parent
        Party.Sibling -> R.string.party_sibling
        Party.Children -> R.string.party_children
        Party.Pet -> R.string.party_pet
        Party.Etc -> R.string.party_etc
    }
}