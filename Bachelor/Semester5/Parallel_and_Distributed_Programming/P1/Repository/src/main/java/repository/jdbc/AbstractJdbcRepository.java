package repository.jdbc;

import domain.Entity;
import repository.Repository;
import repository.jdbc.utils.JdbcUtils;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public abstract class AbstractJdbcRepository<ID, E extends Entity<ID>> implements Repository<ID, E> {

    protected final JdbcUtils jdbcUtils;

    public AbstractJdbcRepository(JdbcUtils jdbcUtils) {
        this.jdbcUtils = jdbcUtils;
    }

    @Override
    public int save(E entity) {
        if (entity == null) {
            throw new IllegalArgumentException();
        }

        try (Connection connection = jdbcUtils.getConnection()) {
            try (PreparedStatement statement = getSaveStatement(connection, entity)) {
                statement.executeUpdate();
            }

            try (Statement statement = connection.createStatement()) {
                ResultSet generatedKeys = statement.executeQuery("SELECT lastval()");
                if (generatedKeys.next()) {
                    return generatedKeys.getInt(1);
                }
            }

            return 0;
        } catch (SQLException e) {
            System.out.println("Error " + e);
            return 0;
        }
    }

    @Override
    public Optional<E> find(ID id) {
        if (id == null) {
            throw new IllegalArgumentException();
        }

        Optional<E> result = Optional.empty();
        try (Connection connection = jdbcUtils.getConnection()) {
            try (PreparedStatement statement = getFindStatement(connection, id)) {
                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        result = Optional.of(extractEntity(resultSet));
                    }
                }
            }
        } catch (SQLException e) {
            System.out.println("Error " + e);
        }
        return result;
    }

    @Override
    public Optional<E> update(E entity) {
        if (entity == null) {
            throw new IllegalArgumentException();
        }

        int result = 0;
        try (Connection connection = jdbcUtils.getConnection()) {
            try (PreparedStatement statement = getUpdateStatement(connection, entity)) {
                result = statement.executeUpdate();
            }
        } catch (SQLException e) {
            System.out.println("Error " + e);
        }

        return result == 0 ? Optional.of(entity) : Optional.empty();
    }

    @Override
    public Iterable<E> findAll() {
        List<E> list = new ArrayList<>();
        try (Connection connection = jdbcUtils.getConnection()) {
            try (PreparedStatement statement = getFindAllStatement(connection)) {
                try (ResultSet resultSet = statement.executeQuery()) {
                    while (resultSet.next()) {
                        list.add(extractEntity(resultSet));
                    }
                }
            }
        } catch (SQLException e) {
            System.out.println("Error " + e);
        }
        return list;
    }

    protected abstract PreparedStatement getSaveStatement(Connection connection, E entity) throws SQLException;

    protected abstract PreparedStatement getFindStatement(Connection connection, ID id) throws SQLException;

    protected abstract PreparedStatement getFindAllStatement(Connection connection) throws SQLException;

    protected abstract PreparedStatement getUpdateStatement(Connection connection, E entity) throws SQLException;

    /**
     * Method for obtaining an entity based on a result set
     *
     * @param resultSet: ResultSet, must point to a valid row
     * @return entity: E, the corresponding entry from resultSet's current row
     */
    protected abstract E extractEntity(ResultSet resultSet);
}