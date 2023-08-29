package com.lr.kotlinjpademo.todo

import com.mickaelb.api.AssertHibernateSQLCount
import com.mickaelb.integration.spring.HibernateAssertTestListener
import io.kotest.assertions.throwables.shouldNotThrow
import io.kotest.assertions.throwables.shouldThrowExactly
import io.kotest.matchers.nulls.shouldNotBeNull
import io.kotest.matchers.should
import io.kotest.matchers.shouldBe
import jakarta.transaction.Transactional
import org.hibernate.LazyInitializationException
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.repository.findByIdOrNull
import org.springframework.test.context.TestExecutionListeners
import org.springframework.test.context.transaction.TestTransaction

@SpringBootTest
@TestExecutionListeners(
    listeners = [HibernateAssertTestListener::class],
    mergeMode = TestExecutionListeners.MergeMode.MERGE_WITH_DEFAULTS,
)
class BasicDemo : TodoEntityTest() {

    @Test
    @Transactional
    @AssertHibernateSQLCount(inserts = 2, selects = 1)
    fun `create testdata, then read from the database, but make sure the lazy entity is not loaded because it is untouched`() {
        val (todo, todoList) = createTestData()

        // End session intentionally
        TestTransaction.end()

        shouldNotThrow<LazyInitializationException> {
            todoRepository.findByIdOrNull(todo.id).shouldNotBeNull().should {
                it.todoTitle shouldBe "DemoTodo"
            }
        }
    }

    @Test
    @Transactional
    @AssertHibernateSQLCount(inserts = 2, selects = 1)
    fun `create testdata, then read from the database, and provoke an exception by ending the transaction beforehand`() {
        val (todo, todoList) = createTestData()

        // End session intentionally
        TestTransaction.end()

        shouldThrowExactly<LazyInitializationException> {
            todoRepository.findByIdOrNull(todo.id)!!.todoList.listName
        }
    }

    @Test
    @Transactional
    @AssertHibernateSQLCount(inserts = 2, selects = 2)
    fun `create testdata, then read from the database, and touch the lazy loaded entity`() {
        val (todo, todoList) = createTestData()

        todoRepository.findByIdOrNull(todo.id).shouldNotBeNull().should {
            it.todoTitle shouldBe "DemoTodo"
            it.todoList.listName shouldBe "DemoList"
        }
    }
}
