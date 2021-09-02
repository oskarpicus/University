package repository;

import domain.Entity;
import domain.validator.Validator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import utils.JdbcUtils;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public abstract class AbstractDBRepository<ID,E extends Entity<ID>> implements Repository<ID,E> {

    protected final JdbcUtils jdbcUtils;
    protected final Validator<ID,E> validator;
    protected final static Logger logger = LogManager.getLogger();

    public AbstractDBRepository(JdbcUtils jdbcUtils, Validator<ID, E> validator){
        this.jdbcUtils = jdbcUtils;
        this.validator = validator;
    }

    @Override
    public Optional<E> save(E entity) {
        logger.traceEntry("Saving {}", entity);
        if(entity==null){
            throw logger.throwing(new IllegalArgumentException());
        }
        validator.validate(entity);
        Optional<E> result = Optional.empty();
        try(Connection connection = jdbcUtils.getConnection()){
            try(PreparedStatement statement = getSaveStatement(connection, entity)){
                int affectedRows = statement.executeUpdate();
                logger.info("{} rows affected",affectedRows);
                result = affectedRows!=0 ? Optional.empty() : Optional.of(entity);
            }
        }catch (SQLException e){
            logger.error(e);
            System.out.println("Error "+e);
        }
        logger.traceExit(result);
        return result;
    }

    @Override
    public Optional<E> delete(ID id) {
        logger.traceEntry("Delete {}",id);
        if(id==null){
            throw logger.throwing(new IllegalArgumentException());
        }
        Optional<E> entity = find(id);
        int result = 0;
        try(Connection connection = jdbcUtils.getConnection()){
            try(PreparedStatement statement = getDeleteStatement(connection,id)){
                result = statement.executeUpdate();
                logger.info("{} rows affected",result);
            }
        }catch (SQLException e){
            logger.error(e);
            System.out.println("Error "+e);
        }
        logger.traceExit();
        return result==0 || entity.isEmpty() ? Optional.empty() : entity;
    }

    @Override
    public Optional<E> update(E entity) {
        logger.traceEntry("Update {}",entity);
        if(entity==null){
            throw logger.throwing(new IllegalArgumentException());
        }
        validator.validate(entity);
        int result = 0;
        try(Connection connection = jdbcUtils.getConnection()){
            try(PreparedStatement statement = getUpdateStatement(connection,entity)){
                result = statement.executeUpdate();
                logger.info("{} rows affected", result);
            }
        }catch (SQLException e){
            logger.error(e);
            System.out.println("Error "+e);
        }
        logger.traceExit();
        return result==0 ? Optional.of(entity) : Optional.empty();
    }

    @Override
    public Optional<E> find(ID id) {
        logger.traceEntry("Find {}",id);
        if(id==null){
            throw logger.throwing(new IllegalArgumentException());
        }
        Optional<E> result = Optional.empty();
        try(Connection connection = jdbcUtils.getConnection()){
            try(PreparedStatement statement = getFindStatement(connection,id)){
                try(ResultSet resultSet = statement.executeQuery()){
                    if(resultSet.next()){
                        result = Optional.of(extractEntity(resultSet));
                    }
                }
            }
        }catch (SQLException e){
            logger.error(e);
            System.out.println("Error "+e);
        }
        logger.traceExit(result);
        return result;
    }

    @Override
    public Iterable<E> findAll() {
        logger.traceEntry();
        List<E> list = new ArrayList<>();
        try(Connection connection = jdbcUtils.getConnection()){
            try(PreparedStatement statement = getFindAllStatement(connection)){
                try(ResultSet resultSet = statement.executeQuery()){
                    while (resultSet.next()){
                        list.add(extractEntity(resultSet));
                    }
                }
            }
        }catch (SQLException e){
            logger.error(e);
            System.out.println("Error "+e);
        }
        logger.traceExit(list);
        return list;
    }

    protected abstract PreparedStatement getSaveStatement(Connection connection, E entity) throws SQLException;

    protected abstract PreparedStatement getDeleteStatement(Connection connection, ID id) throws SQLException;

    protected abstract PreparedStatement getUpdateStatement(Connection connection, E entity) throws SQLException;

    protected abstract PreparedStatement getFindStatement(Connection connection, ID id) throws SQLException;

    protected abstract PreparedStatement getFindAllStatement(Connection connection) throws SQLException;

    /**
     * Method for obtaining an entity based on a result set
     * @param resultSet: ResultSet, must point to a valid row
     * @return entity: E, the corresponding entry from resultSet's current row
     */
    protected abstract E extractEntity(ResultSet resultSet);
}
