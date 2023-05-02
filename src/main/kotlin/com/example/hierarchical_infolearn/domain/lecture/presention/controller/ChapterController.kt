package com.example.hierarchical_infolearn.domain.lecture.presention.controller

import com.example.hierarchical_infolearn.domain.lecture.business.dto.request.chapter.ChangeChapterRequest
import com.example.hierarchical_infolearn.domain.lecture.business.dto.request.chapter.ChangeChapterSequenceRequest
import com.example.hierarchical_infolearn.domain.lecture.business.dto.request.chapter.CreateChapterRequest
import com.example.hierarchical_infolearn.domain.lecture.business.service.chapter.ChapterService
import com.example.hierarchical_infolearn.global.error.data.ErrorResponse
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import org.springframework.http.HttpStatus
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import java.util.*
import javax.validation.Valid


@RestController
@RequestMapping("/api/infolearn/v1/chapter")
@Validated
class ChapterController(
    private val chapterService: ChapterService,
) {

    @PostMapping("")
    @Operation(summary = "챕터 생성하기", description = "해당 강의의 챕터를 생성합니다",
        responses = [
            ApiResponse(responseCode = "201", description = "챕터가 성공적으로 생성돰"),ApiResponse(responseCode = "403", description = "역할은 교사이지만 작성자가 아님", content = [Content(schema = Schema(implementation = ErrorResponse::class))]),
            ApiResponse(responseCode = "400", description = "이미 사용중인 sequence", content = [Content(schema = Schema(implementation = ErrorResponse::class))]),
            ApiResponse(responseCode = "404", description = "1. lectureId에 일치하는 강의를 찾을 수 없음 \n 2. 유저를 찾을 수 없음", content = [Content(schema = Schema(implementation = ErrorResponse::class))]),
        ])
    @ResponseStatus(HttpStatus.CREATED)
    fun createChapter(
        @RequestBody
        @Valid
        request: CreateChapterRequest
    ){
        chapterService.createChapter(
            req = request,
        )
    }

    @DeleteMapping("/{lecture-id}")
    @Operation(summary = "챕터 삭제하기", description = "해당 강의의 챕터를 삭제합니다",
        responses = [
            ApiResponse(responseCode = "204", description = "챕터가 성공적으로 삭제됨"),
            ApiResponse(responseCode = "403", description = "역할은 교사이지만 작성자가 아님", content = [Content(schema = Schema(implementation = ErrorResponse::class))]),
            ApiResponse(responseCode = "404", description = "1. lectureId에 일치하는 강의를 찾을 수 없음 \n 2. chapterId에 일치하는 챕터를 찾을 수 없음", content = [Content(schema = Schema(implementation = ErrorResponse::class))])
        ]
        )
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deleteChapter(
        @PathVariable("lecture-id") lectureId: String,
        @RequestParam(required = true) chapterId: Long,
    ) {
        chapterService.deleteChapter(
            lectureId = lectureId,
            chapterId = chapterId
        )
    }

    @PutMapping("/{lecture-id}/sequence")
    @Operation(summary = "챕터 순서 변경", description = "해당 강의의 챕터 순서를 변경합니다",
        responses = [
            ApiResponse(responseCode = "200", description = "챕터 순서가 성공적으로 변경됨"),
            ApiResponse(responseCode = "403", description = "역할은 교사이지만 작성자가 아님", content = [Content(schema = Schema(implementation = ErrorResponse::class))]),
            ApiResponse(responseCode = "404", description = "1. lectureId에 일치하는 강의를 찾을 수 없음 \n 2. chapterId에 일치하는 챕터를 찾을 수 없음", content = [Content(schema = Schema(implementation = ErrorResponse::class))])
        ]
        )
    fun changeSequence(
        @PathVariable("lecture-id") lectureId: String,
        @RequestBody
        request: ChangeChapterSequenceRequest
    ) {
        chapterService.changeChapterSequence(
            lectureId = lectureId,
            req = request
        )
    }

    @PutMapping("/{chapter-id}")
    @Operation(
        summary = "챕터 변경" , description = "챕터의 이름을 변경합니다",
        responses = [
            ApiResponse(responseCode = "200", description = "챕터의 이름이 성공적으로 변경됨"),
            ApiResponse(responseCode = "403", description = "역할은 교사이지만 작성자가 아님", content = [Content(schema = Schema(implementation = ErrorResponse::class))]),
            ApiResponse(responseCode = "404", description = "1. chapterId에 일치하는 챕터를 찾을 수 없음", content = [Content(schema = Schema(implementation = ErrorResponse::class))])
        ]
    )
    fun changeChapter(
        @PathVariable("chapter-id") chapterId: Long,
        @Valid
        @RequestBody
        request: ChangeChapterRequest,
    ){
        chapterService.changeChapter(
            chapterId = chapterId,
            req = request,
        )
    }


}