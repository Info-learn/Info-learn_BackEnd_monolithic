package com.example.hierarchical_infolearn.global.annotation

import com.example.hierarchical_infolearn.global.annotation.constraint.ListSizeValidator
import javax.validation.Constraint
import javax.validation.Payload
import kotlin.reflect.KClass


@MustBeDocumented
@Target(AnnotationTarget.FIELD, AnnotationTarget.VALUE_PARAMETER)
@Retention(AnnotationRetention.RUNTIME)
@Constraint(validatedBy = [ListSizeValidator::class])
annotation class ListSizeLimit(
    val message: String = "",
    val max: Int = 100,
    val min: Int = 0,
    val groups: Array<KClass<*>> = [],
    val payload: Array<KClass<out Payload>> = []
)