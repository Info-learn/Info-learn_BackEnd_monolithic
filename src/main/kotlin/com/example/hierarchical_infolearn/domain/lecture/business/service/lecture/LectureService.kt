package com.example.hierarchical_infolearn.domain.lecture.business.service.lecture

import com.example.hierarchical_infolearn.domain.lecture.business.dto.request.lecture.ChangeLectureRequest
import com.example.hierarchical_infolearn.domain.lecture.business.dto.request.lecture.CreateLectureRequest
import com.example.hierarchical_infolearn.domain.lecture.business.dto.request.lecture.LectureTagRequest
import com.example.hierarchical_infolearn.domain.lecture.business.dto.response.lecture.LectureIdResponse
import com.example.hierarchical_infolearn.domain.lecture.business.dto.response.lecture.LectureSearchResponse
import com.example.hierarchical_infolearn.domain.lecture.business.dto.response.lecture.MaxLectureResponse
import com.example.hierarchical_infolearn.domain.lecture.business.dto.response.lecture.MiniLectureListResponse
import com.example.hierarchical_infolearn.domain.lecture.business.dto.response.tag.TagNameListResponse
import com.example.hierarchical_infolearn.global.file.dto.ImageFileRequest
import com.example.hierarchical_infolearn.global.file.dto.PreSignedUrlResponse
import java.time.LocalDateTime

interface LectureService {
    fun createLecture(req: CreateLectureRequest): LectureIdResponse
    fun getLecture(lectureId: String): MaxLectureResponse
    fun getLectureList(time: LocalDateTime?, limit: Long, tag: String?): MiniLectureListResponse
    fun searchLectureList(q: String, idx: Int, size: Int): LectureSearchResponse
    fun changeLectureThumbnail(lectureId: String, req: ImageFileRequest): PreSignedUrlResponse
    fun changeLecture(lectureId: String, req: ChangeLectureRequest)
    fun addLectureTag(lectureId: String, req: List<LectureTagRequest>)
    fun deleteLecture(lectureId: String)
    fun deleteLectureTag(lectureId: String, req: List<LectureTagRequest>)
    fun getLectureTag(usageCount: Long?, limit: Long): TagNameListResponse
}