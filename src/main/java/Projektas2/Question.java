package Projektas2;

import java.io.Serializable;
import java.util.List;

public class Question implements Serializable {
    private String questionText;
    private List<String> answerVariants;
    private int correctAnswer;

    public Question(String questionText, List<String> answerVariants, int correctAnswer) {
        this.questionText = questionText;
        this.answerVariants = answerVariants;
        this.correctAnswer = correctAnswer;
    }

    public Question() {
    }

    public String getQuestionText() {
        return questionText;
    }

    public void setQuestionText(String questionText) {
        this.questionText = questionText;
    }

    public List<String> getAnswerVariants() {
        return answerVariants;
    }

    public void setAnswerVariants(List<String> answerVariants) {
        this.answerVariants = answerVariants;
    }

    public int getCorrectAnswer() {
        return correctAnswer;
    }

    public void setCorrectAnswer(int correctAnswer) {
        this.correctAnswer = correctAnswer;
    }
}
