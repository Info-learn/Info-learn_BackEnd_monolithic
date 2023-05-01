package com.example.hierarchical_infolearn.domain.lecture.business.service.chapter

import com.example.hierarchical_infolearn.domain.lecture.business.dto.request.ChangeChapterSequenceRequest
import com.example.hierarchical_infolearn.domain.lecture.business.dto.request.CreateChapterRequest

interface ChapterService {
    fun createChapter(lectureId: String, req: CreateChapterRequest)
    fun deleteChapter(lectureId: String, chapterId: Long)
    fun changeChapterSequence(lectureId: String, req: ChangeChapterSequenceRequest)
}