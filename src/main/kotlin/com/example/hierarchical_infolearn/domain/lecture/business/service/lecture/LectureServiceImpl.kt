package com.example.hierarchical_infolearn.domain.lecture.business.service.lecture

import com.example.hierarchical_infolearn.domain.lecture.business.dto.request.lecture.ChangeLectureRequest
import com.example.hierarchical_infolearn.domain.lecture.business.dto.request.lecture.CreateLectureRequest
import com.example.hierarchical_infolearn.domain.lecture.business.dto.request.lecture.LectureTagRequest
import com.example.hierarchical_infolearn.domain.lecture.business.dto.response.lecture.LectureIdResponse
import com.example.hierarchical_infolearn.domain.lecture.business.dto.response.lecture.LectureSearchResponse
import com.example.hierarchical_infolearn.domain.lecture.business.dto.response.lecture.MaxLectureResponse
import com.example.hierarchical_infolearn.domain.lecture.business.dto.response.lecture.MiniLectureListResponse
import com.example.hierarchical_infolearn.domain.lecture.business.dto.response.tag.TagNameListResponse
import com.example.hierarchical_infolearn.domain.lecture.data.entity.Lecture
import com.example.hierarchical_infolearn.domain.lecture.data.entity.tag.Tag
import com.example.hierarchical_infolearn.domain.lecture.data.entity.tag.TagUsage
import com.example.hierarchical_infolearn.domain.lecture.data.repo.lecture.LectureRepository
import com.example.hierarchical_infolearn.domain.lecture.data.repo.tag.LectureTagRepository
import com.example.hierarchical_infolearn.domain.lecture.data.repo.tag.LectureTagUsageRepository
import com.example.hierarchical_infolearn.domain.lecture.exception.LectureNotFoundException
import com.example.hierarchical_infolearn.domain.lecture.exception.LectureTagNotFound
import com.example.hierarchical_infolearn.domain.lecture.exception.TooManyLectureTag
import com.example.hierarchical_infolearn.global.error.common.NoAuthenticationException
import com.example.hierarchical_infolearn.global.file.dto.ImageFileRequest
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

        val tagId = UUID.randomUUID().toString()
        val lectureEntity = lectureRepository.save(
            Lecture(
                id = tagId,
                title = req.title,
                explanation = req.explanation,
                searchTitle = req.searchTitle,
                searchExplanation = req.searchExplanation,
            )
        )

        val (preSignedUrl, fileUrl) = preSignedUrl(req.lectureThumbnail.fileName, req.lectureThumbnail.contentType,
            lectureEntity.id, req.lectureThumbnail.fileSize
        )

        lectureEntity.uploadLectureThumbnail(fileUrl)

        req.tagNameList.forEach {

            val tagEntity = lectureTagRepository.findByIdOrNull(it) ?: lectureTagRepository.save(
                Tag(
                    it
                )
            )

            lectureTagUsageRepository.save(
                TagUsage(
                    tagEntity,
                    lectureEntity,
                )
            )
            tagEntity.increaseUsageCount()
        }

        return LectureIdResponse(tagId, preSignedUrl)
    }


    override fun getLecture(lectureId: String): MaxLectureResponse {
        val lectureEntity = lectureRepository.findByIdOrNull(lectureId)?: throw LectureNotFoundException
        return lectureEntity.toLectureDetailResponse(currentUtil.getCurrentUser().accountId)
    }

    override fun getLectureList(time: LocalDateTime?, limit: Long, tag: String?): MiniLectureListResponse {
        val lectureEntityList = lectureRepository.queryAllLectureNoOffset(time, limit,tag)
        lectureEntityList?: throw LectureNotFoundException

        return MiniLectureListResponse(
            lectureEntityList.map {
                it.toMiniLectureResponse()
            }
        )
    }

    override fun searchLectureList(q: String, idx: Int, size: Int): LectureSearchResponse {

        val titleResultEntities = lectureRepository.findBySearchTitleContaining(q, PageRequest.of(idx, size))
            ?: throw LectureNotFoundException

        val explanationResultEntities = lectureRepository.findBySearchExplanationContaining(q, PageRequest.of(idx, size))
            ?: throw LectureNotFoundException

        return LectureSearchResponse(
            titleResults = titleResultEntities.map {
                    it.toMiniLectureResponse()
                },
            explanationResults = explanationResultEntities.map {
                it.toMiniLectureResponse()
            }
        )
    }

    override fun changeLectureThumbnail(lectureId: String, req: ImageFileRequest): PreSignedUrlResponse {
        val lectureEntity = lectureRepository.findByIdOrNull(lectureId)?: throw LectureNotFoundException
        isOwner(lectureEntity.createdBy!!)
        val (preSignedUrl, fileUrl) = preSignedUrl(req.fileName, req.contentType, lectureId, req.fileSize)

        lectureEntity.uploadLectureThumbnail(fileUrl)

        return preSignedUrl
    }

    private fun preSignedUrl( fileName: String, contentType: String, lectureId: String, fileSize: Long): Pair<PreSignedUrlResponse, String>{

        val file = s3Util.getPreSignedUrl(
            fileName,
            contentType,
            fileSize,
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
        val lectureEntity = lectureRepository.findByIdOrNull(lectureId)?: throw LectureNotFoundException
        isOwner(lectureEntity.createdBy!!)
        lectureEntity.changeLecture(req)
    }

    override fun deleteLecture(lectureId: String) {
        val lectureEntity = lectureRepository.findByIdOrNull(lectureId)?: throw LectureNotFoundException
        isOwner(lectureEntity.createdBy!!)
        lectureRepository.delete(lectureEntity)
    }

    override fun addLectureTag(lectureId: String, req: List<LectureTagRequest>) {
        val lectureEntity = lectureRepository.findByIdOrNull(lectureId)?: throw LectureNotFoundException
        isOwner(lectureEntity.createdBy!!)

        val tagCount = lectureEntity.tagUsageList.size + req.size

        if(tagCount > 10) throw TooManyLectureTag

        req.forEach {
            val lectureTagEntity = lectureTagRepository.findByIdOrNull(it.tagId)?: lectureTagRepository.save(Tag(it.tagId))
            if (!lectureTagUsageRepository.existsByLectureAndTag(lectureEntity, lectureTagEntity)) {
                lectureTagUsageRepository.save(
                    TagUsage(
                        lectureTagEntity,
                        lectureEntity,
                    )
                )
                lectureTagEntity.increaseUsageCount()
            }
        }
    }

    override fun deleteLectureTag(lectureId: String, req: List<LectureTagRequest>) {
        val lectureEntity = lectureRepository.findByIdOrNull(lectureId)?: throw LectureNotFoundException

        isOwner(lectureEntity.createdBy!!)

        req.forEach {
            val lectureTagEntity = lectureTagRepository.findByIdOrNull(it.tagId)?: throw LectureTagNotFound

            lectureEntity.tagUsageList.firstOrNull{ it1 ->
                it1.tag == lectureTagEntity
            }?.let {
                lectureTagUsageRepository.deleteByLectureAndTag(lectureEntity, lectureTagEntity)
                lectureTagEntity.decreaseUsageCount()
            }
        }
    }

    override fun getLectureTag(usageCount: Long?, limit: Long): TagNameListResponse {
        val lectureTagEntities = lectureTagRepository.queryAllLectureTagNoOffset(usageCount, limit)?: throw LectureTagNotFound

        return TagNameListResponse(lectureTagEntities.map {
            it.toTagResponse()
        })
    }

    private fun isOwner(createdBy: String){
        val teacher = currentUtil.getCurrentUser()
        if(createdBy != teacher.accountId) throw NoAuthenticationException
    }
}