package com.ddd.oi.domain.model

data class Spot(
    val id: Long,
    val name: String,
    val address: String,
    val description: String?,
    val imageUrl: String?,
    val latitude: Double?,
    val longitude: Double?,
    val category: String
)