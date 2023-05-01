package com.example.hierarchical_infolearn.domain.lecture.business.service.chapter

import com.example.hierarchical_infolearn.domain.lecture.business.dto.request.chapter.ChangeChapterSequenceRequest
import com.example.hierarchical_infolearn.domain.lecture.business.dto.request.chapter.CreateChapterRequest
import com.example.hierarchical_infolearn.domain.lecture.data.entity.chapter.Chapter
import com.example.hierarchical_infolearn.domain.lecture.data.repo.chapter.ChapterRepository
import com.example.hierarchical_infolearn.domain.lecture.data.repo.lecture.LectureRepository
import com.example.hierarchical_infolearn.domain.lecture.exception.AlreadyUsingSequence
import com.example.hierarchical_infolearn.domain.lecture.exception.ChapterNotFoundException
import com.example.hierarchical_infolearn.domain.lecture.exception.IncorrectChapter
import com.example.hierarchical_infolearn.domain.lecture.exception.LectureNotFoundException
import com.example.hierarchical_infolearn.global.error.common.NoAuthenticationException
import com.example.hierarchical_infolearn.global.utils.CurrentUtil
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class ChapterServiceImpl(
    private val lectureRepository: LectureRepository,
    private val chapterRepository: ChapterRepository,
    private val currentUtil: CurrentUtil,
):ChapterService {

    override fun createChapter(lectureId: String, req: CreateChapterRequest) {
        val lectureEntity = lectureRepository.findByIdOrNull(lectureId)?: throw LectureNotFoundException(lectureId)
        isOwner(lectureEntity.createdBy!!)

        lectureEntity.chapters.firstOrNull{
            !it.isDeleted && it.sequence == req.sequence
        }?.let {
            throw AlreadyUsingSequence(req.sequence.toString())
        }

        chapterRepository.save(
            Chapter(
                req.title,
                req.sequence,
                lectureEntity,
            )
        )
    }

    override fun deleteChapter(lectureId: String, chapterId: Long) {
        val lectureEntity = lectureRepository.findByIdOrNull(lectureId)?: throw LectureNotFoundException(lectureId)
        isOwner(lectureEntity.createdBy!!)
        val chapterEntity = chapterRepository.findByIdOrNull(chapterId)?: throw ChapterNotFoundException(chapterId.toString())

        lectureEntity.chapters.firstOrNull{
            !it.isDeleted && it == chapterEntity
        }?.let {
            chapterRepository.delete(it)
        }
    }

    override fun changeChapterSequence(lectureId: String, req: ChangeChapterSequenceRequest) {
        val lectureEntity = lectureRepository.findByIdOrNull(lectureId)?: throw LectureNotFoundException(lectureId)
        isOwner(lectureEntity.createdBy!!)
        val chapterEntity = chapterRepository.findByIdOrNull(req.chapterId)?: throw ChapterNotFoundException(req.chapterId.toString())

        lectureEntity.chapters.firstOrNull{
            !it.isDeleted && it == chapterEntity
        }?: IncorrectChapter(req.chapterId.toString())

        val targetChapterEntity = chapterRepository.findBySequenceAndLecture(req.sequence, lectureEntity)?: throw ChapterNotFoundException(req.sequence.toString())

        val sequence = chapterEntity.sequence
        chapterEntity.updateSequence(req.sequence)
        targetChapterEntity.updateSequence(sequence)
    }

    private fun isOwner(createdBy: String){
        val teacher = currentUtil.getCurrentUser()
        if(createdBy != teacher.accountId) throw NoAuthenticationException(teacher.accountId)
    }

}