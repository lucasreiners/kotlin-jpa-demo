package com.lr.kotlinjpademo.entity

import jakarta.persistence.Cacheable
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import org.hibernate.annotations.Cache
import org.hibernate.annotations.CacheConcurrencyStrategy
import java.util.UUID

@Entity
@Table(name = "todo_lists")
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
data class TodoListEntity(

    @GeneratedValue(strategy = GenerationType.UUID)
    @Id
    val id: UUID? = null,

    @Column(name = "list_name")
    val listName: String,
)
