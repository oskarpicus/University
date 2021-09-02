package repository;

import domain.Reservation;
import domain.Trip;
import domain.User;
import domain.validator.ReservationValidator;
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

public class ReservationDBRepositoryTest extends TestCase {

    private static final ReservationDBRepository repository;
    private static final JdbcUtils utils;
    private final Trip trip = new Trip(2L,"Cairo","A .SRL",
            LocalDateTime.of(2021,10,19,19,10,0),250,50);
    private final User user = new User(1L,"Mary","Poppins");
    private final Reservation reservationToSave = new Reservation(6L,"Steve","0192",4,trip,user);
    private final Reservation reservationInDB = new Reservation(3L,"John","098",2,trip,user);

    static {
        Properties properties = new Properties();
        try{
            properties.load(new FileReader("jdbcTest.properties"));
        }catch (IOException e){
            System.out.println("Error properties "+e);
        }
        utils = new JdbcUtils(properties);
        repository = new ReservationDBRepository(utils,new ReservationValidator());
    }

    public void testSave() {
        try{
            repository.save(null);
            fail();
        }catch (IllegalArgumentException e){
            assertTrue(true);
        }
        try{
            repository.save(new Reservation(999L,"","",-4,null,null));
            fail();
        }catch (ValidationException e){
            assertTrue(true);
        }
        assertEquals(Optional.empty(), repository.save(reservationToSave));
        Optional<Reservation> result = repository.find(reservationToSave.getId());
        assertTrue(result.isPresent());
        assertEquals(reservationToSave, result.get());

        // post
        repository.delete(reservationToSave.getId());
        alterSequence();
    }

    public void testDelete() {
        try{
            repository.delete(null);
            fail();
        }catch (IllegalArgumentException e){
            assertTrue(true);
        }
        assertEquals(Optional.of(reservationInDB), repository.delete(reservationInDB.getId()));
        assertEquals(Optional.empty(), repository.find(reservationInDB.getId()));

        // post
        repository.save(reservationInDB);
        try(Connection connection = utils.getConnection()){
            try(PreparedStatement statement = connection.prepareStatement("UPDATE reservations SET id=? WHERE id=?;")){
                statement.setLong(1, reservationInDB.getId());
                statement.setLong(2, 6L);
            }
        }catch (SQLException e){
            System.out.println("Error "+e);
        }
        alterSequence();
    }

    public void testUpdate() {
        try{
            repository.update(null);
            fail();
        }catch (IllegalArgumentException e){
            assertTrue(true);
        }
        try{
            repository.update(new Reservation(999L,"","",-4,null,null));
            fail();
        }catch (ValidationException e){
            assertTrue(true);
        }
        Optional<Reservation> reservation = repository.find(5L);
        assertTrue(reservation.isPresent());
        reservation.get().setClient("Edward");
        reservation.get().setTickets(1);
        assertEquals(Optional.empty(), repository.update(reservation.get()));

        reservation = repository.find(5L);
        assertTrue(reservation.isPresent());
        assertEquals("Edward", reservation.get().getClient());
        assertEquals(1, reservation.get().getTickets());
        assertEquals("080", reservation.get().getPhoneNumber());

        // post
        reservation.get().setClient("Boris");
        reservation.get().setTickets(4);
        repository.update(reservation.get());
    }

    public void testFind() {
        try{
            repository.find(null);
            fail();
        }catch (IllegalArgumentException e){
            assertTrue(true);
        }
        assertTrue(repository.find(12942942L).isEmpty());
        Optional<Reservation> reservation = repository.find(3L);
        assertTrue(reservation.isPresent());
        assertEquals("John", reservation.get().getClient());
        assertEquals("098", reservation.get().getPhoneNumber());
        assertEquals(2, reservation.get().getTickets());
        assertEquals(trip, reservation.get().getTrip());
        assertEquals(user, reservation.get().getUser());
    }

    public void testFindAll() {
        List<Reservation> all = StreamSupport.stream(repository.findAll().spliterator(),false).collect(Collectors.toList());
        assertEquals(5, all.size());
        assertTrue(all.stream().anyMatch(reservation -> reservation.getClient().equals("Anne") && reservation.getPhoneNumber().equals("072")));
    }

    private void alterSequence(){
        try(Connection connection = utils.getConnection()){
            try(PreparedStatement statement = connection.prepareStatement("ALTER SEQUENCE reservations_id_seq RESTART WITH 6;")){
                statement.execute();
            }
        }catch (SQLException e){
            System.out.println("Error "+e);
        }
    }
}