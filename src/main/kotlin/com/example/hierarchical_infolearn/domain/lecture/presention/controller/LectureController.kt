package com.example.hierarchical_infolearn.domain.lecture.presention.controller

import com.example.hierarchical_infolearn.domain.lecture.business.dto.request.lecture.ChangeLectureRequest
import com.example.hierarchical_infolearn.domain.lecture.business.dto.request.lecture.CreateLectureRequest
import com.example.hierarchical_infolearn.domain.lecture.business.dto.request.lecture.LectureTagRequest
import com.example.hierarchical_infolearn.domain.lecture.business.dto.response.lecture.LectureIdResponse
import com.example.hierarchical_infolearn.domain.lecture.business.dto.response.lecture.LectureSearchResponse
import com.example.hierarchical_infolearn.domain.lecture.business.dto.response.lecture.MaxLectureResponse
import com.example.hierarchical_infolearn.domain.lecture.business.dto.response.lecture.MiniLectureListResponse
import com.example.hierarchical_infolearn.domain.lecture.business.dto.response.tag.TagNameListResponse
import com.example.hierarchical_infolearn.domain.lecture.business.service.lecture.LectureService
import com.example.hierarchical_infolearn.global.annotation.ListSizeLimit
import com.example.hierarchical_infolearn.global.error.data.ErrorResponse
import com.example.hierarchical_infolearn.global.file.dto.ImageFileRequest
import com.example.hierarchical_infolearn.global.file.dto.PreSignedUrlResponse
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import org.hibernate.validator.constraints.UniqueElements
import org.springframework.http.HttpStatus
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import java.time.LocalDateTime
import java.util.*
import javax.validation.Valid
import javax.validation.constraints.Pattern

@RestController
@RequestMapping("/api/infolearn/v1/lecture")
@Validated
class LectureController(
    private val lectureService: LectureService,
) {
    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "강의 등록", description = "제목과 설명, 강의 섬네일, 태그 등을 기입한 후 강의를 등록합니다",
        responses = [
            ApiResponse(responseCode = "201", description = "강의가 성공적으로 등록됨", content = [Content(schema = Schema(implementation = LectureIdResponse::class))]),
            ApiResponse(responseCode = "400", description = "1. 제목은 1 ~ 100자이여야함 \n 2. search_title은 1 ~ 100자이여야함 \n 3. 설명은 1 ~ 100자이여야함",content = [Content(schema = Schema(implementation = ErrorResponse::class))]),
        ]
        )
    fun createLecture(
        @Valid
        @RequestBody(required = true) request: CreateLectureRequest,
    ): LectureIdResponse {
        return lectureService.createLecture(
            req = request,
        )
    }
    @GetMapping("")
    @Operation(summary = "강의 리스트 불러오기", description = "list의 마지막에 존재하는 강의의 createdAt을 입력받아 다음 강의 리스트를 불러옵니다.\n 만약 불러온 list가 없다면 time 파라미터를 넣지 않으면 됩니다",
        responses = [
            ApiResponse(responseCode = "200", description = "강의 리스트가 성공적으로 불러와짐", content = [Content(schema = Schema(implementation = MiniLectureListResponse::class))]),
            ApiResponse(responseCode = "404", description = "강의를 찾을 수 없음", content = [Content(schema = Schema(
                implementation = ErrorResponse::class))]),
        ]
    )
    fun getLectureList(
        @RequestParam(required = false) time: LocalDateTime?,
        @RequestParam(required = true) limit: Long,
    ): MiniLectureListResponse {
        return lectureService.getLectureList(time, limit)
    }

    @GetMapping("/{lecture-id}")
    @Operation(summary = "강의를 불러오기", description = "해당 lectureId에 해당하는 강의 정보를 불러옵니다",
        responses = [
            ApiResponse(responseCode = "200", description = "강의가 성공적으로 불러와짐", content = [Content(schema = Schema(implementation = MaxLectureResponse::class))]),
            ApiResponse(responseCode = "404", description = "lectureId에 일치하는 강의를 찾을 수 없음", content = [Content(schema = Schema(implementation = ErrorResponse::class))]),
        ]
    )
    fun getLecture(
        @PathVariable("lecture-id") lectureId: String,
    ): MaxLectureResponse {
        return lectureService.getLecture(lectureId)
    }


    @GetMapping("/search")
    @Operation(summary = "강의를 검색합니다", description = "강의 제목과 설명을 기반으로 검색합니다",
        responses = [
            ApiResponse(responseCode = "200", description = "성공적으로 검색함" , content = [Content(schema = Schema(implementation = LectureSearchResponse::class))]),
            ApiResponse(responseCode = "404", description = "lectureId에 일치하는 강의를 찾을 수 없음", content = [Content(schema = Schema(implementation = ErrorResponse::class))]),
        ]
        )
    fun searchLecture(
        @Pattern(regexp = "^[a-zA-Z0-9!@#\$%^&*()_+\\-=\\[\\]{}|;':\",./<>?\\s]{1,200}\$", message = "query는 영문자이며 1 ~ 200자이여야 합니다")
        @RequestParam(required = true) q: String,
        @RequestParam(required = false, defaultValue = "0") idx: Int,
        @RequestParam(required = false, defaultValue = "10") size: Int,
    ): LectureSearchResponse {
        return lectureService.searchLectureList(q, idx, size)
    }

    @PutMapping("/{lecture-id}/thumbnail")
    @Operation(summary = "강의 썸네일 변경", description = "해당 강의의 섬네일을 변경합니다",
        responses = [
            ApiResponse(responseCode = "200", description = "성공적으로 변경함", content = [Content(schema = Schema(implementation = PreSignedUrlResponse::class))]),ApiResponse(responseCode = "403", description = "역할은 교사이지만 작성자가 아님", content = [Content(schema = Schema(implementation = ErrorResponse::class))]),
            ApiResponse(responseCode = "404", description = "lectureId에 일치하는 강의를 찾을 수 없음 \n 2. 유저를 찾을 수 없음", content = [Content(schema = Schema(implementation = ErrorResponse::class))]),
        ])
    fun changThumbnail(
        @PathVariable("lecture-id") lectureId: String,
        @Valid
        @RequestBody
        request: ImageFileRequest
    ):PreSignedUrlResponse {
        return lectureService.changeLectureThumbnail(
            lectureId = lectureId,
            req = request,
        )
    }

    @PutMapping("/{lecture-id}")
    @Operation(summary = "강의 제목과 설명 변경", description = "강의의 제목 또는 설명을 변경합니다",
        responses = [
            ApiResponse(responseCode = "200", description = "성공적으로 변경함", content = [Content(schema = Schema(implementation = PreSignedUrlResponse::class))]),ApiResponse(responseCode = "403", description = "역할은 교사이지만 작성자가 아님", content = [Content(schema = Schema(implementation = ErrorResponse::class))]),
            ApiResponse(responseCode = "404", description = "lectureId에 일치하는 강의를 찾을 수 없음 \n 2. 유저를 찾을 수 없음", content = [Content(schema = Schema(implementation = ErrorResponse::class))]),
        ])
    fun changeLecture(
        @PathVariable("lecture-id") lectureId: String,
        @Valid
        @RequestBody
        request: ChangeLectureRequest
    ) {
        lectureService.changeLecture(
            lectureId = lectureId,
            req = request,
            )
    }

    @PutMapping("/{lecture-id}/tag")
    @Operation(summary = "강의 태그 추가", description = "해당 강의에 태그를 추가합니다",
        responses = [
        ApiResponse(responseCode = "200", description = "태그가 성공적으로 추가됨"),
        ApiResponse(responseCode = "403", description = "역할은 교사이지만 작성자가 아님", content = [Content(schema = Schema(implementation = ErrorResponse::class))]),
        ApiResponse(responseCode = "404", description = "1. lectureId에 일치하는 강의를 찾을 수 없음 \n 2. tagId에 해당하는 lectureTag를 찾을 수 없음", content = [Content(schema = Schema(implementation = ErrorResponse::class))]),
    ])
    fun addLectureTag(
        @PathVariable("lecture-id") lectureId: String,
        @Valid
        @RequestBody
        @UniqueElements
        @ListSizeLimit(message = "태그 갯수는 10개 이하이여야 합니다",max = 10)
        request: List<LectureTagRequest>
    ){
      lectureService.addLectureTag(lectureId, request)
    }

    @DeleteMapping("/{lecture-id}/tag")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "강의 태그 삭제", description = "해당 강의에 태그를 삭제합니다",
        responses = [
            ApiResponse(responseCode = "204", description = "태그가 성공적으로 삭제됨"),
            ApiResponse(responseCode = "403", description = "역할은 교사이지만 작성자가 아님", content = [Content(schema = Schema(implementation = ErrorResponse::class))]),
            ApiResponse(responseCode = "404", description = "1. lectureId에 일치하는 강의를 찾을 수 없음 \n 2. tagId에 해당하는 lectureTag를 찾을 수 없음", content = [Content(schema = Schema(implementation = ErrorResponse::class))]),
        ]
        )
    fun deleteLectureTag(
        @PathVariable("lecture-id") lectureId: String,
        @Valid
        @RequestBody
        @UniqueElements
        @ListSizeLimit(max = 10)
        request: List<LectureTagRequest>
    ){
        lectureService.deleteLectureTag(lectureId, request)
    }

    @DeleteMapping("/{lecture-id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "강의 삭제", description = "lectureId에 해당하는 강의를 삭제합니다",
        responses = [
            ApiResponse(responseCode = "204", description = "성공적으로 강의를 삭제함"),ApiResponse(responseCode = "403", description = "역할은 교사이지만 작성자가 아님", content = [Content(schema = Schema(implementation = ErrorResponse::class))]),
            ApiResponse(responseCode = "404", description = "lectureId에 일치하는 강의를 찾을 수 없음 \n 2. 유저를 찾을 수 없음", content = [Content(schema = Schema(implementation = ErrorResponse::class))]),
        ]
        )
    fun deleteLecture(
        @PathVariable("lecture-id") lectureId: String,
    ){
      lectureService.deleteLecture(lectureId)
    }

    @GetMapping("/tag")
    @Operation(
        summary = "전체 태그 불러오기", description = "list의 마지막에 존재하는 태그의 tagUsage을 입력받아 사용이 많이 된 순으로 태그를 불러옵니다",
        responses = [
            ApiResponse(responseCode = "200", description = "태그를 성공적으로 불러옴", content = [Content(schema = Schema(implementation = TagNameListResponse::class))]),
            ApiResponse(responseCode = "404", description = "태그를 찾을 수 없음", content = [Content(schema = Schema(implementation = ErrorResponse::class))])
        ]
    )
    fun getLectureTag(
        @RequestParam(required = false) usageCount: Long?,
        @RequestParam(required = true) limit: Long,
    ): TagNameListResponse {
        return lectureService.getLectureTag(
            usageCount = usageCount,
            limit = limit
        )
    }


}