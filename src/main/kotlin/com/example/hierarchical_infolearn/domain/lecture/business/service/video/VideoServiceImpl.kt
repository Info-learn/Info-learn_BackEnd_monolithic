package com.example.hierarchical_infolearn.domain.lecture.business.service.video

import com.example.hierarchical_infolearn.domain.lecture.business.dto.request.ChangeVideoSequenceRequest
import com.example.hierarchical_infolearn.domain.lecture.business.dto.request.CreateVideoRequest
import com.example.hierarchical_infolearn.domain.lecture.data.entity.video.Video
import com.example.hierarchical_infolearn.domain.lecture.data.repo.chapter.ChapterRepository
import com.example.hierarchical_infolearn.domain.lecture.data.repo.video.VideoRepository
import com.example.hierarchical_infolearn.domain.lecture.exception.AlreadyUsingSequence
import com.example.hierarchical_infolearn.domain.lecture.exception.ChapterNotFoundException
import com.example.hierarchical_infolearn.domain.lecture.exception.IncorrectVideo
import com.example.hierarchical_infolearn.domain.lecture.exception.VideoNotFound
import com.example.hierarchical_infolearn.global.error.common.NoAuthenticationException
import com.example.hierarchical_infolearn.global.file.dto.PreSignedUrlResponse
import com.example.hierarchical_infolearn.global.utils.CurrentUtil
import com.example.hierarchical_infolearn.infra.s3.S3Util
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class VideoServiceImpl(
    private val videoRepository: VideoRepository,
    private val chapterRepository: ChapterRepository,
    private val currentUtil: CurrentUtil,
    private val s3Util: S3Util,
    ): VideoService {

    override fun createVideo(chapterId: Long, req: CreateVideoRequest):PreSignedUrlResponse {
        val chapterEntity = chapterRepository.findByIdOrNull(chapterId)?: throw ChapterNotFoundException(chapterId.toString())
        isOwner(chapterEntity.createdBy!!)

        chapterEntity.videos.firstOrNull{
            !it.isDeleted && it.sequence == req.sequence
        }?.let {
            throw AlreadyUsingSequence(req.sequence.toString())
        }

        val videoEntity = videoRepository.save(
            Video(
                req.title,
                req.playTime,
                req.sequence,
                chapterEntity,
            )
        )


        val (preSignedUrl, fileUrl) = preSignedUrl(req.videoUrl.fileName, req.videoUrl.contentType, chapterId, videoEntity.id!!)

        videoEntity.uploadVideoUrl(fileUrl)

       return preSignedUrl

    }

    private fun preSignedUrl(fileName: String, contentType: String, chapterId: Long, videoId: Long): Pair<PreSignedUrlResponse, String>{

        val file = s3Util.getPreSignedUrl(
            fileName,
            contentType,
            "CHAPTER/$chapterId",
            "VIDEO/$videoId"
        )

        val preSignedUrl = PreSignedUrlResponse(
            file.fileUrl,
            file.fileName
        )

        file.removeParameter()

        return Pair(preSignedUrl, file.fileUrl)
    }

    override fun deleteVideo(chapterId: Long, videoId: Long) {
        val videoEntity = videoRepository.findByIdOrNull(videoId)?: throw VideoNotFound(videoId.toString())

        isOwner(videoEntity.createdBy!!)

        val chapterEntity = chapterRepository.findByIdOrNull(chapterId)?: throw ChapterNotFoundException(chapterId.toString())

        chapterEntity.videos.firstOrNull{
            !it.isDeleted && it == videoEntity
        }?.let {
            videoRepository.delete(videoEntity)
        }
    }

    override fun changeVideoSequence(chapterId: Long, req: ChangeVideoSequenceRequest) {
        val chapterEntity = chapterRepository.findByIdOrNull(chapterId)?: throw ChapterNotFoundException(chapterId.toString())
        isOwner(chapterEntity.createdBy!!)
        val videoEntity = videoRepository.findByIdOrNull(req.videoId)?: throw VideoNotFound(req.videoId.toString())

        chapterEntity.videos.firstOrNull{
            !it.isDeleted && it == videoEntity
        }?: IncorrectVideo(req.videoId.toString())

        val targetVideoEntity = videoRepository.findBySequenceAndChapter(req.sequence, chapterEntity)?: throw VideoNotFound(req.sequence.toString())

        val sequence = videoEntity.sequence
        videoEntity.updateSequence(req.sequence)
        targetVideoEntity.updateSequence(sequence)
    }

    private fun isOwner(createdBy: String){
        val teacher = currentUtil.getCurrentUser()
        if(createdBy != teacher.accountId) throw NoAuthenticationException(teacher.accountId)
    }
}