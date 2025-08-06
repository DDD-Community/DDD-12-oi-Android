package com.ddd.oi.domain.repository

import com.ddd.oi.domain.model.Content

interface ContentRepository {
    suspend fun getContents(userId: Long = 1L): List<Content>
    suspend fun createContent(content: Content, userId: Long = 1L): Content
    suspend fun getContent(contentsId: Long, userId: Long = 1L): Content
    suspend fun updateContent(contentsId: Long, content: Content, userId: Long = 1L): Content
    suspend fun deleteContent(contentsId: Long, userId: Long = 1L): Boolean
}