package com.lr.kotlinjpademo.todo

import com.lr.kotlinjpademo.entity.TodoEntity
import com.lr.kotlinjpademo.entity.TodoListEntity
import com.lr.kotlinjpademo.repository.TodoListRepository
import com.lr.kotlinjpademo.repository.TodoRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.context.transaction.TestTransaction

abstract class TodoEntityTest {
    @Autowired
    lateinit var todoRepository: TodoRepository

    @Autowired
    lateinit var todoListRepository: TodoListRepository

    protected fun createTestData(): Pair<TodoEntity, TodoListEntity> {
        val todoList = todoListRepository.save(
            TodoListEntity(
                listName = "DemoList",
            ),
        )
        val todo = todoRepository.save(
            TodoEntity(
                todoTitle = "DemoTodo",
                todoListId = todoList.id!!,
            ),
        )
        TestTransaction.flagForCommit()
        TestTransaction.end()
        TestTransaction.start()

        return todo to todoList
    }
}
