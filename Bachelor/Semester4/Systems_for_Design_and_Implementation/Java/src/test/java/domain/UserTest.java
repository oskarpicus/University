package domain;

import junit.framework.TestCase;

public class UserTest extends TestCase {

    public void testGetUsername() {
        User user = new User(2L, "anne","flowers");
        assertEquals("anne", user.getUsername());
    }

    public void testSetUsername() {
        User user = new User(2L, "anne","flowers");
        user.setUsername("anne2");
        assertEquals("anne2", user.getUsername());
        assertNotSame("anne", user.getUsername());
    }

    public void testGetPassword() {
        User user = new User(2L, "anne","flowers");
        assertEquals("flowers", user.getPassword());
    }

    public void testSetPassword() {
        User user = new User(2L, "anne","flowers");
        user.setPassword("cars");
        assertEquals("cars", user.getPassword());
    }
}