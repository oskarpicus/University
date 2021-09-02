package repository;

import domain.User;
import domain.validator.UserValidator;
import domain.validator.ValidationException;
import junit.framework.TestCase;
import utils.JdbcUtils;

import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.Properties;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class UserDBRepositoryTest extends TestCase {

    private static final UserDBRepository repository;
    private static final JdbcUtils utils;
    private final User userToSave = new User(4L,"Anne","Apples");
    private final User userToSaveBack = new User(2L,"Andrew","Right");

    static {
        Properties properties = new Properties();
        try{
            properties.load(new FileReader("jdbcTest.properties"));
        }catch (IOException e){
            System.out.println("Error File Properties");
        }
        utils = new JdbcUtils(properties);
        repository = new UserDBRepository(utils,new UserValidator());
    }

    public void testSave() {
        try{
            repository.save(null);
            fail();
        }catch (IllegalArgumentException e){
            assertTrue(true);
        }
        try{
            repository.save(new User(10L,"",""));
            fail();
        }catch (ValidationException e){
            assertTrue(true);
        }
        assertEquals(Optional.empty(), repository.save(userToSave));

        //post
        repository.delete(userToSave.getId());
        alterSequence();
    }

    public void testDelete() {
        try{
            repository.delete(null);
            fail();
        }catch (IllegalArgumentException e){
            assertTrue(true);
        }
        assertEquals(Optional.empty(), repository.delete(129L));
        long userToDelete = 2L;
        assertNotSame(Optional.empty(), repository.delete(userToDelete));

        //post
        repository.save(userToSaveBack);
        try(Connection connection = utils.getConnection()){
            try(PreparedStatement statement = connection.prepareStatement("UPDATE users SET id=? WHERE id=4;")){
                statement.setLong(1, userToDelete);
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
            repository.update(new User(49L,"",""));
            fail();
        }catch (ValidationException e){
            assertTrue(true);
        }
        assertEquals(Optional.empty(),repository.update(new User(2L,"Andrei","Password")));
        Optional<User> user = repository.find(2L);
        assertTrue(user.isPresent());
        assertEquals("Andrei",user.get().getUsername());
        assertEquals("Password",user.get().getPassword());

        //post
        repository.update(new User(2L,"Andrew","Right"));
    }

    public void testFind() {
        try{
            repository.find(null);
            fail();
        }catch (IllegalArgumentException e){
            assertTrue(true);
        }
        assertEquals(Optional.empty(), repository.find(1242L));
        Optional<User> result = repository.find(2L);
        assertTrue(result.isPresent());
        assertEquals(2L, (long)result.get().getId());
        assertEquals("Andrew",result.get().getUsername());
        assertEquals("Right", result.get().getPassword());
    }

    public void testFindAll() {
        List<User> all = StreamSupport.stream(repository.findAll().spliterator(),false).collect(Collectors.toList());
        assertEquals(3, all.size());
        assertTrue(all.stream().anyMatch(user -> user.getPassword().equals("Right") && user.getUsername().equals("Andrew")));
    }

    public void testFindByUsernamePassword() {
        assertEquals(Optional.empty(),repository.findByUsernamePassword("abc","dfe"));
        Optional<User> result = repository.findByUsernamePassword("Andrew","Right");
        assertTrue(result.isPresent());
        assertEquals("Andrew", result.get().getUsername());
        assertEquals("Right", result.get().getPassword());
    }

    private void alterSequence(){
        try(Connection connection = utils.getConnection()){
            try(PreparedStatement statement = connection.prepareStatement("ALTER SEQUENCE users_id_seq RESTART WITH 4;")){
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