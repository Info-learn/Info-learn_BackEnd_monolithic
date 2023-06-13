package com.example.hierarchical_infolearn.domain.til.business.service.til

import com.example.hierarchical_infolearn.domain.til.business.dto.request.CreateTilRequest
import com.example.hierarchical_infolearn.domain.til.business.dto.response.CreateTilResponse
import com.example.hierarchical_infolearn.domain.til.business.dto.response.TilContentImageResponse
import com.example.hierarchical_infolearn.global.file.dto.ImageFileRequest

interface TilService {

    fun createTil(req: CreateTilRequest): CreateTilResponse

    fun createImage(req: ImageFileRequest): TilContentImageResponse

}