package com.example.hierarchical_infolearn.domain.user.data.entity

import com.example.hierarchical_infolearn.domain.til.data.entity.Til
import com.example.hierarchical_infolearn.domain.user.data.entity.common.user.Role
import com.example.hierarchical_infolearn.global.base.entity.BaseTimeEntity
import org.hibernate.annotations.SQLDelete
import org.hibernate.annotations.Where
import javax.persistence.*


@Entity(name = "tbl_user")
@Table(name = "tbl_user")
@Where(clause = "is_deleted = false")
@SQLDelete(sql = "UPDATE `tbl_user` SET is_deleted = true WHERE id = ?")
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "user_type")
abstract class User(
    email: String,
    accountId: String,
    nickname: String,
    password: String,
    role: Role
): BaseTimeEntity() {

    @Id
    @Column(name = "account_id", length = 50, nullable = false, unique = true)
    val accountId: String = accountId

    @Column(name = "email", length = 50, nullable = false,  unique = false)
    val email: String = email

    @Column(name = "nickname", length = 50, nullable = false, unique = false)
    var nickname: String = nickname
        protected set

    @Column(name = "password", length = 255, nullable = false, unique = false)
    var password: String = password
        protected set

    @Enumerated(value = EnumType.STRING)
    @Column(name = "role", length = 7, nullable = false, unique = false)
    var role: Role = role
        protected set

    @Column(name = "profile_image_url", length = 255, nullable = true, unique = false)
    var profileImageUrl: String? = null
        protected set

    @Column(name = "is_deleted", nullable = false)
    var isDeleted: Boolean = false
        protected set

    @OneToMany(mappedBy = "user")
    var til: MutableSet<Til> = HashSet()
        protected set

    fun uploadProfileImage(file: String) {
        this.profileImageUrl = file
    }

}