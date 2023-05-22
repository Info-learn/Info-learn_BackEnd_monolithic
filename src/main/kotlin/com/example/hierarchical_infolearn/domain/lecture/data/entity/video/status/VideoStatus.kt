package com.example.hierarchical_infolearn.domain.lecture.data.entity.video.status

import com.example.hierarchical_infolearn.domain.lecture.data.entity.common.VideoStatusType
import com.example.hierarchical_infolearn.domain.lecture.data.entity.video.Video
import com.example.hierarchical_infolearn.global.base.entity.BaseAuthorEntity
import javax.persistence.*

@Entity(name = "tbl_video_status")
@Table(name = "tbl_video_status")
class VideoStatus(
    video: Video,
    statusType: VideoStatusType,
):BaseAuthorEntity() {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null

    @Column(name = "status", unique = false, nullable = false)
    @Enumerated(EnumType.STRING)
    var status: VideoStatusType = statusType
        protected set

    @ManyToOne(fetch = FetchType.LAZY, cascade = [CascadeType.PERSIST])
    @JoinColumn(name = "video_id")
    var video: Video = video
        protected set
}