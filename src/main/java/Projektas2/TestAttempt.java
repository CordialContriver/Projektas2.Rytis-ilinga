package Projektas2;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

public class TestAttempt implements Serializable {
    private String studentID;
    private String testID;
    private LocalDateTime testDateTime;
    private List<String> studentAnswers;
    private double grade;
    private boolean isPassed;

    public TestAttempt() {
    }

    public TestAttempt(String studentID, String testID, LocalDateTime testDateTime,
                       List<String> studentAnswers, double grade, boolean isPassed) {
        this.studentID = studentID;
        this.testID = testID;
        this.testDateTime = testDateTime;
        this.studentAnswers = studentAnswers;
        this.grade = grade;
        this.isPassed = isPassed;
    }

    public String getStudentID() {
        return studentID;
    }

    public void setStudentID(String studentID) {
        this.studentID = studentID;
    }

    public String getTestID() {
        return testID;
    }

    public void setTestID(String testID) {
        this.testID = testID;
    }

    public LocalDateTime getTestDateTime() {
        return testDateTime;
    }

    public void setTestDateTime(LocalDateTime testDateTime) {
        this.testDateTime = testDateTime;
    }

    public List<String> getStudentAnswers() {
        return studentAnswers;
    }

    public void setStudentAnswers(List<String> studentAnswers) {
        this.studentAnswers = studentAnswers;
    }

    public double getGrade() {
        return grade;
    }

    public void setGrade(double grade) {
        this.grade = grade;
    }

    public boolean isPassed() {
        return isPassed;
    }

    public void setPassed(boolean passed) {
        isPassed = passed;
    }
}
