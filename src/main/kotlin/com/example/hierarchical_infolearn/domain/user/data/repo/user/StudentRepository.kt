package com.example.hierarchical_infolearn.domain.user.data.repo.user

import com.example.hierarchical_infolearn.domain.user.data.entity.student.Student
import org.springframework.data.jpa.repository.JpaRepository

interface StudentRepository: JpaRepository<Student, String> {
}