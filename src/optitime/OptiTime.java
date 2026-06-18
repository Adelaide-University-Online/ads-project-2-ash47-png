/**
 * File: OptiTime.java
 * Student ID: kamay021
 */
package optitime;

import java.io.IOException;
import java.util.List;

public class OptiTime {
    public static void main(String[] args) {
        if (args.length != 2) {
            System.out.println("Usage: java optitime.OptiTime <filename> <maxConcurrent>");
            System.out.println("Example: java optitime.OptiTime data/XBIT.txt 2");
            System.exit(1);
        }

        String filename = args[0];
        int maxConcurrent = 0;

        try {
            maxConcurrent = Integer.parseInt(args[1]);
            if (maxConcurrent <= 0) {
                System.out.println("Error: maxConcurrent must be a positive integer");
                System.exit(1);
            }
        } catch (NumberFormatException e) {
            System.out.println("Error: maxConcurrent must be an integer");
            System.exit(1);
        }

        try {
            DegreePlanner planner = new DegreePlanner(filename, maxConcurrent);
            List<StudyPeriod> plan = planner.planDegree();

            System.out.println("Optimized Degree Plan:");
            System.out.println("======================");
            for (StudyPeriod period : plan) {
                System.out.println(period);
            }
            System.out.println("Total study periods: " + plan.size());
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        } catch (IllegalStateException | IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}