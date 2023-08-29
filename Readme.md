# Kotlin JPA Demo

tl;dr: `./gradlew test`

In our current project we were struggling with the existing Hibernate/JPA setup in combination with Kotlin.

The misconfiguration of the project lead to a massive amount of unnecessary SELECT statements at the database, because
the lazy loading of related entities was not working as expected and all entities were fetched eagerly from the database
which lead to severe performance issues.

In combination with Kotlin, some details need to be considered in the configuration.
If the configuration is correct, even data classes for the entities are working without an issue.

## Findings & things I didn't know

- Entity id: The entity id should be nullable - if it's not, a placeholder value needs to be set during creation, but
  this placeholder will make hibernate perform a select before every insert, to find out if the operation is an insert
  or an update
- Level2 Cache only works across transactions. A cache hit for an existing entity will only occur, after the entity was
  put into the cache and another translation tries to access this entity again
- For a custom repository query to be cached by hibernate, it is not enough to set `use_query_cache: true` in
  application.yaml. The custom query also needs a `@QueryHints` annotation instruction hibernate to cache this query