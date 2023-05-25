package com.example.hierarchical_infolearn.domain.lecture.data.repo.lecture.querydsl

import com.example.hierarchical_infolearn.domain.lecture.data.entity.Lecture
import java.time.LocalDateTime

interface LectureSupport {
    fun queryAllLectureNoOffset(time: LocalDateTime?, limit: Long, tag: String?): List<Lecture>?
    fun queryAllLecture(idx: Int, size: Int): List<Lecture>?
}