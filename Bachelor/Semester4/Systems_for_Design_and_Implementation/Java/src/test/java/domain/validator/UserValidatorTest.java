package domain.validator;

import domain.User;
import junit.framework.TestCase;

public class UserValidatorTest extends TestCase {

    private final UserValidator validator = new UserValidator();

    public void testValidate() {
        User user = new User(1L,"abc","pass");
        try{
            validator.validate(user);
            fail();
        }catch (ValidationException e){
            assertEquals("Invalid password\n", e.getMessage());
        }
        user.setPassword("password");
        validator.validate(user);
        try{
            validator.validate(new User(2L,"",""));
            fail();
        }catch (ValidationException e){
            assertEquals("Invalid username\nInvalid password\n", e.getMessage());
        }
    }
}