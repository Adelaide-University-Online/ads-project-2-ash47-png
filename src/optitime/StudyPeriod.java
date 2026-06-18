/**
 * File: StudyPeriod.java
 * Student ID: kamay021
 */
package optitime;

import java.util.ArrayList;
import java.util.List;

public class StudyPeriod {
    private final int periodNumber;
    private final List<Course> courses;

    public StudyPeriod(int periodNumber) {
        this.periodNumber = periodNumber;
        this.courses = new ArrayList<>();
    }

    public void addCourse(Course course) {
        courses.add(course);
    }

    public int getPeriodNumber() {
        return periodNumber;
    }

    public List<Course> getCourses() {
        return new ArrayList<>(courses);
    }

    @Override
    public String toString() {
        return "Study Period " + periodNumber + ": " + courses;
    }
}