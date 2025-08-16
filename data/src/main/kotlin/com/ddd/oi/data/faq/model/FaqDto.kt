package com.ddd.oi.data.faq.model

import kotlinx.serialization.Serializable

@Serializable
data class FaqDto(
    val id: Long? = null,
    val title: String,
    val content: String,
    val createdAt: String? = null
)

@Serializable
data class FaqPageDto(
    val content: List<FaqDto>,
    val totalElements: Int? = null,
    val totalPages: Int? = null,
    val hasNext: Boolean? = null,
    val pageNumber: Int,
    val pageSize: Int
)