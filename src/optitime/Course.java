/**
 * File: Course.java
 * Student ID: kamay021
 */
package optitime;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Course {
    private final String courseCode;
    private final List<Course> prerequisites;
    private final List<Course> dependents;

    public Course(String courseCode) {
        this.courseCode = courseCode;
        this.prerequisites = new ArrayList<>();
        this.dependents = new ArrayList<>();
    }

    // Accessor methods
    public String getCourseCode() {
        return courseCode;
    }

    public List<Course> getPrerequisites() {
        return new ArrayList<>(prerequisites);
    }

    public List<Course> getDependents() {
        return new ArrayList<>(dependents);
    }

    public int getInDegree() {
        return prerequisites.size();
    }

    // Mutator methods
    public void addPrerequisite(Course prerequisite) {
        if (prerequisite != null && !prerequisites.contains(prerequisite)) {
            prerequisites.add(prerequisite);
        }
    }

    public void addDependent(Course dependent) {
        if (dependent != null && !dependents.contains(dependent)) {
            dependents.add(dependent);
        }
    }

    // Standard methods
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Course course = (Course) o;
        return Objects.equals(courseCode, course.courseCode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(courseCode);
    }

    @Override
    public String toString() {
        return courseCode;
    }
}