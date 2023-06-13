package com.example.hierarchical_infolearn.domain.lecture.data.repo.tag

import com.example.hierarchical_infolearn.domain.lecture.data.entity.Lecture
import com.example.hierarchical_infolearn.domain.lecture.data.entity.tag.Tag
import com.example.hierarchical_infolearn.domain.lecture.data.entity.tag.TagUsage
import org.springframework.data.jpa.repository.JpaRepository

interface LectureTagUsageRepository: JpaRepository<TagUsage, TagUsage.IdClass> {

    fun existsByLectureAndTag(lecture: Lecture, tag: Tag): Boolean
    fun deleteByLectureAndTag(lecture: Lecture, tag: Tag)

}