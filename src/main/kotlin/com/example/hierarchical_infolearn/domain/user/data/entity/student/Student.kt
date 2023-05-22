package com.example.hierarchical_infolearn.domain.user.data.entity.student

import com.example.hierarchical_infolearn.domain.user.data.entity.User
import com.example.hierarchical_infolearn.domain.user.data.entity.common.user.Role
import org.hibernate.annotations.OnDelete
import org.hibernate.annotations.OnDeleteAction
import java.util.*
import javax.persistence.DiscriminatorValue
import javax.persistence.Entity

@Entity
@DiscriminatorValue("student")
@OnDelete(action = OnDeleteAction.CASCADE)
class Student(
    accountId: String,
    nickname: String,
    email: String,
    password: String
): User(
    accountId = accountId,
    nickname = nickname,
    email = email,
    password = password,
    role = Role.STUDENT
)