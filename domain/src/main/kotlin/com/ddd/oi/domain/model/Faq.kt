package com.ddd.oi.domain.model

data class Faq(
    val id: Long,
    val question: String,
    val answer: String,
    val createdAt: String
)

data class FaqPage(
    val faqs: List<Faq>,
    val totalCount: Int,
    val hasNext: Boolean,
    val page: Int,
    val size: Int
)