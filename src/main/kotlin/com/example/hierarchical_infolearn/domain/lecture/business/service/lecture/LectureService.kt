package com.example.hierarchical_infolearn.domain.lecture.business.service.lecture

import com.example.hierarchical_infolearn.domain.lecture.business.dto.request.lecture.ChangeLectureRequest
import com.example.hierarchical_infolearn.domain.lecture.business.dto.request.lecture.CreateLectureRequest
import com.example.hierarchical_infolearn.domain.lecture.business.dto.response.lecture.LectureIdResponse
import com.example.hierarchical_infolearn.domain.lecture.business.dto.response.lecture.MaxLectureResponse
import com.example.hierarchical_infolearn.domain.lecture.business.dto.response.lecture.MiniLectureListResponse
import com.example.hierarchical_infolearn.global.file.dto.ImageFileRequest
import com.example.hierarchical_infolearn.global.file.dto.PreSignedUrlResponse
import java.time.LocalDateTime

interface LectureService {
    fun createLecture(req: CreateLectureRequest): LectureIdResponse
    fun getLecture(lectureId: String): MaxLectureResponse
    fun getLectureList(time: LocalDateTime?, limit: Long): MiniLectureListResponse
    fun searchLectureList(q: String, type: String, idx: Int, size: Int): MiniLectureListResponse
    fun changeLectureThumbnail(lectureId: String, req: ImageFileRequest): PreSignedUrlResponse
    fun changeLecture(lectureId: String, req: ChangeLectureRequest)
    fun addLectureTag(lectureId: String, tagId: String)
    fun deleteLecture(lectureId: String)
    fun deleteLectureTag(lectureId: String, tagId: String)
}