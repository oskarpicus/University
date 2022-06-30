package repository;

import domain.Entity;

import java.util.Optional;

public interface Repository<ID, E extends Entity<ID>> {
    int save(E entity);

    Optional<E> find(ID id);

    Optional<E> update(E entity);

    Iterable<E> findAll();
}
