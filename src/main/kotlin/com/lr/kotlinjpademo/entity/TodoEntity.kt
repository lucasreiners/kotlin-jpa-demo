package com.lr.kotlinjpademo.entity

import jakarta.persistence.Cacheable
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table
import org.hibernate.annotations.Cache
import org.hibernate.annotations.CacheConcurrencyStrategy
import java.util.UUID

@Entity
@Table(name = "todos")
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
data class TodoEntity(
    /*
    This is the old approach.
    Since version 3.1.0, which came out in 2022,
    the JPA specification provides developers with a new GenerationType.UUID
    we can use in the @GeneratedValue annotation
*/
//    @GeneratedValue(generator = "UUID")
//    @GenericGenerator(
//        name = "UUID",
//        strategy = "org.hibernate.id.UUIDGenerator",
//    )
    @GeneratedValue(strategy = GenerationType.UUID)
    @Id
    val id: UUID? = null,

    @Column(name = "title")
    val todoTitle: String,

    @Column(name = "list_id")
    val todoListId: UUID,
) {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "list_id", updatable = false, insertable = false)
    lateinit var todoList: TodoListEntity
}
