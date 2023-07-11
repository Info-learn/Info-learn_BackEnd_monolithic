package com.example.hierarchical_infolearn.domain.til.data.entity

import com.example.hierarchical_infolearn.domain.til.data.entity.comment.Comment
import com.example.hierarchical_infolearn.domain.til.data.entity.like.Like
import com.example.hierarchical_infolearn.domain.til.data.entity.socket.TilUser
import com.example.hierarchical_infolearn.domain.til.data.entity.tag.TagUsage
import com.example.hierarchical_infolearn.domain.user.data.entity.User
import com.example.hierarchical_infolearn.global.base.entity.BaseTimeEntity
import org.hibernate.annotations.SQLDelete
import org.hibernate.annotations.Where
import java.util.*
import javax.persistence.*


@Entity
@Table(name = "til", indexes = [Index(name = "i_search_title", columnList = "search_title")])
@SQLDelete(sql = "UPDATE `til` SET is_deleted = true WHERE id = ?")
@Where(clause = "is_deleted = false")
class Til(
    id: UUID? = null,
    title: String,
    searchTitle: String,
    subTitle: String?,
    isPrivate: Boolean,
    tilContent: String,
    user: User,
): BaseTimeEntity() {

    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(columnDefinition = "BINARY(16)")
    val id: UUID? = id

    @Column(name = "title", length = 50, nullable = true, unique = false)
    var title: String = title
        protected set

    @Column(name = "search_title", length = 100, nullable = true, unique = false)
    var searchTitle: String = searchTitle
        protected set

    @Column(name = "sub_title", length = 30, nullable = true, unique = false)
    var subTitle: String? = subTitle
        protected set

    @Column(name = "content_id", length = 50, nullable = false, unique = true, columnDefinition = "CHAR(24)")
    var contentId: String = tilContent
        protected set

    @Column(name = "til_thumbnail_url", length = 255, nullable = true, unique = false)
    var tilThumbnail: String? = null
        protected set

    @Column(name = "is_private", nullable = false)
    var isPrivate: Boolean = isPrivate
        protected set

    @Column(name = "is_deleted", nullable = false)
    var isDeleted: Boolean = false
        protected set

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    var user: User = user

    @OneToMany(mappedBy = "til")
    var likes: MutableSet<Like> = HashSet()
        protected set

    @OneToMany(mappedBy = "til")
    var comments: MutableSet<Comment> = HashSet()
        protected set

    @OneToMany(mappedBy = "til", orphanRemoval = true)
    var tagUsageList: MutableSet<TagUsage> = HashSet()
        protected set

    @OneToMany(mappedBy = "til", cascade = [CascadeType.REMOVE], fetch = FetchType.EAGER)
    var tilUserList: MutableList<TilUser> = arrayListOf()
        protected set

    fun addTilUser(tilUser: TilUser) {
        this.tilUserList.add(tilUser)
    }

    fun uploadTilThumbnail(url: String) {
        this.tilThumbnail = url
    }
}