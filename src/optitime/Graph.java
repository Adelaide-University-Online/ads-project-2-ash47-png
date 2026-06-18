/**
 * File: Graph.java
 * Student ID: kamay021
 */
package optitime;

import java.util.*;

public class Graph {
    private final Map<String, Course> courses;

    public Graph() {
        this.courses = new HashMap<>();
    }

    public void addCourse(String courseCode) {
        courses.putIfAbsent(courseCode, new Course(courseCode));
    }

    public void addPrerequisite(String courseCode, String prerequisiteCode) {
        Course course = courses.get(courseCode);
        Course prerequisite = courses.get(prerequisiteCode);
        if (course != null && prerequisite != null) {
            course.addPrerequisite(prerequisite);
            prerequisite.addDependent(course);
        }
    }

    public Course getCourse(String courseCode) {
        return courses.get(courseCode);
    }

    public Collection<Course> getAllCourses() {
        return courses.values();
    }

    public boolean hasCourse(String courseCode) {
        return courses.containsKey(courseCode);
    }

    public boolean hasCycle() {
        Set<Course> visited = new HashSet<>();
        Set<Course> recursionStack = new HashSet<>();

        for (Course course : courses.values()) {
            if (!visited.contains(course)) {
                if (hasCycleDFS(course, visited, recursionStack)) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean hasCycleDFS(Course course, Set<Course> visited, Set<Course> recursionStack) {
        visited.add(course);
        recursionStack.add(course);

        for (Course dependent : course.getDependents()) {
            if (!visited.contains(dependent)) {
                if (hasCycleDFS(dependent, visited, recursionStack)) {
                    return true;
                }
            } else if (recursionStack.contains(dependent)) {
                return true;
            }
        }

        recursionStack.remove(course);
        return false;
    }
}