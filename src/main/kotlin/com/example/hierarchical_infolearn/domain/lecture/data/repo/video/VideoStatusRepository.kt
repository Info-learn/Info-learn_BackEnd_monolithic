package com.example.hierarchical_infolearn.domain.lecture.data.repo.video

import com.example.hierarchical_infolearn.domain.lecture.data.entity.video.Video
import com.example.hierarchical_infolearn.domain.lecture.data.entity.video.status.VideoStatus
import org.springframework.data.jpa.repository.JpaRepository

interface VideoStatusRepository: JpaRepository<VideoStatus, Long> {
    fun existsByVideoAndCreatedBy(video: Video, userId: String): Boolean
}