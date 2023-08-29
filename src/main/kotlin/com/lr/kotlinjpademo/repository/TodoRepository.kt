package com.lr.kotlinjpademo.repository

import com.lr.kotlinjpademo.entity.TodoEntity
import jakarta.persistence.QueryHint
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.QueryHints
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
interface TodoRepository : JpaRepository<TodoEntity, UUID> {
    @QueryHints(
        QueryHint(
            name = "org.hibernate.cacheable",
            value = "true",
        ),
    )
    fun findAllByTodoTitle(todoTitle: String): List<TodoEntity>
}
