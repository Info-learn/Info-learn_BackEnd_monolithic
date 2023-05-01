package com.example.hierarchical_infolearn.domain.lecture.business.service.video

import com.example.hierarchical_infolearn.domain.lecture.business.dto.request.video.ChangeVideoChapterRequest
import com.example.hierarchical_infolearn.domain.lecture.business.dto.request.video.ChangeVideoRequest
import com.example.hierarchical_infolearn.domain.lecture.business.dto.request.video.ChangeVideoSequenceRequest
import com.example.hierarchical_infolearn.domain.lecture.business.dto.request.video.CreateVideoRequest
import com.example.hierarchical_infolearn.domain.lecture.business.dto.response.video.VideoUrlResponse
import com.example.hierarchical_infolearn.global.file.dto.PreSignedUrlResponse

interface VideoService {
    fun createVideo(chapterId: Long, req: CreateVideoRequest): PreSignedUrlResponse
    fun deleteVideo(videoId: Long)
    fun changeVideoSequence(sequence: Int, chapterId: Long ,req: ChangeVideoSequenceRequest)
    fun changeVideo(videoId:Long, req: ChangeVideoRequest)
    fun changeVideoChapter(videoId: Long, req: ChangeVideoChapterRequest)
    fun getVideo(videoId: Long): VideoUrlResponse
}