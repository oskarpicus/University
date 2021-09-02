package domain;

import java.io.Serializable;

public class Entity<ID> implements Serializable {
    private ID id;

    public Entity(ID id) {
        this.id = id;
    }

    /**
     * Getter for the id of an Entity
     * @return id: ID, id of the current Entity
     */
    public ID getId() {
        return id;
    }

    /**
     * Setter for the id of an Entity
     * @param id: ID
     * id of the current Entity is set to {@param id}
     */
    public void setId(ID id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return id.toString();
    }
}
