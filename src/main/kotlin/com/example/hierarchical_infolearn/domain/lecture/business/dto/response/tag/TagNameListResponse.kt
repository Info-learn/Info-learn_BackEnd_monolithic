package com.example.hierarchical_infolearn.domain.lecture.business.dto.response.tag

data class TagNameListResponse(
    val tags: List<TagResponse>
){
    data class TagResponse(
        val name: String,
        val usageCount: Long,
    )
}
