package repository;

import domain.Trip;
import domain.validator.Validator;
import utils.JdbcUtils;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class TripDBRepository extends AbstractDBRepository<Long,Trip> implements TripRepository {

    public TripDBRepository(JdbcUtils jdbcUtils, Validator<Long, Trip> validator) {
        super(jdbcUtils, validator);
    }

    @Override
    public Iterable<Trip> findTripsByDestinationTime(String destination, LocalDateTime from, LocalDateTime to) {
        logger.traceEntry(destination,from,to);
        List<Trip> tripList = new ArrayList<>();
        try(Connection connection = jdbcUtils.getConnection()){
            try(PreparedStatement statement = connection.prepareStatement("SELECT * FROM trips WHERE destination=? AND departuretime BETWEEN ? AND ?;")){
                statement.setString(1, destination);
                statement.setTimestamp(2, Timestamp.valueOf(from));
                statement.setTimestamp(3, Timestamp.valueOf(to));
                try(ResultSet resultSet = statement.executeQuery()){
                    while (resultSet.next()){
                        tripList.add(this.extractEntity(resultSet));
                    }
                }
            }
        }catch (SQLException e){
            logger.error(e);
            System.out.println("Error "+e);
        }
        logger.traceExit(tripList);
        return tripList;
    }

    @Override
    protected PreparedStatement getSaveStatement(Connection connection, Trip entity) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("INSERT INTO trips(destination, transportfirm, departuretime, price, seats) VALUES (?,?,?,?,?);");
        statement.setString(1, entity.getDestination());
        statement.setString(2, entity.getTransportFirm());
        statement.setTimestamp(3, Timestamp.valueOf(entity.getDepartureTime()));
        statement.setFloat(4, (float)entity.getPrice());
        statement.setInt(5, entity.getSeats());
        return statement;
    }

    @Override
    protected PreparedStatement getDeleteStatement(Connection connection, Long aLong) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("DELETE FROM trips WHERE id=?;");
        statement.setLong(1, aLong);
        return statement;
    }

    @Override
    protected PreparedStatement getUpdateStatement(Connection connection, Trip entity) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("UPDATE trips SET destination=?, transportfirm=?, departuretime=?, price=?, seats=? WHERE id=?;");
        statement.setString(1, entity.getDestination());
        statement.setString(2, entity.getTransportFirm());
        statement.setTimestamp(3, Timestamp.valueOf(entity.getDepartureTime()));
        statement.setFloat(4, (float)entity.getPrice());
        statement.setInt(5, entity.getSeats());
        statement.setLong(6, entity.getId());
        return statement;
    }

    @Override
    protected PreparedStatement getFindStatement(Connection connection, Long aLong) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("SELECT * FROM trips WHERE id=?;");
        statement.setLong(1, aLong);
        return statement;
    }

    @Override
    protected PreparedStatement getFindAllStatement(Connection connection) throws SQLException {
        return connection.prepareStatement("SELECT * FROM trips;");
    }

    @Override
    protected Trip extractEntity(ResultSet resultSet) {
        logger.traceEntry();
        Trip trip = null;
        try {
            Long id = resultSet.getLong("id");
            String destination = resultSet.getString("destination");
            String transportFirm = resultSet.getString("transportFirm");
            LocalDateTime departureTime = resultSet.getTimestamp("departureTime").toLocalDateTime();
            float price = resultSet.getFloat("price");
            int seats = resultSet.getInt("seats");
            trip = new Trip(id, destination, transportFirm, departureTime, price, seats);

        }catch (SQLException e){
            logger.error(e);
            System.out.println("Error "+e);
        }
        logger.traceExit(trip);
        return trip;
    }
}
