/**
 * File: CourseTest.java
 * Student ID: kamay021
 */
package optitime;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class CourseTest {

    @Test
    public void testCourseCreation() {
        Course c = new Course("COMP2026");
        assertEquals("COMP2026", c.getCourseCode());
        assertTrue(c.getPrerequisites().isEmpty());
        assertTrue(c.getDependents().isEmpty());
        assertEquals(0, c.getInDegree());
    }

    @Test
    public void testEqualsAndHashCode() {
        Course c1 = new Course("COMP2026");
        Course c2 = new Course("COMP2026");
        Course c3 = new Course("COMP2027");

        assertEquals(c1, c2);
        assertEquals(c1.hashCode(), c2.hashCode());
        assertNotEquals(c1, c3);
    }

    @Test
    public void testToString() {
        Course c = new Course("COMP2026");
        assertEquals("COMP2026", c.toString());
    }

    @Test
    public void testAddPrerequisite() {
        Course a = new Course("A");
        Course b = new Course("B");
        a.addPrerequisite(b);

        assertEquals(1, a.getPrerequisites().size());
        assertEquals(b, a.getPrerequisites().get(0));
        assertEquals(1, a.getInDegree());
    }

    @Test
    public void testDuplicatePrerequisiteNotAdded() {
        Course a = new Course("A");
        Course b = new Course("B");
        a.addPrerequisite(b);
        a.addPrerequisite(b);
        assertEquals(1, a.getInDegree());
    }
}