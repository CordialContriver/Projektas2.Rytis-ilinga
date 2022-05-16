package Projektas2;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import static Projektas2.TestDB.tests;
import static Projektas2.TestDB.testsAttempts;
import static Projektas2.UserDB.users;
import static com.fasterxml.jackson.core.JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES;


public class Serializer {

    public void printTestResultFile(String pickTestID) {
        String testSource = "";
        ObjectMapper om = new ObjectMapper();
        om.enable(SerializationFeature.INDENT_OUTPUT);
        om.registerModule(new JavaTimeModule());
        String testResultFilePath = testSource+"AlltestsAttempsts.json";
        File file = new File(testResultFilePath);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(file))) {
            bw.write(om.writeValueAsString(TestDB.testsAttempts.get(pickTestID)));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void printUserFile(String filePath) throws IOException {
        ObjectMapper om = new ObjectMapper();
        om.enable(SerializationFeature.INDENT_OUTPUT);
        om.registerModule(new JavaTimeModule());
        File file = new File(filePath);
        if (!file.exists()) {
            file.createNewFile();
        }
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(file))) {
            bw.write(om.writeValueAsString(users));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public HashMap<String, User> readUserFile(String filePath) throws IOException {
        ObjectMapper om = new ObjectMapper();
        om.enable(SerializationFeature.INDENT_OUTPUT);
        om.registerModule(new JavaTimeModule());
        File file = new File(filePath);
        HashMap<String, User> vartotojai = om.readValue(file, new TypeReference<>() {
        });
        return vartotojai;
    }

    public void printTestFile(String filePath) throws IOException {
        ObjectMapper om = new ObjectMapper();
        om.enable(SerializationFeature.INDENT_OUTPUT);
        om.registerModule(new JavaTimeModule());
        File file = new File(filePath+"/AllTest.json");
        if (!file.exists()) {
            file.createNewFile();
        }
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(file))) {
            bw.write(om.writeValueAsString(tests));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public HashMap<String, Test> readTestFile(String filePath) throws IOException {
        ObjectMapper om = new ObjectMapper();
        om.enable(ALLOW_UNQUOTED_FIELD_NAMES);
        om.enable(SerializationFeature.INDENT_OUTPUT);
        om.registerModule(new JavaTimeModule());
        File file = new File(filePath);
        HashMap<String, Test> testai = om.readValue(file, new TypeReference<>() {
        });
        return testai;
    }

    public void printTestAttemptFile(String filePath) throws IOException {
        ObjectMapper om = new ObjectMapper();
        om.enable(SerializationFeature.INDENT_OUTPUT);
        om.registerModule(new JavaTimeModule());
        File file = new File(filePath+"/AlltestsAttempts.json");
        if (!file.exists()) {
            file.createNewFile();
        }
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(file))) {
            bw.write(om.writeValueAsString(testsAttempts));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public HashMap<String, List<TestAttempt>> readTestAttemptFile(String filePath) throws IOException {
        ObjectMapper om = new ObjectMapper();
        om.enable(ALLOW_UNQUOTED_FIELD_NAMES);
        om.enable(SerializationFeature.INDENT_OUTPUT);
        om.registerModule(new JavaTimeModule());
        File file = new File(filePath);
        HashMap<String, List<TestAttempt>> bandymai = om.readValue(file, new TypeReference<>() {
        });
        return bandymai;
    }

}
