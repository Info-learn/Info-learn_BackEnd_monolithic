package com.example.hierarchical_infolearn.domain.lecture.business.service.lecture

import com.example.hierarchical_infolearn.domain.lecture.business.dto.request.ChangeLectureRequest
import com.example.hierarchical_infolearn.domain.lecture.business.dto.request.CreateLectureRequest
import com.example.hierarchical_infolearn.domain.lecture.business.dto.response.lecture.LectureIdResponse
import com.example.hierarchical_infolearn.domain.lecture.business.dto.response.lecture.MaxLectureResponse
import com.example.hierarchical_infolearn.domain.lecture.business.dto.response.lecture.MiniLectureListResponse
import com.example.hierarchical_infolearn.domain.lecture.data.entity.Lecture
import com.example.hierarchical_infolearn.domain.lecture.data.entity.tag.Tag
import com.example.hierarchical_infolearn.domain.lecture.data.entity.tag.TagUsage
import com.example.hierarchical_infolearn.domain.lecture.data.repo.lecture.LectureRepository
import com.example.hierarchical_infolearn.domain.lecture.data.repo.tag.LectureTagRepository
import com.example.hierarchical_infolearn.domain.lecture.data.repo.tag.LectureTagUsageRepository
import com.example.hierarchical_infolearn.domain.lecture.exception.IncorrectSearchType
import com.example.hierarchical_infolearn.domain.lecture.exception.LectureNotFoundException
import com.example.hierarchical_infolearn.domain.lecture.exception.LectureTagNotFound
import com.example.hierarchical_infolearn.global.error.common.NoAuthenticationException
import com.example.hierarchical_infolearn.global.file.dto.FileRequest
import com.example.hierarchical_infolearn.global.file.dto.PreSignedUrlResponse
import com.example.hierarchical_infolearn.global.utils.CurrentUtil
import com.example.hierarchical_infolearn.infra.s3.S3Util
import org.springframework.data.domain.PageRequest
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime
import java.util.*

@Service
@Transactional
class LectureServiceImpl(
    private val lectureRepository: LectureRepository,
    private val lectureTagRepository: LectureTagRepository,
    private val lectureTagUsageRepository: LectureTagUsageRepository,
    private val s3Util: S3Util,
    private val currentUtil: CurrentUtil,
):LectureService {

    override fun createLecture(req: CreateLectureRequest): LectureIdResponse {

        val lectureEntity = lectureRepository.save(
            Lecture(
                id = UUID.randomUUID().toString(),
                title = req.title,
                explanation = req.explanation,
                searchTitle = req.searchTitle,
                searchExplanation = req.searchExplanation,
            )
        )

        val (preSignedUrl, fileUrl) = preSignedUrl(req.lectureThumbnail.fileName, req.lectureThumbnail.contentType,
            lectureEntity.id
        )

        lectureEntity.uploadLectureThumbnail(fileUrl)

        req.tagNameList.map {

            val tag = lectureTagRepository.findByIdOrNull(it) ?: lectureTagRepository.save(
                Tag(
                    it
                )
            )
            lectureTagUsageRepository.save(
                TagUsage(
                    tag,
                    lectureEntity
                )
            )
        }

        return LectureIdResponse(lectureEntity.id,preSignedUrl)
    }


    override fun getLecture(lectureId: String): MaxLectureResponse {
        val lectureEntity = lectureRepository.findByIdOrNull(lectureId)?: throw LectureNotFoundException(lectureId)
        return lectureEntity.toLectureDetailResponse()
    }

    override fun getLectureList(time: LocalDateTime?, limit: Long): MiniLectureListResponse {
        val lectureEntityList = lectureRepository.queryAllLectureNoOffset(time, limit)
        lectureEntityList?: throw LectureNotFoundException(time.toString())

        return MiniLectureListResponse(
            lectureEntityList.map {
                it.toMiniLectureResponse()
            }
        )
    }

    override fun searchLectureList(q: String, type: String, idx: Int, size: Int): MiniLectureListResponse {
        val searchList = when (type) {
            "title" -> lectureRepository.findBySearchTitleContaining(q, PageRequest.of(idx, size))?: throw LectureNotFoundException(q)
            "explanation" -> lectureRepository.findBySearchExplanationContaining(q, PageRequest.of(idx, size))?: throw LectureNotFoundException(q)
            else -> throw IncorrectSearchType(type)
        }
        return MiniLectureListResponse(
            searchList.content.map {
                it.toMiniLectureResponse()
            }
        )
    }

    override fun changeLectureThumbnail(lectureId: String, req: FileRequest): PreSignedUrlResponse {
        val lectureEntity = lectureRepository.findByIdOrNull(lectureId)?: throw LectureNotFoundException(lectureId)
        isOwner(lectureEntity.createdBy!!)
        val (preSignedUrl, fileUrl) = preSignedUrl(req.fileName, req.contentType, lectureId)

        lectureEntity.uploadLectureThumbnail(fileUrl)

        return preSignedUrl
    }

    private fun preSignedUrl( fileName: String, contentType: String, lectureId: String): Pair<PreSignedUrlResponse, String>{

        val file = s3Util.getPreSignedUrl(
            fileName,
            contentType,
            "LECTURE/$lectureId",
            "THUMBNAIL"
        )

        val preSignedUrl = PreSignedUrlResponse(
            file.fileUrl,
            file.fileName
        )

        file.removeParameter()

        return Pair(preSignedUrl, file.fileUrl)
    }

    override fun changeLecture(lectureId: String, req: ChangeLectureRequest) {
        val lectureEntity = lectureRepository.findByIdOrNull(lectureId)?: throw LectureNotFoundException(lectureId)
        isOwner(lectureEntity.createdBy!!)
        lectureEntity.changeLecture(req)
    }

    override fun deleteLecture(lectureId: String) {
        val lectureEntity = lectureRepository.findByIdOrNull(lectureId)?: throw LectureNotFoundException(lectureId)
        isOwner(lectureEntity.createdBy!!)
        lectureRepository.delete(lectureEntity)
    }

    override fun addLectureTag(lectureId: String, tagId: String) {
        val lectureEntity = lectureRepository.findByIdOrNull(lectureId)?: throw LectureNotFoundException(lectureId)
        isOwner(lectureEntity.createdBy!!)
        val lectureTagEntity = lectureTagRepository.findByIdOrNull(tagId)?: lectureTagRepository.save(Tag(tagId))
        lectureEntity.tagUsageList.firstOrNull{
            it.tag == lectureTagEntity
        }?: lectureTagUsageRepository.save(
            TagUsage(
                lectureTagEntity,
                lectureEntity,
            )
        )

    }

    override fun deleteLectureTag(lectureId: String, tagId: String) {
        val lectureEntity = lectureRepository.findByIdOrNull(lectureId)?: throw LectureNotFoundException(lectureId)

        isOwner(lectureEntity.createdBy!!)

        val tag = lectureTagRepository.findByIdOrNull(tagId)?: throw LectureTagNotFound(tagId)

        lectureEntity.tagUsageList.firstOrNull{
            it.tag == tag
        }?.let {
            lectureTagUsageRepository.deleteByLectureAndTag(lectureEntity, tag)
        }
    }

    private fun isOwner(createdBy: String){
        val teacher = currentUtil.getCurrentUser()
        if(createdBy != teacher.accountId) throw NoAuthenticationException(teacher.accountId)
    }
}