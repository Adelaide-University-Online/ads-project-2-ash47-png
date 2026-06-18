/**
 * File: DegreePlanner.java
  * Student ID: kamay021
 */
package optitime;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class DegreePlanner {
    private final Graph graph;
    private final int maxConcurrent;

    public DegreePlanner(String filename, int maxConcurrent) throws IOException {
        if (maxConcurrent <= 0) {
            throw new IllegalArgumentException("Concurrent course limit must be positive");
        }
        this.maxConcurrent = maxConcurrent;
        this.graph = buildGraph(filename);
    }

    private Graph buildGraph(String filename) throws IOException {
        Graph graph = new Graph();

        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line = reader.readLine();
            if (line == null || line.trim().isEmpty()) {
                throw new IOException("File is empty or missing course list");
            }

            String[] courseCodes = line.split(",");
            for (String code : courseCodes) {
                graph.addCourse(code.trim());
            }

            while ((line = reader.readLine()) != null) {
                if (line.trim().isEmpty()) continue;
                String[] parts = line.split(",");
                String courseCode = parts[0].trim();

                for (int i = 1; i < parts.length; i++) {
                    String prereqCode = parts[i].trim();
                    graph.addPrerequisite(courseCode, prereqCode);
                }
            }
        }

        return graph;
    }

    public List<StudyPeriod> planDegree() {
        if (graph.hasCycle()) {
            throw new IllegalStateException("Cannot plan degree: prerequisite cycle detected");
        }

        Map<Course, Integer> inDegree = new HashMap<>();
        for (Course c : graph.getAllCourses()) {
            inDegree.put(c, c.getInDegree());
        }

        List<StudyPeriod> plan = new ArrayList<>();
        Set<Course> scheduled = new HashSet<>();
        int totalCourses = graph.getAllCourses().size();

        while (scheduled.size() < totalCourses) {
            List<Course> available = new ArrayList<>();

            for (Course c : graph.getAllCourses()) {
                if (!scheduled.contains(c) && inDegree.get(c) == 0) {
                    available.add(c);
                }
            }

            if (available.isEmpty()) {
                throw new IllegalStateException("No courses available but courses remain");
            }

            available.sort(Comparator.comparing(Course::getCourseCode));

            StudyPeriod period = new StudyPeriod(plan.size() + 1);
            int limit = Math.min(maxConcurrent, available.size());

            for (int i = 0; i < limit; i++) {
                Course course = available.get(i);
                period.addCourse(course);
                scheduled.add(course);

                for (Course dependent : course.getDependents()) {
                    inDegree.put(dependent, inDegree.get(dependent) - 1);
                }
            }

            plan.add(period);
        }

        return plan;
    }

    public Graph getGraph() {
        return graph;
    }
}