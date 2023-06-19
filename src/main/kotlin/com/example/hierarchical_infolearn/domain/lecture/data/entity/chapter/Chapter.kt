package com.example.hierarchical_infolearn.domain.lecture.data.entity.chapter

import com.example.hierarchical_infolearn.domain.lecture.business.dto.request.chapter.ChangeChapterRequest
import com.example.hierarchical_infolearn.domain.lecture.business.dto.response.chapter.ChapterDetailResponse
import com.example.hierarchical_infolearn.domain.lecture.business.dto.response.chapter.ChapterMiniResponse
import com.example.hierarchical_infolearn.domain.lecture.data.entity.Lecture
import com.example.hierarchical_infolearn.domain.lecture.data.entity.video.Video
import com.example.hierarchical_infolearn.global.base.entity.BaseAuthorEntity
import org.hibernate.annotations.SQLDelete
import org.hibernate.annotations.Where
import javax.persistence.*

@Entity
@Table(name = "chapter")
@Where(clause = "is_deleted = false")
@SQLDelete(sql = "UPDATE `chapter` SET is_deleted = true WHERE id = ?")
class Chapter(
    title: String,
    sequence: Int,
    lecture: Lecture
): BaseAuthorEntity() {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null

    @Column(name = "title", length = 100, nullable = false, unique = false)
    var title: String = title
        protected set

    @Column(name = "sequence", nullable = false)
    var sequence: Int = sequence
        protected set

    @Column(name = "is_deleted", nullable = false)
    var isDeleted: Boolean = false
        protected set

    @ManyToOne(fetch = FetchType.LAZY, cascade = [CascadeType.PERSIST])
    @JoinColumn(name = "lecture_id")
    var lecture: Lecture = lecture

    @OneToMany(mappedBy = "chapter", cascade = [CascadeType.REMOVE])
    var videos: MutableSet<Video> = HashSet()
        protected set

    fun toChapterDetailResponse(userId: String): ChapterDetailResponse {
        return ChapterDetailResponse(
            chapterId = this.id!!,
            title = this.title,
            sequence = this.sequence,
            videos = this.videos.filter {
                !it.isDeleted
            }.let {
                it.map { it1 -> it1.toVideoDetailResponse(userId)
                }
            }.sortedBy { it.sequence }
        )
    }

    fun toChapterMiniResponse(): ChapterMiniResponse {
        return ChapterMiniResponse(
            chapterId = this.id!!,
            title = this.title,
            sequence = this.sequence,
        )
    }

    fun updateSequence(sequence: Int) {
        this.sequence = sequence
    }

    fun changeChapter(req: ChangeChapterRequest) {
        req.title?.let {
            this.title = it
        }
    }
}
