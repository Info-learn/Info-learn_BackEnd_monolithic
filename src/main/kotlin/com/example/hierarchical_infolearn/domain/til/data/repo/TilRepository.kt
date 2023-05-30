package com.example.hierarchical_infolearn.domain.til.data.repo

import com.example.hierarchical_infolearn.domain.til.data.entity.Til
import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

interface TilRepository: JpaRepository<Til, UUID> {
}