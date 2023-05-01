package com.example.hierarchical_infolearn.domain.lecture.data.repo.lecture

import com.example.hierarchical_infolearn.domain.lecture.data.entity.Lecture
import com.example.hierarchical_infolearn.domain.lecture.data.repo.lecture.querydsl.LectureSupport
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

interface LectureRepository: JpaRepository<Lecture,String>, LectureSupport {


    fun findBySearchTitleContaining(searchTitle: String, pageable: Pageable): Page<Lecture>?
    fun findBySearchExplanationContaining(searchExplanation: String, pageable: Pageable): Page<Lecture>?


}