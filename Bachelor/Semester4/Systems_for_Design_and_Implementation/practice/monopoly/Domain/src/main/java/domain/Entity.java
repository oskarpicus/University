package domain;

import java.io.Serializable;

public interface Entity<ID extends Serializable> extends Serializable{

    /**
     * Getter for the id of an {@code Entity}
     * @return id: ID, id of the current {@code Entity}
     */
    ID getId();

    /**
     * Setter for the id of an @code Entity}
     * @param id: ID
     * id of the current @code Entity} is set to {@param id}
     */
    void setId(ID id);
}