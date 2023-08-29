package com.lr.kotlinjpademo.todo

import com.mickaelb.api.AssertHibernateL2CCount
import com.mickaelb.api.AssertHibernateSQLCount
import com.mickaelb.integration.spring.HibernateAssertTestListener
import io.kotest.matchers.nulls.shouldNotBeNull
import io.kotest.matchers.should
import io.kotest.matchers.shouldBe
import jakarta.transaction.Transactional
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.repository.findByIdOrNull
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.TestExecutionListeners
import org.springframework.test.context.transaction.TestTransaction

@SpringBootTest
@ActiveProfiles("cache")
@TestExecutionListeners(
    listeners = [HibernateAssertTestListener::class],
    mergeMode = TestExecutionListeners.MergeMode.MERGE_WITH_DEFAULTS,
)
class CacheDemo : TodoEntityTest() {

    @Test
    @Transactional
    @AssertHibernateSQLCount(inserts = 2, selects = 1)
    @AssertHibernateL2CCount(puts = 1, hits = 1, misses = 1)
    fun `create testdata, then read from the database to load into cache, then test cache hit`() {
        val (todo, todoList) = createTestData()

        // Expect a cache MISS here

        todoRepository.findByIdOrNull(todo.id).shouldNotBeNull().should {
            it.todoTitle shouldBe "DemoTodo"
        }

        TestTransaction.end()
        TestTransaction.start()

        // Expect a cache HIT here

        todoRepository.findByIdOrNull(todo.id).shouldNotBeNull().should {
            it.todoTitle shouldBe "DemoTodo"
        }
    }

    @Test
    @Transactional
    @AssertHibernateSQLCount(inserts = 4, selects = 2)
    @AssertHibernateL2CCount(puts = 2, hits = 0, misses = 2)
    fun `create testdata, then read from the database to load into cache, but read an unrelated entity for another cache MISS`() {
        val (todo, todoList) = createTestData()
        val (todo2, todoList2) = createTestData()

        // Expect a cache MISS here

        todoRepository.findByIdOrNull(todo.id).shouldNotBeNull().should {
            it.todoTitle shouldBe "DemoTodo"
        }

        TestTransaction.end()
        TestTransaction.start()

        // Expect another cache MISS here

        todoRepository.findByIdOrNull(todo2.id).shouldNotBeNull().should {
            it.todoTitle shouldBe "DemoTodo"
        }
    }
}
