package repository;

import domain.Reservation;
import domain.Trip;
import domain.User;
import domain.validator.Validator;
import utils.JdbcUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

public class ReservationDBRepository extends AbstractDBRepository<Long, Reservation> implements ReservationRepository {

    public ReservationDBRepository(JdbcUtils jdbcUtils, Validator<Long, Reservation> validator) {
        super(jdbcUtils, validator);
    }

    @Override
    protected PreparedStatement getSaveStatement(Connection connection, Reservation entity) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("INSERT INTO reservations(client, phonenumber, tickets, tripid, userid) VALUES (?,?,?,?,?);");
        statement.setString(1, entity.getClient());
        statement.setString(2, entity.getPhoneNumber());
        statement.setInt(3, entity.getTickets());
        statement.setLong(4, entity.getTrip().getId());
        statement.setLong(5, entity.getUser().getId());
        return statement;
    }

    @Override
    protected PreparedStatement getDeleteStatement(Connection connection, Long aLong) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("DELETE FROM reservations WHERE id=?;");
        statement.setLong(1, aLong);
        return statement;
    }

    @Override
    protected PreparedStatement getUpdateStatement(Connection connection, Reservation entity) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("UPDATE reservations " +
                "SET client=?,phonenumber=?,tickets=?,tripid=?,userid=? " +
                "WHERE id=?;");
        statement.setString(1, entity.getClient());
        statement.setString(2, entity.getPhoneNumber());
        statement.setInt(3, entity.getTickets());
        statement.setLong(4, entity.getTrip().getId());
        statement.setLong(5, entity.getUser().getId());
        statement.setLong(6, entity.getId());
        return statement;
    }

    @Override
    protected PreparedStatement getFindStatement(Connection connection, Long aLong) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("SELECT r.id,client,phonenumber,tickets,tripid,userid,username,password,destination,transportfirm,departuretime,price,seats " +
                "FROM reservations r INNER JOIN users u on r.userid = u.id INNER JOIN trips t on r.tripid = t.id " +
                "WHERE r.id=?;");
        statement.setLong(1, aLong);
        return statement;
    }

    @Override
    protected PreparedStatement getFindAllStatement(Connection connection) throws SQLException {
        return connection.prepareStatement("SELECT r.id,client,phonenumber,tickets,tripid,userid,username,password,destination,transportfirm,departuretime,price,seats " +
                "FROM reservations r INNER JOIN users u on r.userid = u.id INNER JOIN trips t on r.tripid = t.id;");
    }

    @Override
    protected Reservation extractEntity(ResultSet resultSet) {
        logger.traceEntry();
        Reservation reservation = null;
        try{
            Long id = resultSet.getLong("id");
            String client = resultSet.getString("client");
            String phoneNumber = resultSet.getString("phonenumber");
            int tickets = resultSet.getInt("tickets");

            Long tripId = resultSet.getLong("tripid");
            String destination = resultSet.getString("destination");
            String transportFirm = resultSet.getString("transportfirm");
            LocalDateTime departureTime = resultSet.getTimestamp("departuretime").toLocalDateTime();
            float price = resultSet.getFloat("price");
            int seats = resultSet.getInt("seats");
            Trip trip = new Trip(tripId,destination,transportFirm,departureTime,price,seats);

            Long userId = resultSet.getLong("userid");
            String username = resultSet.getString("username");
            String password = resultSet.getString("password");
            User user = new User(userId, username, password);

            reservation = new Reservation(id,client,phoneNumber,tickets,trip,user);
        }catch (SQLException e){
            logger.error(e);
            System.out.println("Error "+e);
        }
        logger.traceExit(reservation);
        return reservation;
    }
}
