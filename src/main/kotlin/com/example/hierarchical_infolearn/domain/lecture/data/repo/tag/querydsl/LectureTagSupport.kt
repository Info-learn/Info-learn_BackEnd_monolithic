package com.example.hierarchical_infolearn.domain.lecture.data.repo.tag.querydsl

import com.example.hierarchical_infolearn.domain.lecture.data.entity.tag.Tag

interface LectureTagSupport {
    fun queryAllLectureTagNoOffset(usageCount: Long?, limit: Long): List<Tag>?
}