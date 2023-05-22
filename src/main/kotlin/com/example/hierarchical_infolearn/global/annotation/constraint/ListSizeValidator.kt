package com.example.hierarchical_infolearn.global.annotation.constraint

import com.example.hierarchical_infolearn.global.annotation.ListSizeLimit
import javax.validation.ConstraintValidator
import javax.validation.ConstraintValidatorContext

class ListSizeValidator: ConstraintValidator<ListSizeLimit, List<*>> {

    private var maxSize = 0
    private var minSize = 0
    private var message = ""

    override fun isValid(itemList: List<*>, cxt: ConstraintValidatorContext): Boolean {
        return if (itemList.size in minSize..maxSize) {
            true
        } else {
            cxt.disableDefaultConstraintViolation()
            cxt.buildConstraintViolationWithTemplate("리스트 원소의 갯수는 최소 ${minSize}개 최대 ${maxSize}개 입니다.")
                .addConstraintViolation()
            false
        }
    }

    override fun initialize(constraintAnnotation: ListSizeLimit) {
        maxSize = constraintAnnotation.max
        minSize = constraintAnnotation.min
        message = constraintAnnotation.message
    }
}