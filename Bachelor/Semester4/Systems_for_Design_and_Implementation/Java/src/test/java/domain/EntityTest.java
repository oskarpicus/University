package domain;

import junit.framework.TestCase;

public class EntityTest extends TestCase {

    public void testGetId() {
        Entity<Long> entity = new Entity<>(12L);
        assertEquals(12L, (long) entity.getId());
        Entity<String> entity1 = new Entity<>("abc");
        assertEquals("abc", entity1.getId());
    }

    public void testSetId() {
        Entity<Long> entity = new Entity<>(9L);
        entity.setId(10L);
        assertEquals(10L, (long)entity.getId());
        assertNotSame(9L, entity.getId());
    }
}