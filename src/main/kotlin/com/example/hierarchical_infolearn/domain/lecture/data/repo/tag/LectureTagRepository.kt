package com.example.hierarchical_infolearn.domain.lecture.data.repo.tag

import com.example.hierarchical_infolearn.domain.lecture.data.entity.tag.Tag
import com.example.hierarchical_infolearn.domain.lecture.data.repo.tag.querydsl.LectureTagSupport
import org.springframework.data.jpa.repository.JpaRepository

interface LectureTagRepository: JpaRepository<Tag, String> ,LectureTagSupport {
}