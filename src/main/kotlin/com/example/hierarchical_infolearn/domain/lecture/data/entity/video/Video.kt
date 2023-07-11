package com.example.hierarchical_infolearn.domain.lecture.data.entity.video

import com.example.hierarchical_infolearn.domain.lecture.business.dto.request.video.ChangeVideoRequest
import com.example.hierarchical_infolearn.domain.lecture.business.dto.response.video.VideoDetailResponse
import com.example.hierarchical_infolearn.domain.lecture.business.dto.response.video.VideoMaxResponse
import com.example.hierarchical_infolearn.domain.lecture.data.entity.chapter.Chapter
import com.example.hierarchical_infolearn.domain.lecture.data.entity.video.status.VideoStatus
import com.example.hierarchical_infolearn.global.base.entity.BaseAuthorEntity
import org.hibernate.annotations.SQLDelete
import org.hibernate.annotations.Where
import javax.persistence.*

@Entity
@Table(name = "video")
@Where(clause = "is_deleted = false")
@SQLDelete(sql = "UPDATE `video` SET is_deleted = true WHERE id = ?")
class Video(
    title: String,
    playTime : Int,
    sequence: Int,
    chapter: Chapter,
): BaseAuthorEntity() {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null

    @Column(name = "title", length = 100, nullable = false, unique = false)
    var title: String = title
        protected set

    @Column(name = "play_time", nullable = false, unique = false)
    var playTime: Int = playTime
        protected set

    @Column(name = "sequence", nullable = false)
    var sequence: Int = sequence
        protected set

    @Column(name = "video_url", nullable = true, length = 500)
    var videoUrl: String? = null
        protected set

    @Column(name = "is_deleted", nullable = false)
    var isDeleted: Boolean = false
        protected set

    @ManyToOne(fetch = FetchType.LAZY, cascade = [CascadeType.PERSIST])
    @JoinColumn(name = "chapter_id")
    var chapter: Chapter = chapter

    @OneToMany(mappedBy = "video", cascade = [CascadeType.REMOVE])
    var videoStatus: MutableSet<VideoStatus> = HashSet()
        protected set

    fun toVideoDetailResponse(userId: String): VideoDetailResponse {
        return VideoDetailResponse(
            videoId = this.id!!,
            title = this.title,
            sequence = this.sequence,
            hour = this.playTime / 3600,
            minute = (this.playTime % 3600) / 60,
            second = this.playTime % 60,
            status = videoStatus.firstOrNull{
                it.createdBy == userId
            }?.status
            )
    }

    fun uploadVideoUrl(file: String) {
        this.videoUrl = file
    }

    fun updateSequence(sequence: Int) {
        this.sequence = sequence
    }

    fun changeVideo(req: ChangeVideoRequest) {
        req.title?.let {
            this.title = it
        }
        req.playTime?.let {
            this.playTime = it
        }
    }

    fun changeChapter(chapter: Chapter) {
        this.chapter = chapter
    }
    fun toVideoUrlResponse(userId: String): VideoMaxResponse {
        return VideoMaxResponse(
            videoId = this.id!!,
            title = this.title,
            sequence = this.sequence,
            hour = this.playTime / 3600,
            minute = (this.playTime % 3600) / 60,
            second = this.playTime % 60,
            status = videoStatus.firstOrNull{
                        it.createdBy == userId
                    }?.status,
            videoUrl = this.videoUrl!!
        )
    }

}