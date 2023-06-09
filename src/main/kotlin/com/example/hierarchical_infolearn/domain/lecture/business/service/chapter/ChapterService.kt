package com.example.hierarchical_infolearn.domain.lecture.business.service.chapter

import com.example.hierarchical_infolearn.domain.lecture.business.dto.request.chapter.ChangeChapterRequest
import com.example.hierarchical_infolearn.domain.lecture.business.dto.request.chapter.ChangeChapterSequenceRequest
import com.example.hierarchical_infolearn.domain.lecture.business.dto.request.chapter.CreateChapterRequest
import com.example.hierarchical_infolearn.domain.lecture.business.dto.response.chapter.ChapterListResponse

interface ChapterService {
    fun createChapter(req: CreateChapterRequest)
    fun deleteChapter(lectureId: String, chapterId: Long)
    fun changeChapterSequence(lectureId: String, req: ChangeChapterSequenceRequest)
    fun changeChapter(chapterId: Long , req: ChangeChapterRequest)
    fun getChapters(lectureId: String): ChapterListResponse
}