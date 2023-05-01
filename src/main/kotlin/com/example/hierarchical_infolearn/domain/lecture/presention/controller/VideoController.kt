package com.example.hierarchical_infolearn.domain.lecture.presention.controller

import com.example.hierarchical_infolearn.domain.lecture.business.dto.request.video.ChangeVideoRequest
import com.example.hierarchical_infolearn.domain.lecture.business.dto.request.video.ChangeVideoSequenceRequest
import com.example.hierarchical_infolearn.domain.lecture.business.dto.request.video.CreateVideoRequest
import com.example.hierarchical_infolearn.domain.lecture.business.service.video.VideoService
import com.example.hierarchical_infolearn.global.error.data.ErrorResponse
import com.example.hierarchical_infolearn.global.file.dto.PreSignedUrlResponse
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import org.springframework.http.HttpStatus
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping("/api/infolearn/v1/video")
@Validated
class VideoController(
    private val videoService: VideoService,
) {

    @PostMapping("/{chapter-id}")
    @Operation(summary = "영상를 등록", description = "영상을 등록합니다",
        responses = [
            ApiResponse(responseCode = "201", description = "영상이 성공적으로 등록돰", content = [Content(schema = Schema(implementation = PreSignedUrlResponse::class))]),
            ApiResponse(responseCode = "400", description = "이미 사용중인 sequence", content = [Content(schema = Schema(implementation = ErrorResponse::class))]),
            ApiResponse(responseCode = "403", description = "역할은 교사이지만 작성자가 아님", content = [Content(schema = Schema(implementation = ErrorResponse::class))]),
            ApiResponse(responseCode = "404", description = "1. chapterId에 일치하는 강의를 찾을 수 없음\n 2. 유저를 찾을 수 없음", content = [Content(schema = Schema(implementation = ErrorResponse::class))])
        ]
        )
    @ResponseStatus(HttpStatus.CREATED)
    fun createVideo(
        @PathVariable("chapter-id") chapterId: Long,
        @RequestBody
        @Valid
        request: CreateVideoRequest
    ): PreSignedUrlResponse {
        return videoService.createVideo(
            chapterId = chapterId,
            req = request,
        )
    }

    @DeleteMapping("/{chapter-id}")
    @Operation( summary = "영상을 삭제", description = "영상을 삭제합니다",
        responses = [
            ApiResponse(responseCode = "204", description = "챕터가 성공적으로 삭제됨"),
            ApiResponse(responseCode = "403", description = "역할은 교사이지만 작성자가 아님", content = [Content(schema = Schema(implementation = ErrorResponse::class))]),
            ApiResponse(responseCode = "404", description = "1. chapterId에 일치하는 챕터를 찾을 수 없음\n 2. 유저를 찾을 수 없음 \n 3. videoId에 일치하는 영상을 찾을 수 없음", content = [Content(schema = Schema(implementation = ErrorResponse::class))])
        ]
    )
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deleteVideo(
        @PathVariable("chapter-id") chapterId: Long,
        @RequestParam(required = true) videoId: Long,
    ) {
        videoService.deleteVideo(
            chapterId = chapterId,
            videoId = videoId
        )
    }

    @PutMapping("/{chapter-id}/sequence")
    @Operation(summary = "영상 순서 변경", description = "해당 영상의 순서를 변경합니다",
        responses = [
            ApiResponse(responseCode = "200", description = "영상 순서가 성공적으로 변경됨"),
            ApiResponse(responseCode = "403", description = "역할은 교사이지만 작성자가 아님", content = [Content(schema = Schema(implementation = ErrorResponse::class))]),
            ApiResponse(responseCode = "404", description = "1. chapterId에 일치하는 챕터를 찾을 수 없음 \n 2. videoId에 일치하는 영상를 찾을 수 없음", content = [Content(schema = Schema(implementation = ErrorResponse::class))])
        ]
    )
    fun changeSequence(
        @PathVariable("chapter-id") chapterId: Long,
        @Valid
        @RequestBody
        request: ChangeVideoSequenceRequest
    ) {
        videoService.changeVideoSequence(
            chapterId = chapterId,
            req = request
        )
    }

    @PutMapping("/{chapter-id}")
    @Operation(summary = "영상 내용 변경", description = "영상의 제목 또는 재생 시간을 변경합니다",
        responses = [
            ApiResponse(responseCode = "200", description = "영상의 제목 또는 재생시간이 성공적으로 변경됨"),
            ApiResponse(responseCode = "403", description = "역할은 교사이지만 작성자가 아님", content = [Content(schema = Schema(implementation = ErrorResponse::class))]),
            ApiResponse(responseCode = "404", description = "1. chapterId에 일치하는 챕터를 찾을 수 없음 \n 2. videoId에 일치하는 영상를 찾을 수 없음", content = [Content(schema = Schema(implementation = ErrorResponse::class))])
        ]
        )
    fun changeVideo(
        @PathVariable("chapter-id") chapterId: Long,
        @Valid
        @RequestBody
        request: ChangeVideoRequest
    ){
        videoService.changeVideo(
            chapterId = chapterId,
            req = request,
        )
    }
}