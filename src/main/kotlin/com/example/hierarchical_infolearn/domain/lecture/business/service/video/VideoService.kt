package com.example.hierarchical_infolearn.domain.lecture.business.service.video

import com.example.hierarchical_infolearn.domain.lecture.business.dto.request.video.ChangeVideoChapterRequest
import com.example.hierarchical_infolearn.domain.lecture.business.dto.request.video.ChangeVideoRequest
import com.example.hierarchical_infolearn.domain.lecture.business.dto.request.video.ChangeVideoSequenceRequest
import com.example.hierarchical_infolearn.domain.lecture.business.dto.request.video.CreateVideoRequest
import com.example.hierarchical_infolearn.global.file.dto.PreSignedUrlResponse

interface VideoService {
    fun createVideo(chapterId: Long, req: CreateVideoRequest): PreSignedUrlResponse
    fun deleteVideo(chapterId: Long, sequence: Int)
    fun changeVideoSequence(sequence: Int, chapterId: Long ,req: ChangeVideoSequenceRequest)
    fun changeVideo(sequence: Int, chapterId:Long, req: ChangeVideoRequest)
    fun changeVideoChapter(sequence: Int, chapterId: Long, req: ChangeVideoChapterRequest)
}