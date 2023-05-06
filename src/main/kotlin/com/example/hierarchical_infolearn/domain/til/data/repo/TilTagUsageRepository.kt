package com.example.hierarchical_infolearn.domain.til.data.repo

import com.example.hierarchical_infolearn.domain.til.data.entity.tag.TagUsage
import com.example.hierarchical_infolearn.domain.til.data.entity.tag.TagUsageIdClass
import org.springframework.data.jpa.repository.JpaRepository

interface TilTagUsageRepository:JpaRepository<TagUsage, TagUsageIdClass> {
}