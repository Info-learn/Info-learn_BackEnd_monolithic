package com.example.hierarchical_infolearn.domain.til.business.service.til

import com.example.hierarchical_infolearn.domain.til.business.dto.request.CreateTilRequest
import com.example.hierarchical_infolearn.domain.til.business.dto.response.TilContentImageResponse
import com.example.hierarchical_infolearn.domain.til.data.entity.Til
import com.example.hierarchical_infolearn.domain.til.data.entity.content.Content
import com.example.hierarchical_infolearn.domain.til.data.entity.tag.Tag
import com.example.hierarchical_infolearn.domain.til.data.entity.tag.TagUsage
import com.example.hierarchical_infolearn.domain.til.data.repo.TilContentRepository
import com.example.hierarchical_infolearn.domain.til.data.repo.TilRepository
import com.example.hierarchical_infolearn.domain.til.data.repo.TilTagRepository
import com.example.hierarchical_infolearn.domain.til.data.repo.TilTagUsageRepository
import com.example.hierarchical_infolearn.global.file.dto.ImageFileRequest
import com.example.hierarchical_infolearn.global.file.dto.PreSignedUrlResponse
import com.example.hierarchical_infolearn.global.utils.CurrentUtil
import com.example.hierarchical_infolearn.infra.s3.S3Util
import org.bson.types.ObjectId
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import java.util.*

@Service
class TilServiceImpl(
    private val tilRepository: TilRepository,
    private val tilContentRepository: TilContentRepository,
    private val currentUtil: CurrentUtil,
    private val s3Util: S3Util,
    private val tilTagRepository: TilTagRepository,
    private val tilTagUsageRepository: TilTagUsageRepository,
): TilService {


    companion object {
        const val THUMBNAIL = "THUMBNAIL"
        const val IMAGE = "IMAGE"
    }


    override fun createTil(req: CreateTilRequest): PreSignedUrlResponse {
        val tilId = UUID.randomUUID().toString()
        val tilContentId = ObjectId.get()
        val tilEntity = tilRepository.save(
            Til(
                id = tilId,
                title = req.title,
                searchTitle = req.searchTitle,
                subTitle = req.subTitle,
                isPrivate = req.isPrivate,
                tilContent = tilContentId,
                user = currentUtil.getCurrentUser()
            )
        )

        tilContentRepository.save(
            Content(
                tilContentId,
                tilId,
                req.content
            )
        )
        req.tagNameList.forEach {

            val tagEntity = tilTagRepository.findByIdOrNull(it)?: tilTagRepository.save(
                Tag(
                    it
                )
            )

            tilTagUsageRepository.save(
                TagUsage(
                    tagEntity,
                    tilEntity
                )
            )

            tagEntity.increaseUsageCount()
        }

        val (preSignedUrl, fileUrl) = preSignedUrl(req.tilThumbnail.fileName, req.tilThumbnail.contentType, tilId, THUMBNAIL)

        tilEntity.uploadTilThumbnail(fileUrl)

        return preSignedUrl
    }

    override fun createImage(req: ImageFileRequest): TilContentImageResponse {
        val (preSignedUrl, fileUrl) = preSignedUrl(
            fileName = req.fileName,
            contentType = req.contentType,
            type = IMAGE,
            )

        return TilContentImageResponse(
            fileUrl = fileUrl,
            preSignedUrl = preSignedUrl
        )
    }

    private fun preSignedUrl(fileName: String, contentType: String, tilId: String? = null, type: String): Pair<PreSignedUrlResponse, String>{

        val file = s3Util.getPreSignedUrl(
            fileName,
            contentType,
            tilId?.let {
                "TIL/$tilId"
            }?: UUID.randomUUID().toString(),
            type,
        )

        val preSignedUrl = PreSignedUrlResponse(
            file.fileUrl,
            file.fileName
        )

        file.removeParameter()

        return Pair(preSignedUrl, file.fileUrl)
    }

}