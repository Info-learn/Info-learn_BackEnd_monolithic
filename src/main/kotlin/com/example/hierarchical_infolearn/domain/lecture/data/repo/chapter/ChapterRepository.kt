package com.example.hierarchical_infolearn.domain.lecture.data.repo.chapter

import com.example.hierarchical_infolearn.domain.lecture.data.entity.Lecture
import com.example.hierarchical_infolearn.domain.lecture.data.entity.chapter.Chapter
import org.springframework.data.jpa.repository.JpaRepository

interface ChapterRepository: JpaRepository<Chapter,Long> {

    fun findBySequenceAndLecture(sequence: Int, lecture: Lecture): Chapter?
    fun findByIdAndLecture(id: Long, lecture: Lecture): Chapter?
}