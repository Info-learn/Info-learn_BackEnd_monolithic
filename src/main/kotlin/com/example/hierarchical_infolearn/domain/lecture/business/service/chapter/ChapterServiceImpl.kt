package com.example.hierarchical_infolearn.domain.lecture.business.service.chapter

import com.example.hierarchical_infolearn.domain.lecture.business.dto.request.chapter.ChangeChapterRequest
import com.example.hierarchical_infolearn.domain.lecture.business.dto.request.chapter.ChangeChapterSequenceRequest
import com.example.hierarchical_infolearn.domain.lecture.business.dto.request.chapter.CreateChapterRequest
import com.example.hierarchical_infolearn.domain.lecture.business.dto.response.chapter.ChapterListResponse
import com.example.hierarchical_infolearn.domain.lecture.data.entity.chapter.Chapter
import com.example.hierarchical_infolearn.domain.lecture.data.repo.chapter.ChapterRepository
import com.example.hierarchical_infolearn.domain.lecture.data.repo.lecture.LectureRepository
import com.example.hierarchical_infolearn.domain.lecture.exception.AlreadyUsingSequence
import com.example.hierarchical_infolearn.domain.lecture.exception.ChapterNotFoundException
import com.example.hierarchical_infolearn.domain.lecture.exception.DuplicationSequenceException
import com.example.hierarchical_infolearn.domain.lecture.exception.LectureNotFoundException
import com.example.hierarchical_infolearn.global.error.common.NoAuthenticationException
import com.example.hierarchical_infolearn.global.utils.CurrentUtil
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import javax.transaction.Transactional

@Service
@Transactional
class ChapterServiceImpl(
    private val lectureRepository: LectureRepository,
    private val chapterRepository: ChapterRepository,
    private val currentUtil: CurrentUtil,
):ChapterService {

    override fun createChapter(req: CreateChapterRequest) {
        val lectureEntity = lectureRepository.findByIdOrNull(req.lectureId)?: throw LectureNotFoundException
        isOwner(lectureEntity.createdBy!!)

        lectureEntity.chapters.firstOrNull{
            !it.isDeleted && it.sequence == req.sequence
        } ?: throw AlreadyUsingSequence

        chapterRepository.save(
            Chapter(
                req.title,
                req.sequence,
                lectureEntity,
            )
        )
    }

    override fun deleteChapter(lectureId: String, chapterId: Long) {
        val lectureEntity = lectureRepository.findByIdOrNull(lectureId)?: throw LectureNotFoundException
        isOwner(lectureEntity.createdBy!!)
        val chapterEntity = chapterRepository.findByIdOrNull(chapterId)?: throw ChapterNotFoundException

        lectureEntity.chapters.firstOrNull{
            !it.isDeleted && it == chapterEntity
        }?.let {
            chapterRepository.delete(it)
        }
    }

    override fun changeChapterSequence(lectureId: String, req: ChangeChapterSequenceRequest) {
        val lectureEntity = lectureRepository.findByIdOrNull(lectureId)?: throw LectureNotFoundException
        isOwner(lectureEntity.createdBy!!)

        val duplicationChecker = req.chapterSequences.map {
            it.sequence
        }.toSet()

        if (duplicationChecker.size != req.chapterSequences.size) throw DuplicationSequenceException

        req.chapterSequences.forEach {
            val chapterEntity = chapterRepository.findByIdAndLecture(it.chapterId, lectureEntity)?: throw ChapterNotFoundException
            chapterEntity.updateSequence(it.sequence)
        }
    }

    override fun changeChapter(chapterId: Long, req: ChangeChapterRequest) {
        val chapterEntity = chapterRepository.findByIdOrNull(chapterId)?: throw ChapterNotFoundException
        chapterEntity.changeChapter(req)
    }

    override fun getChapters(lectureId: String): ChapterListResponse {
        val lectureEntity = lectureRepository.findByIdOrNull(lectureId) ?: throw LectureNotFoundException
        isOwner(lectureEntity.createdBy!!)
        return ChapterListResponse(
            lectureEntity.chapters.map {
                it.toChapterMiniResponse()
            }.sortedBy { it.sequence }
        )
    }

    private fun isOwner(createdBy: String){
        val teacher = currentUtil.getCurrentUser()
        if(createdBy != teacher.accountId) throw NoAuthenticationException
    }

}