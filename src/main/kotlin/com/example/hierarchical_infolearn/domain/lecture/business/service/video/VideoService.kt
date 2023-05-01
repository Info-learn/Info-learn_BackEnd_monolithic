package com.example.hierarchical_infolearn.domain.lecture.business.service.video

import com.example.hierarchical_infolearn.domain.lecture.business.dto.request.ChangeVideoSequenceRequest
import com.example.hierarchical_infolearn.domain.lecture.business.dto.request.CreateVideoRequest
import com.example.hierarchical_infolearn.global.file.dto.PreSignedUrlResponse

interface VideoService {
    fun createVideo(chapterId: Long, req: CreateVideoRequest): PreSignedUrlResponse
    fun deleteVideo(chapterId: Long, videoId: Long)
    fun changeVideoSequence(chapterId: Long, req: ChangeVideoSequenceRequest)
}