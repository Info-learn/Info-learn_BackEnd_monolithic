package com.example.hierarchical_infolearn.domain.lecture.data.repo.tag.querydsl

import com.example.hierarchical_infolearn.domain.lecture.data.entity.tag.QTag.tag
import com.example.hierarchical_infolearn.domain.lecture.data.entity.tag.Tag
import com.querydsl.core.BooleanBuilder
import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.stereotype.Repository

@Repository
class LectureTagSupportImpl(
    private val queryFactory: JPAQueryFactory
): LectureTagSupport {

    override fun queryAllLectureTagNoOffset(usageCount: Long?, limit: Long): List<Tag>? {
        val expression = BooleanBuilder()
        if(usageCount != null) expression.and(tag.usageCount.lt(usageCount))

        return queryFactory
            .selectFrom(tag)
            .where(expression)
            .groupBy(tag.id)
            .orderBy(tag.usageCount.desc())
            .limit(limit)
            .fetch()
    }
}