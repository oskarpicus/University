package domain.validator;

import domain.Reservation;
import domain.Trip;
import domain.User;
import junit.framework.TestCase;

import java.time.LocalDateTime;

public class ReservationValidatorTest extends TestCase {

    private final ReservationValidator validator = new ReservationValidator();

    public void testValidate() {
        Trip trip = new Trip(1L, "Paris","Auto", LocalDateTime.MIN, 129, 20);
        User user = new User(1L,"Anna","Apples");
        Reservation reservation;
        reservation = new Reservation(1L, "Anne","072",20, trip, user);
        validator.validate(reservation);
        reservation = new Reservation(2L,"","",-4, null, user);
        try{
            validator.validate(reservation);
            fail();
        }catch (ValidationException e){
            String fullMessage = "Invalid client name\nInvalid phone number\nInvalid number of tickets\nInvalid trip\n";
            assertEquals(fullMessage, e.getMessage());
        }
        reservation = new Reservation(3L, "n", "076", 90, trip, user);
        try{
            validator.validate(reservation);
            fail();
        }catch (ValidationException e){
            assertEquals("Invalid number of tickets\n", e.getMessage());
        }
    }
}