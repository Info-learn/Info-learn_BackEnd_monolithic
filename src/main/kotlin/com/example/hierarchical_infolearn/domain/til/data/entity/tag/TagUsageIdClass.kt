package com.example.hierarchical_infolearn.domain.til.data.entity.tag

import java.io.Serializable
import java.util.*

data class TagUsageIdClass (
    val tag: UUID? = null,
    val til: Long? = null,
): Serializable