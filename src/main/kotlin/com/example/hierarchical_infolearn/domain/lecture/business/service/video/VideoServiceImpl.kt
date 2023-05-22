package com.example.hierarchical_infolearn.domain.lecture.business.service.video

import com.example.hierarchical_infolearn.domain.lecture.business.dto.request.video.ChangeVideoChapterRequest
import com.example.hierarchical_infolearn.domain.lecture.business.dto.request.video.ChangeVideoRequest
import com.example.hierarchical_infolearn.domain.lecture.business.dto.request.video.ChangeVideoSequenceRequest
import com.example.hierarchical_infolearn.domain.lecture.business.dto.request.video.CreateVideoRequest
import com.example.hierarchical_infolearn.domain.lecture.business.dto.response.video.VideoMaxResponse
import com.example.hierarchical_infolearn.domain.lecture.data.entity.common.VideoStatusType
import com.example.hierarchical_infolearn.domain.lecture.data.entity.video.Video
import com.example.hierarchical_infolearn.domain.lecture.data.entity.video.status.VideoStatus
import com.example.hierarchical_infolearn.domain.lecture.data.repo.chapter.ChapterRepository
import com.example.hierarchical_infolearn.domain.lecture.data.repo.video.VideoRepository
import com.example.hierarchical_infolearn.domain.lecture.data.repo.video.VideoStatusRepository
import com.example.hierarchical_infolearn.domain.lecture.exception.*
import com.example.hierarchical_infolearn.global.error.common.NoAuthenticationException
import com.example.hierarchical_infolearn.global.file.dto.PreSignedUrlResponse
import com.example.hierarchical_infolearn.global.utils.CurrentUtil
import com.example.hierarchical_infolearn.infra.s3.S3Util
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import javax.transaction.Transactional

@Service
@Transactional
class VideoServiceImpl(
    private val videoRepository: VideoRepository,
    private val chapterRepository: ChapterRepository,
    private val currentUtil: CurrentUtil,
    private val s3Util: S3Util,
    private val videoStatusRepository: VideoStatusRepository,
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


        val (preSignedUrl, fileUrl) = preSignedUrl(req.videoUrl.fileName, req.videoUrl.contentType, chapterEntity.lecture.id ,chapterId, videoEntity.id!!, req.videoUrl.fileSize)

        println("test1 : $fileUrl")
        videoEntity.uploadVideoUrl(fileUrl)
        println("test2 : ${videoEntity.videoUrl}")
       return preSignedUrl

    }

    private fun preSignedUrl(fileName: String, contentType: String, lectureId: String,chapterId: Long, videoId: Long, fileSize: Long): Pair<PreSignedUrlResponse, String>{

        val file = s3Util.getPreSignedUrl(
            fileName,
            contentType,
            fileSize,
            "LECTURE/$lectureId/CHAPTER/$chapterId",
            "VIDEO/$videoId"
        )

        val preSignedUrl = PreSignedUrlResponse(
            file.fileUrl,
            file.fileName
        )

        file.removeParameter()

        return Pair(preSignedUrl, file.fileUrl)
    }

    override fun deleteVideo(videoId: Long) {
        val videoEntity = videoRepository.findByIdOrNull(videoId)?: throw VideoNotFound(videoId.toString())

        isOwner(videoEntity.createdBy!!)

        videoRepository.delete(videoEntity)
    }

    override fun changeVideoSequence(chapterId: Long, req: ChangeVideoSequenceRequest) {
        val chapterEntity = chapterRepository.findByIdOrNull(chapterId)?: throw ChapterNotFoundException(chapterId.toString())
        isOwner(chapterEntity.createdBy!!)

        if(req.videoSequences.size > chapterEntity.videos.size) throw VideoNotFound(req.videoSequences.size.toString())

        val duplicationChecker = req.videoSequences.map {
            it.sequence
        }.toSet()

        if (duplicationChecker.size != req.videoSequences.size) throw DuplicationSequenceException(req.videoSequences.size.toString())

        req.videoSequences.forEach {
            val videoEntity = videoRepository.findByIdAndAndChapter(it.videoId, chapterEntity)?: throw VideoNotFound(it.videoId.toString())
            videoEntity.updateSequence(it.sequence)
        }

    }

    override fun changeVideo(videoId: Long, req: ChangeVideoRequest): PreSignedUrlResponse? {
        val videoEntity = videoRepository.findByIdOrNull(videoId)?: throw VideoNotFound(videoId.toString())
        isOwner(videoEntity.createdBy!!)
        videoEntity.changeVideo(req)
        req.videoUrl?.let {
            val (preSignedUrl, fileUrl) = preSignedUrl(it.fileName, it.contentType, videoEntity.chapter.lecture.id ,videoEntity.chapter.id!!, videoEntity.id!!, it.fileSize)
            videoEntity.uploadVideoUrl(fileUrl)
            return preSignedUrl
        }
        return null
    }

    override fun changeVideoChapter(videoId: Long, req: ChangeVideoChapterRequest) {
        val chapterEntity = chapterRepository.findByIdOrNull(req.chapterId)?: throw ChapterNotFoundException(req.chapterId.toString())
        isOwner(chapterEntity.createdBy!!)
        val videoEntity = videoRepository.findByIdOrNull(videoId)?: throw VideoNotFound(videoId.toString())
        val targetChapterEntity = chapterRepository.findByIdOrNull(req.targetChapterId)?: throw ChapterNotFoundException(req.targetChapterId.toString())
        val lastSequence = targetChapterEntity.videos.filter {
            !it.isDeleted
        }.size
        videoEntity.changeChapter(targetChapterEntity)
        videoEntity.updateSequence(lastSequence + 1)
    }

    override fun getVideo(videoId: Long): VideoMaxResponse {
        val videoEntity = videoRepository.findByIdOrNull(videoId)?: throw VideoNotFound(videoId.toString())
        return videoEntity.toVideoUrlResponse(currentUtil.getCurrentUser().accountId)
    }


    override fun videoCompleted(videoId: Long) {
        val videoEntity = videoRepository.findByIdOrNull(videoId)?: throw VideoNotFound(videoId.toString())

        if(videoStatusRepository.existsByVideoAndCreatedBy(videoEntity,currentUtil.getCurrentUser().accountId)) throw AlreadyCompletedVideoException(videoEntity.id.toString())

        val videoStatus = VideoStatus(
            videoEntity,
            VideoStatusType.COMPLETE,
        )
        videoStatusRepository.save(videoStatus)
    }

    private fun isOwner(createdBy: String){
        val teacher = currentUtil.getCurrentUser()
        if(createdBy != teacher.accountId) throw NoAuthenticationException(teacher.accountId)
    }
}