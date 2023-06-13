package com.example.hierarchical_infolearn.domain.til.presention.controller

import com.example.hierarchical_infolearn.domain.til.business.dto.request.CreateTilRequest
import com.example.hierarchical_infolearn.domain.til.business.dto.response.CreateTilResponse
import com.example.hierarchical_infolearn.domain.til.business.dto.response.TilContentImageResponse
import com.example.hierarchical_infolearn.domain.til.business.service.til.TilService
import com.example.hierarchical_infolearn.global.file.dto.ImageFileRequest
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
@Validated
@RequestMapping("/api/infolearn/v1/til")
class TilController(
    private val tilService: TilService,
){
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(
        summary = "til 생성", description = "til을 생성합니다",
        responses = [
            ApiResponse(
                responseCode = "201",
                description = "til이 성공적으로 생성됨",
                content = [Content(schema = Schema(implementation = CreateTilResponse::class))])]
    )
    fun createTil(
        @RequestBody
        @Valid
        request: CreateTilRequest,
    ) = tilService.createTil(request)

    @PostMapping("/image")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(
        summary = "이미지 생성", description = "til 내용에 들어갈 이미지를 생성합니다",
        responses = [
            ApiResponse(
                responseCode = "201",
                description = "이미지가 성공적으로 생성됨",
                content = [Content(schema = Schema(implementation = TilContentImageResponse::class))])
        ]
    )
    fun createImage(
        @RequestBody
        @Valid
        request: ImageFileRequest
    ): TilContentImageResponse{
        return tilService.createImage(
            req = request
        )
    }

}