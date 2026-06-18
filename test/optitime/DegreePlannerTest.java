/**
 * File: DegreePlannerTest.java
 * Student ID: kamay021
 */
package optitime;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class DegreePlannerTest {

    @TempDir
    Path tempDir;

    private Path createFile(String content) throws IOException {
        Path file = tempDir.resolve("degree.txt");
        Files.writeString(file, content);
        return file;
    }

    @Test
    public void testLinearChain() throws IOException {
        Path file = createFile("A,B,C\nA,B\nB,C\n");
        DegreePlanner planner = new DegreePlanner(file.toString(), 1);
        List<StudyPeriod> plan = planner.planDegree();

        assertEquals(3, plan.size());
        assertEquals("C", plan.get(0).getCourses().get(0).getCourseCode());
        assertEquals("B", plan.get(1).getCourses().get(0).getCourseCode());
        assertEquals("A", plan.get(2).getCourses().get(0).getCourseCode());
    }

    @Test
    public void testConcurrentScheduling() throws IOException {
        Path file = createFile("A,B,C,D\nA,B\nB,C\n");
        DegreePlanner planner = new DegreePlanner(file.toString(), 2);
        List<StudyPeriod> plan = planner.planDegree();

        assertEquals(3, plan.size());
        assertEquals(2, plan.get(0).getCourses().size());
        assertEquals(1, plan.get(1).getCourses().size());
        assertEquals("B", plan.get(1).getCourses().get(0).getCourseCode());
        assertEquals(1, plan.get(2).getCourses().size());
        assertEquals("A", plan.get(2).getCourses().get(0).getCourseCode());
    }

    @Test
    public void testCycleThrowsException() throws IOException {
        Path file = createFile("A,B\nA,B\nB,A\n");
        DegreePlanner planner = new DegreePlanner(file.toString(), 1);
        Exception exception = assertThrows(IllegalStateException.class, planner::planDegree);
        assertTrue(exception.getMessage().contains("cycle"));
    }

    @Test
    public void testSingleCourse() throws IOException {
        Path file = createFile("COMP2026\n");
        DegreePlanner planner = new DegreePlanner(file.toString(), 1);
        List<StudyPeriod> plan = planner.planDegree();

        assertEquals(1, plan.size());
        assertEquals("COMP2026", plan.get(0).getCourses().get(0).getCourseCode());
    }

    @Test
    public void testInvalidConcurrentLimit() throws IOException {
        Path file = createFile("A\n");
        assertThrows(IllegalArgumentException.class, () -> {
            new DegreePlanner(file.toString(), 0);
        });
    }
}