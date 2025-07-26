package com.ddd.oi.presentation.core.navigation

import android.net.Uri
import android.os.Bundle
import androidx.navigation.NavType
import androidx.savedstate.SavedState
import com.ddd.oi.domain.model.schedule.Schedule
import com.ddd.oi.domain.model.schedule.SchedulePlace
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json

sealed interface Route {
    @Serializable
    data class UpsertSchedule(val mode: UpsertMode = UpsertMode.CREATE) : Route

    @Serializable
    data class ScheduleDetail(val schedule: Schedule) : Route

    @Serializable
    data class SearchPlace(val scheduleId: Long, val targetDate: String) : Route

    @Serializable
    data class UpsertPlace(
        val scheduleId: Long,
        val schedulePlace: SchedulePlace
    ) : Route
}

sealed interface MainTabRoute : Route {
    @Serializable
    data object Home : MainTabRoute

    @Serializable
    data object Schedule : MainTabRoute
}


@Serializable
enum class UpsertMode {
    CREATE,
    EDIT,
    COPY
}

@Serializable
enum class SchedulePlaceMode {
    ADD,
    UPDATE
}

object ScheduleNavType : NavType<Schedule>(isNullableAllowed = false) {
    override fun put(bundle: SavedState, key: String, value: Schedule) {
        bundle.putString(key, Json.encodeToString(value))
    }

    override fun serializeAsValue(value: Schedule): String {
        return Uri.encode(Json.encodeToString(value))
    }

    override fun get(bundle: SavedState, key: String): Schedule? {
        return Json.decodeFromString(bundle.getString(key) ?: return null)
    }

    override fun parseValue(value: String): Schedule {
        return Json.decodeFromString(Uri.decode(value))
    }
}

object SchedulePlaceNavType : NavType<SchedulePlace>(isNullableAllowed = false) {
    override fun put(bundle: Bundle, key: String, value: SchedulePlace) {
        bundle.putString(key, Json.encodeToString(value))
    }

    override fun serializeAsValue(value: SchedulePlace): String {
        return Uri.encode(Json.encodeToString(value))
    }

    override fun get(bundle: Bundle, key: String): SchedulePlace? {
        return Json.decodeFromString(bundle.getString(key) ?: return null)
    }

    override fun parseValue(value: String): SchedulePlace {
        return Json.decodeFromString(Uri.decode(value))
    }
}