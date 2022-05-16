package Projektas2;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

import static Projektas2.TestDB.testSource;
import static Projektas2.TestDB.testsAttempts;
import static Projektas2.TestsMain.intNotEmpty;

public class StudentMenu {
    private User studentas;
    private Scanner sc;

    public StudentMenu(User studentas, Scanner sc) {
        this.studentas = studentas;
        this.sc = sc;
        studentMenu();
    }

    private void studentMenu() {
        System.out.printf("Studentas: %s %s\n", studentas.getName(), studentas.getSurname());
        boolean repeat = true;
        do {
            System.out.println("""
                    [1] Rašyti testą
                    [2] Peržiūrėti savo rezultatus
                    [3] Pakeisti slaptažodį
                    [4] Atsijungti
                    """);
            switch (sc.nextLine()) {
                case "1" -> takeTest(sc);
                case "2" -> personalScores(sc);
                //case "3" -> changePassword(student, sc);
                case "4" -> repeat = false;
                default -> System.out.println("Tokio pasirinkimo nėra");
            }
        } while (repeat);
        System.out.println("Viso gero");
    }

    private void personalScores(Scanner sc) {
        for (List<TestAttempt> listTA : TestDB.testsAttempts.values()) {
            for (TestAttempt ta : listTA) {
                if (studentas.getId().equals(ta.getStudentID())) {
                    System.out.printf("Testas: %s. Įvertinimas: %f. %s.\n",
                            ta.getTestID(), ta.getGrade(), (ta.isPassed()) ? "Išlaikyta" : "Neišlaikyta");
                    if (!ta.isPassed()) {
                        LocalDateTime repeatTime = ta.getTestDateTime().plusHours(48);
                        System.out.printf("Perlaikymas galimas %s\n",
                                (repeatTime.isBefore(LocalDateTime.now())) ? "jau dabar" :
                                        "nuo "+repeatTime.format(DateTimeFormatter.ofPattern("yy-MM-dd: hh:mm")));
                    }
                }
            }
        }
    }

    private void takeTest(Scanner sc) {
        Test test = TestDB.pickTest(sc);
        boolean canTakeTest = canRepeatTest(studentas, test);
        if (canTakeTest) {
            List<String> studentAnswers = new ArrayList<>();
            int correctAnswers = 0;
            LocalDateTime timeLog = LocalDateTime.now();
            int[] questionNumbers = (test.isRandomQuestions()) ?
                    randomQuestionNum(test.getNumberOfQuestions()) : notRandQuestionNum(test.getNumberOfQuestions());

            for (int i : questionNumbers) {
                Question question = test.getQuestions().get(i);
                System.out.println(question.getQuestionText());
                System.out.println(question.getAnswerVariants());
                int studentAnswer = intNotEmpty("", sc);
                studentAnswers.add("Nr. "+i+": "+studentAnswer);
                if (studentAnswer == question.getCorrectAnswer()) {
                    correctAnswers++;
                }
            }
            double grade = 1d * correctAnswers / test.getNumberOfQuestions() * 10;
            boolean isPassed = grade >= test.getPassGrade();
            System.out.printf("Tesingi atsakymai: %d/%d\nĮvertinimas - %f\n%s\n\n",
                    correctAnswers, test.getNumberOfQuestions(), grade, (isPassed) ? "Testas išlaikytas" : "Testas neišlaikytas");

            TestDB.testsAttempts.computeIfAbsent(test.getTestID(), k -> new ArrayList<>());
            TestDB.testsAttempts.get(test.getTestID()).add(new TestAttempt(studentas.getId(), test.getTestID(), timeLog, studentAnswers, grade, isPassed));

            Serializer serializer = new Serializer();
            try {
                serializer.printTestAttemptFile(testSource);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    private boolean canRepeatTest(User studentas, Test test) {
        try {
            for (TestAttempt ta : testsAttempts.get(test.getTestID())) {
                if (studentas.getId().equals(ta.getStudentID())) {
                    if (ta.getTestDateTime().plusHours(48).isAfter(LocalDateTime.now())) {
                        System.out.printf("Perlaikymas bus galimas nuo %s\n",
                                ta.getTestDateTime().plusHours(48).format(DateTimeFormatter.ofPattern("yy-MM-dd: hh:mm")));
                        return false;
                    }
                }
            }
        }catch (NullPointerException e){
            return true;
        }
        return true;
    }

    private int[] notRandQuestionNum(int numberOfQuestions) {
        int[] intNotRand = new int[numberOfQuestions];
        for (int i = 0; i < numberOfQuestions; i++) {
            intNotRand[i] = i+1;
        }
        return intNotRand;
    }

    private int[] randomQuestionNum(int numberOfQuestions) {
        Random rand = new Random();
        int[] randomIntArray = new int[numberOfQuestions];

        HashSet<Integer> intRandom = new HashSet<>();
        while (intRandom.size() < numberOfQuestions) {
            intRandom.add(rand.nextInt(1, numberOfQuestions+1));
        }
        int i = 0;
        for (int rnd : intRandom) {
            randomIntArray[i] = rnd;
            i++;
        }
        return randomIntArray;
    }

}
