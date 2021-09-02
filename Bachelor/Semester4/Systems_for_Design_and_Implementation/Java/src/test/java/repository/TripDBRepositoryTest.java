package repository;

import domain.Trip;
import domain.validator.TripValidator;
import domain.validator.ValidationException;
import junit.framework.TestCase;
import utils.JdbcUtils;

import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Properties;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class TripDBRepositoryTest extends TestCase {

    private static final TripDBRepository repository;
    private static final JdbcUtils utils;
    private final Trip tripToSave = new Trip(5L,"Madrid","B .SRL",
            LocalDateTime.of(2021,3,10,12,50,0),90,15);
    private final Trip tripInDB = new Trip(3L,"Bucharest","B .SRL",
            LocalDateTime.of(2021,5,20,20,0,0),50,20);

    static {
        Properties properties = new Properties();
        try{
            properties.load(new FileReader("jdbcTest.properties"));
        }catch (IOException e){
            System.out.println("Error File Properties");
        }
        utils = new JdbcUtils(properties);
        repository = new TripDBRepository(utils, new TripValidator());
    }

    public void testSave() {
        try{
            repository.save(null);
            fail();
        }catch (IllegalArgumentException e){
            assertTrue(true);
        }
        try{
            repository.save(new Trip(999L,"","",LocalDateTime.MAX,12,1));
            fail();
        }catch (ValidationException e){
            assertTrue(true);
        }
        assertEquals(Optional.empty(), repository.save(tripToSave));

        // post
        repository.delete(tripToSave.getId());
        alterSequence();
    }

    public void testDelete() {
        try{
            repository.delete(null);
            fail();
        }catch (IllegalArgumentException e){
            assertTrue(true);
        }
        assertEquals(Optional.empty(),repository.delete(14242L));
        assertEquals(Optional.of(tripInDB), repository.delete(tripInDB.getId()));

        // post
        repository.save(tripInDB);
        try(Connection connection = utils.getConnection()){
            try(PreparedStatement statement = connection.prepareStatement("UPDATE trips SET id=? WHERE id=5;")){
                statement.setLong(1, tripInDB.getId());
                statement.executeUpdate();
            }
        }catch (SQLException e){
            System.out.println("Error "+e);
        }
        alterSequence();
        restoreReservations();
    }

    public void testUpdate() {
        try{
            repository.update(null);
            fail();
        }catch (IllegalArgumentException e){
            assertTrue(true);
        }
        try{
            repository.update(new Trip(19L,"b","",LocalDateTime.MAX,-42,42));
            fail();
        }catch (ValidationException e){
            assertTrue(true);
        }
        assertTrue(repository.update(new Trip(19999L,"a","a",LocalDateTime.MAX,1,1)).isPresent());
        assertTrue(repository.update(new Trip(tripInDB.getId(),"a","b",
                LocalDateTime.of(2011,1,1,1,1,1),10,10))
                .isEmpty());
        Optional<Trip> trip = repository.find(tripInDB.getId());
        assertTrue(trip.isPresent());
        assertEquals("a",trip.get().getDestination());
        assertEquals("b",trip.get().getTransportFirm());
        assertEquals(10,trip.get().getSeats());

        // post
        repository.update(tripInDB);
    }

    public void testFind() {
        try{
            repository.find(null);
            fail();
        }catch (IllegalArgumentException e){
            assertTrue(true);
        }
        assertEquals(Optional.empty(), repository.find(124242L));
        Optional<Trip> trip = repository.find(2L);
        assertTrue(trip.isPresent());
        assertEquals(2L, (long)trip.get().getId());
        assertEquals("Cairo",trip.get().getDestination());
        assertEquals("A .SRL",trip.get().getTransportFirm());
        assertEquals(LocalDateTime.of(2021,10,19,19,10,0),trip.get().getDepartureTime());
        assertEquals(250.0, trip.get().getPrice());
        assertEquals(50, trip.get().getSeats());
    }

    public void testFindAll() {
        List<Trip> all = StreamSupport.stream(repository.findAll().spliterator(), false).collect(Collectors.toList());
        assertEquals(4, all.size());
        assertTrue(all.stream().anyMatch(trip -> trip.equals(tripInDB)));
    }

    public void testFindTripsByDestinationTime() {
        List<Trip> list = StreamSupport.stream(repository
                .findTripsByDestinationTime("abc",
                LocalDateTime.of(2011,1,1,1,1),
                        LocalDateTime.of(2013,4,4,4,4))
                .spliterator(), false)
                .collect(Collectors.toList());
        assertTrue(list.isEmpty());
        list = StreamSupport.stream(repository.findTripsByDestinationTime("Cairo",
                LocalDateTime.of(2021,7,21,10,10,10),
                LocalDateTime.of(2023,8,8,1,1))
                .spliterator(), false)
                .collect(Collectors.toList());
        assertEquals(1, list.size());
        assertEquals(2L, (long)list.get(0).getId());
        list = StreamSupport.stream(repository
                .findTripsByDestinationTime("Cairo",
                LocalDateTime.of(2021,5,21,10,10,10),
                LocalDateTime.of(2021,11,19,12,12,12))
                .spliterator(), false)
                .collect(Collectors.toList());
        assertEquals(2, list.size());
    }

    private void alterSequence(){
        try(Connection connection = utils.getConnection()){
            try(PreparedStatement statement = connection.prepareStatement("ALTER SEQUENCE trips_id_seq RESTART WITH 5;")){
                statement.execute();
            }
        }catch (SQLException e){
            System.out.println("Error "+e);
        }
    }

    private void restoreReservations(){
        try(Connection connection = utils.getConnection()){
            try(PreparedStatement statement = connection.prepareStatement(
                    "DELETE FROM reservations; ALTER SEQUENCE reservations_id_seq RESTART WITH 1; " +
                            "insert into Reservations(client, phoneNumber, tickets, tripId, userId) VALUES " +
                            "('Anne','072',2,1,1),('Bob','032',4,1,2),('John','098',2,2,1),('Anne','072',4,3,2),('Boris','080',4,2,2);"
            )){
                statement.execute();
            }
        }catch (SQLException e){
            System.out.println("Error "+e);
        }
    }
}