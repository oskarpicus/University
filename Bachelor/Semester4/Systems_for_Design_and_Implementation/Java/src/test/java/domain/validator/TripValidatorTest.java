package domain.validator;

import domain.Trip;
import junit.framework.TestCase;

import java.time.LocalDateTime;

public class TripValidatorTest extends TestCase {

    public void testValidate() {
        TripValidator validator = new TripValidator();
        Trip trip = new Trip(1L, "Paris","Auto", LocalDateTime.MIN, 129, 20);
        validator.validate(trip);
        trip = new Trip(2L,"","",LocalDateTime.MAX,-42,-1);
        try{
            validator.validate(trip);
            fail();
        }catch (ValidationException e){
            assertEquals("Invalid destination\nInvalid transport firm\nInvalid price\nInvalid number of seats\n",e.getMessage());
        }
    }
}