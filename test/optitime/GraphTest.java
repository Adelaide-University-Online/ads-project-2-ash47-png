/**
 * File: GraphTest.java
 * Student ID: kamay021
 */
package optitime;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class GraphTest {

    @Test
    public void testAddAndRetrieveCourse() {
        Graph g = new Graph();
        g.addCourse("COMP2026");
        assertTrue(g.hasCourse("COMP2026"));
        assertNotNull(g.getCourse("COMP2026"));
        assertFalse(g.hasCourse("COMP2027"));
    }

    @Test
    public void testPrerequisiteRelationship() {
        Graph g = new Graph();
        g.addCourse("A");
        g.addCourse("B");
        g.addPrerequisite("A", "B");

        Course a = g.getCourse("A");
        assertEquals(1, a.getPrerequisites().size());
        assertEquals("B", a.getPrerequisites().get(0).getCourseCode());

        Course b = g.getCourse("B");
        assertEquals(1, b.getDependents().size());
        assertEquals("A", b.getDependents().get(0).getCourseCode());
    }

    @Test
    public void testNoCycle() {
        Graph g = new Graph();
        g.addCourse("A");
        g.addCourse("B");
        g.addCourse("C");
        g.addPrerequisite("A", "B");
        g.addPrerequisite("B", "C");
        assertFalse(g.hasCycle());
    }

    @Test
    public void testSimpleCycle() {
        Graph g = new Graph();
        g.addCourse("A");
        g.addCourse("B");
        g.addPrerequisite("A", "B");
        g.addPrerequisite("B", "A");
        assertTrue(g.hasCycle());
    }

    @Test
    public void testIndirectCycle() {
        Graph g = new Graph();
        g.addCourse("A");
        g.addCourse("B");
        g.addCourse("C");
        g.addPrerequisite("A", "B");
        g.addPrerequisite("B", "C");
        g.addPrerequisite("C", "A");
        assertTrue(g.hasCycle());
    }
}