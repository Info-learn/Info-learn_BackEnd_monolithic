package com.example.hierarchical_infolearn.domain.user.data.repo.user

import com.example.hierarchical_infolearn.domain.user.data.entity.teacher.Teacher
import org.springframework.data.jpa.repository.JpaRepository

interface TeacherRepository: JpaRepository<Teacher, String> {
}