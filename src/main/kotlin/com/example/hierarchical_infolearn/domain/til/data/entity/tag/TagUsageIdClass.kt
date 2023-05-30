package com.example.hierarchical_infolearn.domain.til.data.entity.tag

import java.io.Serializable
import java.util.UUID

data class TagUsageIdClass (
    val tag: String? = null,
    val til: UUID? = null,
): Serializable