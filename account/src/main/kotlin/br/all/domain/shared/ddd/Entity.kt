package br.all.domain.shared.ddd

abstract class Entity <T> (val id: Identifier<T>) {

    final override fun equals(other: Any?) = when {
        this === other -> true
        other !is Entity<*> -> false
        this::class != other::class -> false
        else -> id == other.id
    }

    final override fun hashCode() = id.hashCode()
}