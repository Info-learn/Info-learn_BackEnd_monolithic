package com.example.hierarchical_infolearn.domain.til.data.repo

import com.example.hierarchical_infolearn.domain.til.data.entity.tag.TagUsage
import org.springframework.data.jpa.repository.JpaRepository

interface TilTagUsageRepository:JpaRepository<TagUsage, TagUsage.IdClass> {
}