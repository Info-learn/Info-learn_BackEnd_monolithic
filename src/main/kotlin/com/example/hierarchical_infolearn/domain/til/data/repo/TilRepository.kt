package com.example.hierarchical_infolearn.domain.til.data.repo

import com.example.hierarchical_infolearn.domain.til.data.entity.Til
import org.springframework.data.jpa.repository.JpaRepository

interface TilRepository: JpaRepository<Til,Long> {
}