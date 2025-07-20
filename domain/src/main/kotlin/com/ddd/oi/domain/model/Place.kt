package com.ddd.oi.domain.model

data class Place(
    val id: String,
    val title: String,
    val category: String,
    val address: String,
    val roadAddress: String,
    val latitude: Double,
    val longitude: Double,
    val categoryColor: String,
) {
    val shownAddress: String get() = roadAddress.ifBlank { address }
}