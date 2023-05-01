package com.example.hierarchical_infolearn.domain.user.data.entity.teacher

import com.example.hierarchical_infolearn.domain.user.data.entity.User
import com.example.hierarchical_infolearn.domain.user.data.entity.common.user.Role
import org.hibernate.annotations.OnDelete
import org.hibernate.annotations.OnDeleteAction
import javax.persistence.DiscriminatorValue
import javax.persistence.Entity

@Entity
@DiscriminatorValue("tbl_student")
@OnDelete(action = OnDeleteAction.CASCADE)
class Teacher(
    accountId: String,
    nickname: String,
    email: String,
    password: String
): User(
    email = email,
    accountId = accountId,
    nickname = nickname,
    password = password,
    role = Role.TEACHER
)