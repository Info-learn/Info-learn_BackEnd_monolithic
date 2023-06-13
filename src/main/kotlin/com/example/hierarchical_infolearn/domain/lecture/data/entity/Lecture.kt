package com.example.hierarchical_infolearn.domain.lecture.data.entity

import com.example.hierarchical_infolearn.domain.lecture.business.dto.request.lecture.ChangeLectureRequest
import com.example.hierarchical_infolearn.domain.lecture.business.dto.response.lecture.MaxLectureResponse
import com.example.hierarchical_infolearn.domain.lecture.business.dto.response.lecture.MiniLectureResponse
import com.example.hierarchical_infolearn.domain.lecture.data.entity.chapter.Chapter
import com.example.hierarchical_infolearn.domain.lecture.data.entity.tag.TagUsage
import com.example.hierarchical_infolearn.global.base.entity.BaseAuthorEntity
import org.hibernate.annotations.SQLDelete
import org.hibernate.annotations.Where
import javax.persistence.*

@Entity
@Table(name = "lecture", indexes = [
    Index(name = "i_search_title", columnList = "search_title"),
    Index(name = "i_search_explanation", columnList = "search_explanation")
    ])
@Where(clause = "is_deleted = false")
@SQLDelete(sql = "UPDATE `tbl_lecture` SET is_deleted = true WHERE id = ?")
class Lecture(
    id: String,
    title: String,
    explanation: String,
    searchTitle: String,
    searchExplanation: String,
): BaseAuthorEntity() {
    @Id
    val id: String = id

    @Column(name = "title", length = 100, nullable = false, unique = false)
    var title: String = title
        protected set

    @Column(name = "explanation", length = 100, nullable = false, unique = false)
    var explanation: String = explanation
        protected set

    @Column(name = "search_title", length = 150, nullable = false, unique = false)
    var searchTitle: String = searchTitle
        protected set

    @Column(name = "search_explanation", length = 200, nullable = false, unique = false)
    var searchExplanation: String = searchExplanation
        protected set

    @Column(name = "lecture_thumbnail_url", length = 500, nullable = true, unique = false)
    var lectureThumbnail: String? = null
        protected set

    @Column(name = "is_deleted", nullable = false)
    var isDeleted: Boolean = false
        protected set

    @OneToMany(mappedBy = "lecture" ,cascade = [CascadeType.REMOVE])
    var chapters: MutableSet<Chapter> = HashSet()
        protected set

    @OneToMany(mappedBy = "lecture")
    var tagUsageList: MutableSet<TagUsage> = HashSet()
        protected set

    fun uploadLectureThumbnail(file: String) {
        this.lectureThumbnail = file
    }

    fun toLectureDetailResponse(userId: String): MaxLectureResponse {
        return MaxLectureResponse(
            lectureId = this.id,
            title = this.title,
            explanation = this.explanation,
            lectureThumbnailUrl = this.lectureThumbnail!!,
            chapters = this.chapters.let {
                it.filter { it1 ->
                    !it1.isDeleted
                }.map { it1 -> it1.toChapterDetailResponse(userId)
                }
            }.toSet(),
            tagNameList = tagUsageList.map {
                it.tag.toTagNameResponse()
            }.toSet()
        )
    }

    fun toMiniLectureResponse(): MiniLectureResponse{
        return MiniLectureResponse(
            lectureId = this.id,
            title = this.title,
            explanation = this.explanation,
            lectureThumbnailUrl = this.lectureThumbnail!!,
            tagNameList = tagUsageList.map {
                it.tag.toTagNameResponse()
            }.toSet(),
            createdAt = this.createdAt!!,
            createdBy = this.createdBy!!
        )
    }

    fun changeLecture(req: ChangeLectureRequest) {
        req.titleRequest?.let {
            this.title = it.title
            this.searchTitle = it.searchTitle
        }
        req.explanationRequest?.let {
            this.explanation = it.explanation
            this.searchExplanation = it.searchExplanation
        }
    }
}