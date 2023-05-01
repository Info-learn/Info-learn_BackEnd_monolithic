package com.example.hierarchical_infolearn.domain.lecture.data.repo.lecture.querydsl

import com.example.hierarchical_infolearn.domain.lecture.data.entity.Lecture
import com.example.hierarchical_infolearn.domain.lecture.data.entity.QLecture.lecture
import com.example.hierarchical_infolearn.domain.lecture.data.entity.tag.QTagUsage.tagUsage
import com.querydsl.core.BooleanBuilder
import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.stereotype.Repository
import java.time.LocalDateTime

@Repository
class LectureSupportImpl(
    private val queryFactory: JPAQueryFactory,
): LectureSupport {

    override fun queryAllLectureNoOffset(time: LocalDateTime?, limit: Long): List<Lecture>? {
        val expression = BooleanBuilder()
        if(time != null) expression.and(lecture.createdAt.lt(time))

        return queryFactory
            .selectFrom(lecture)
            .leftJoin(tagUsage)
            .on(lecture.id.eq(tagUsage.lecture.id))
            .where(expression, lecture.isDeleted.eq(false))
            .groupBy(lecture.id)
            .orderBy(lecture.createdAt.desc())
            .limit(limit)
            .fetch()
    }

    override fun queryAllLecture(idx: Int, size: Int): List<Lecture>? {
        return queryFactory
            .selectFrom(lecture)
            .leftJoin(tagUsage)
            .on(lecture.id.eq(tagUsage.lecture.id))
            .where(lecture.isDeleted.eq(false))
            .groupBy(lecture.id)
            .orderBy(lecture.createdAt.desc())
            .limit(size.toLong())
            .offset((idx * size).toLong())
            .fetch()
    }

}
