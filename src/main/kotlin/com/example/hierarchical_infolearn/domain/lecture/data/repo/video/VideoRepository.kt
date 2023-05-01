package com.example.hierarchical_infolearn.domain.lecture.data.repo.video

import com.example.hierarchical_infolearn.domain.lecture.data.entity.chapter.Chapter
import com.example.hierarchical_infolearn.domain.lecture.data.entity.video.Video
import org.springframework.data.jpa.repository.JpaRepository

interface VideoRepository: JpaRepository<Video, Long> {
    fun findBySequenceAndChapter(sequence: Int, chapter: Chapter): Video?

}