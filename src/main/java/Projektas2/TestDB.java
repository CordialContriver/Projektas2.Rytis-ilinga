package Projektas2;

import java.io.IOException;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

public class TestDB{
    public static HashMap<String, Test> tests;
    public static HashMap<String, List<TestAttempt>> testsAttempts;
    public static String testSource;

    public TestDB(String testSource) {
        this.testSource = testSource;

        Serializer serializer = new Serializer();
        try {
            tests = serializer.readTestFile(""+testSource+"AllTest.json");
        } catch (IOException e) {
            tests = new HashMap<>();
            System.out.println("Nėra testų failo");
        }

        try {
            testsAttempts = serializer.readTestAttemptFile(""+testSource+"AlltestsAttempts.json");
        } catch (IOException e) {
            e.printStackTrace();
            testsAttempts = new HashMap<>();
            System.out.println("Nėra bandymų failo");
        }
    }

    public static Test pickTest(Scanner sc) {
        for (String testId : tests.keySet()) {
            System.out.println(testId);
        }
        do {
            System.out.println("Pasirinkite testą:");
            Test pickedTest = tests.get(sc.nextLine());
            if (pickedTest != null) {
                return pickedTest;
            }
        } while (true);
    }

}
