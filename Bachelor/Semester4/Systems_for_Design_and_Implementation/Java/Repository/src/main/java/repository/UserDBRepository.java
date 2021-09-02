package repository;

import domain.User;
import domain.validator.Validator;
import utils.JdbcUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class UserDBRepository extends AbstractDBRepository<Long,User> implements UserRepository{

    public UserDBRepository(JdbcUtils jdbcUtils, Validator<Long, User> validator) {
        super(jdbcUtils,validator);
    }

    @Override
    protected PreparedStatement getSaveStatement(Connection connection, User entity) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("INSERT INTO users(username, password) VALUES (?,?);");
        statement.setString(1, entity.getUsername());
        statement.setString(2, entity.getPassword());
        return statement;
    }

    @Override
    protected PreparedStatement getDeleteStatement(Connection connection, Long aLong) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("DELETE FROM users WHERE id=?;");
        statement.setLong(1, aLong);
        return statement;
    }

    @Override
    protected PreparedStatement getUpdateStatement(Connection connection, User entity) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("UPDATE users SET username=?, password=? WHERE id=?;");
        statement.setString(1, entity.getUsername());
        statement.setString(2, entity.getPassword());
        statement.setLong(3, entity.getId());
        return statement;
    }

    @Override
    protected PreparedStatement getFindStatement(Connection connection, Long aLong) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("SELECT * FROM users WHERE id=?;");
        statement.setLong(1, aLong);
        return statement;
    }

    @Override
    protected PreparedStatement getFindAllStatement(Connection connection) throws SQLException {
        return connection.prepareStatement("SELECT * FROM users;");
    }

    @Override
    protected User extractEntity(ResultSet resultSet) {
        logger.traceEntry();
        User result = null;
        try {
            Long id = resultSet.getLong("id");
            String name = resultSet.getString("username");
            String password = resultSet.getString("password");
            result = new User(id, name, password);
        }catch (SQLException e){
            logger.error(e);
            System.out.println("Error "+e);
        }
        logger.traceExit(result);
        return result;
    }

    @Override
    public Optional<User> findByUsernamePassword(String username, String password) {
        logger.traceEntry("Find username {}, {}", username, password);
        Optional<User> result = Optional.empty();
        try(Connection connection = jdbcUtils.getConnection()){
            try(PreparedStatement statement = connection.prepareStatement("SELECT * FROM users WHERE username=? AND password=?;")){
                statement.setString(1,username);
                statement.setString(2,password);
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
}
