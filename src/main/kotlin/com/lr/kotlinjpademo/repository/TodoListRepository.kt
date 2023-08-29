package com.lr.kotlinjpademo.repository

import com.lr.kotlinjpademo.entity.TodoEntity
import com.lr.kotlinjpademo.entity.TodoListEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
interface TodoListRepository : JpaRepository<TodoListEntity, UUID>
