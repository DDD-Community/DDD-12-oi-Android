package com.ddd.oi.data.faq.mapper

import com.ddd.oi.data.faq.model.FaqDto
import com.ddd.oi.data.faq.model.FaqPageDto
import com.ddd.oi.domain.model.Faq
import com.ddd.oi.domain.model.FaqPage

fun FaqDto.toDomain(): Faq {
    return Faq(
        id = id ?: 0L,
        question = title,
        answer = content,
        createdAt = createdAt ?: ""
    )
}

fun FaqPageDto.toDomain(): FaqPage {
    return FaqPage(
        faqs = content.map { it.toDomain() },
        totalCount = totalElements ?: content.size,
        hasNext = hasNext ?: false,
        page = pageNumber,
        size = pageSize
    )
}