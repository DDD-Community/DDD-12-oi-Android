package com.ddd.oi.domain.model

data class Place(
    val title: String,
    val category: String,
    val address: String,
    val roadAddress: String,
    val mapX: Int,
    val mapY: Int,
)