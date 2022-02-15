package repository

import validator.Validator
import java.util.*

interface Repository<ID, E> {
    val validator: Validator<E>

    fun save(e: E): Optional<E>

    fun delete(id: ID): Optional<E>

    fun update(e: E): Optional<E>

    fun find(id: ID): Optional<E>

    fun findAll(): List<E>
}