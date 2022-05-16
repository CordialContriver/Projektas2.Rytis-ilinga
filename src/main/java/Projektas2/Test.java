package Projektas2;

import java.io.Serializable;
import java.util.HashMap;

public class Test  implements Serializable {
    private String testID;
    private HashMap<Integer, Question> questions;
    private boolean randomQuestions;
    private int numberOfQuestions;
    private double passGrade;

    public Test() {
    }

    public Test(String testID, HashMap<Integer, Question> questions,
                boolean randomQuestions, int numberOfQuestions, double passGrade) {
        this.testID = testID;
        this.questions = questions;
        this.randomQuestions = randomQuestions;
        this.numberOfQuestions = numberOfQuestions;
        this.passGrade = passGrade;
    }

    public String getTestID() {
        return testID;
    }

    public void setTestID(String testID) {
        this.testID = testID;
    }

    public HashMap<Integer, Question> getQuestions() {
        return questions;
    }

    public void setQuestions(HashMap<Integer, Question> questions) {
        this.questions = questions;
    }

    public boolean isRandomQuestions() {
        return randomQuestions;
    }

    public void setRandomQuestions(boolean randomQuestions) {
        this.randomQuestions = randomQuestions;
    }

    public int getNumberOfQuestions() {
        return numberOfQuestions;
    }

    public void setNumberOfQuestions(int numberOfQuestions) {
        this.numberOfQuestions = numberOfQuestions;
    }

    public double getPassGrade() {
        return passGrade;
    }

    public void setPassGrade(double passGrade) {
        this.passGrade = passGrade;
    }


}
