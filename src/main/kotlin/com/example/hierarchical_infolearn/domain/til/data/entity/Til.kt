package com.example.hierarchical_infolearn.domain.til.data.entity

import com.example.hierarchical_infolearn.domain.til.data.entity.comment.Comment
import com.example.hierarchical_infolearn.domain.til.data.entity.like.Like
import com.example.hierarchical_infolearn.domain.til.data.entity.tag.TagUsage
import com.example.hierarchical_infolearn.domain.user.data.entity.User
import com.example.hierarchical_infolearn.global.base.entity.BaseTimeEntity
import org.hibernate.annotations.SQLDelete
import org.hibernate.annotations.Where
import javax.persistence.*


@Entity(name = "tbl_til")
@Table(name = "tbl_til", indexes = [Index(name = "i_search_title", columnList = "search_title")])
@SQLDelete(sql = "UPDATE `tbl_til` SET is_deleted = true WHERE id = ?")
@Where(clause = "is_deleted = false AND is_private = false")
class Til(
    title: String,
    searchTitle: String,
    subTitle: String?,
    contentId: String,
    user: User,
): BaseTimeEntity() {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null

    @Column(name = "title", length = 50, nullable = false, unique = false)
    var title: String = title
        protected set

    @Column(name = "search_title", length = 100, nullable = false, unique = false)
    var searchTitle: String = searchTitle
        protected set


    @Column(name = "sub_title", length = 30, nullable = true, unique = false)
    var subTitle: String? = subTitle
        protected set

    @Column(name = "content_id", length = 50, nullable = false, unique = true)
    val contentId: String = contentId


    @Column(name = "til_thumbnail_url", length = 255, nullable = true, unique = false)
    var tilThumbnail: String? = null
        protected set

    @Column(name = "is_private", nullable = false)
    var isPrivate: Boolean = false
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

}