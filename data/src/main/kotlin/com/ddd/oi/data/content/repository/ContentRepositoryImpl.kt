package com.ddd.oi.data.content.repository

import com.ddd.oi.data.content.mapper.toDomain
import com.ddd.oi.data.content.mapper.toRequest
import com.ddd.oi.data.content.remote.ContentApi
import com.ddd.oi.domain.model.Content
import com.ddd.oi.domain.repository.ContentRepository
import javax.inject.Inject

class ContentRepositoryImpl @Inject constructor(
    private val contentApi: ContentApi
) : ContentRepository {
    
    override suspend fun getContents(userId: Long): List<Content> {
        val response = contentApi.getContents(userId)
        if (response.isSuccessful) {
            return response.body()?.data?.map { it.toDomain() } ?: emptyList()
        } else {
            throw Exception("Failed to get contents: ${response.errorBody()?.string()}")
        }
    }
    
    override suspend fun createContent(content: Content, userId: Long): Content {
        val response = contentApi.createContent(userId, content.toRequest())
        if (response.isSuccessful) {
            return response.body()?.data?.toDomain()
                ?: throw IllegalStateException("Failed to create content")
        } else {
            throw Exception("Failed to create content: ${response.errorBody()?.string()}")
        }
    }
    
    override suspend fun getContent(contentsId: Long, userId: Long): Content {
        val response = contentApi.getContent(userId, contentsId)
        if (response.isSuccessful) {
            return response.body()?.data?.toDomain()
                ?: throw IllegalStateException("Content not found")
        } else {
            throw Exception("Failed to get content: ${response.errorBody()?.string()}")
        }
    }
    
    override suspend fun updateContent(contentsId: Long, content: Content, userId: Long): Content {
        val response = contentApi.updateContent(userId, contentsId, content.toRequest())
        if (response.isSuccessful) {
            return response.body()?.data?.toDomain()
                ?: throw IllegalStateException("Failed to update content")
        } else {
            throw Exception("Failed to update content: ${response.errorBody()?.string()}")
        }
    }
    
    override suspend fun deleteContent(contentsId: Long, userId: Long): Boolean {
        val response = contentApi.deleteContent(userId, contentsId)
        if (response.isSuccessful) {
            return response.body()?.data ?: false
        } else {
            throw Exception("Failed to delete content: ${response.errorBody()?.string()}")
        }
    }
}