package com.example.hierarchical_infolearn.domain.lecture.business.service.video

import com.example.hierarchical_infolearn.domain.lecture.business.dto.request.video.ChangeVideoRequest
import com.example.hierarchical_infolearn.domain.lecture.business.dto.request.video.ChangeVideoSequenceRequest
import com.example.hierarchical_infolearn.domain.lecture.business.dto.request.video.CreateVideoRequest
import com.example.hierarchical_infolearn.global.file.dto.PreSignedUrlResponse

interface VideoService {
    fun createVideo(chapterId: Long, req: CreateVideoRequest): PreSignedUrlResponse
    fun deleteVideo(chapterId: Long, videoId: Long)
    fun changeVideoSequence(chapterId: Long, req: ChangeVideoSequenceRequest)
    fun changeVideo(chapterId: Long, req: ChangeVideoRequest)
}