package com.lr.kotlinjpademo.repository

import com.lr.kotlinjpademo.entity.TodoEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
interface TodoRepository : JpaRepository<TodoEntity, UUID>
