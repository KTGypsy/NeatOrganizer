package one.gypsy.neatorganizer.database.entity

/**
 * Interface that represents an entity with a creation timestamp.
 * Classes implementing this interface should have a `createdAt` property
 * that holds the timestamp indicating when the entity was created.
 *
 * @property createdAt The timestamp when the entity was created, represented as a `Long`.
 */
interface Timestamped {
    val createdAt: Long
}
