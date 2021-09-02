package domain.validator;

import domain.Entity;

public interface Validator<ID, E extends Entity<ID>> {

    /**
     * Validates a given entity
     * @param entity: E, entity to be validated
     * @throws ValidationException if entity is not valid
     */
    void validate(E entity);
}
