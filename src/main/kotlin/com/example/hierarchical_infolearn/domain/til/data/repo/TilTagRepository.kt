package com.example.hierarchical_infolearn.domain.til.data.repo

import com.example.hierarchical_infolearn.domain.til.data.entity.tag.Tag
import org.springframework.data.jpa.repository.JpaRepository

interface TilTagRepository: JpaRepository<Tag, String> {
}