package Projektas2;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

import static Projektas2.TestDB.testSource;
import static Projektas2.TestsMain.*;

public class TeacherMenu {
    private User teacher;
    private Scanner sc;

    public TeacherMenu(User teacher, Scanner sc) {
        this.teacher = teacher;
        this.sc = sc;
        teacherMenu();
    }

    private void teacherMenu() {
        System.out.printf("Pedagogas: %s %s\n", teacher.getName(), teacher.getSurname());
        boolean repeat = true;
        do {
            System.out.println("""
                    [1] Peržiūrėti testo rezultatus
                    [2] Keisti testą
                    [3] Kurti naują testą
                    [4] Pakeisti slaptažodį
                    [5] Atsijungti
                    """);
            switch (sc.nextLine()) {
                case "1" -> viewTestScores(sc);
                case "2" -> editTest(sc);
                case "3" -> createTest(sc);
                //  case "4" -> changePassword(teacher, sc);
                case "5" -> repeat = false;
                default -> System.out.println("Tokio pasirinkimo nėra");
            }
        } while (repeat);
        System.out.println("Viso gero");
    }

    private void editTest(Scanner sc) {
    }

    private void viewTestScores(Scanner sc) {
        String pickTestID = TestDB.pickTest(sc).getTestID();

        float averageSum = 0;
        int studentNumber = TestDB.testsAttempts.get(pickTestID).size();

        System.out.printf("Testo %s rezultatai\n", pickTestID);
        for (TestAttempt ta : TestDB.testsAttempts.get(pickTestID)) {
            System.out.printf("Studentas: %s %s. Balas - %.0f, %s\n",
                    UserDB.users.get(ta.getStudentID()).getName(),
                    UserDB.users.get(ta.getStudentID()).getSurname(),
                    ta.getGrade(),
                    ta.isPassed() ? "Išlaikytas" : "Neišlaikytas");
            averageSum += ta.getGrade();
        }
        System.out.printf("Testą laikė %d studentai. Balų vidurkis - %.1f", studentNumber, averageSum / studentNumber);

        Serializer sr = new Serializer();
        sr.printTestResultFile(pickTestID);
    }


    private void createTest(Scanner sc) {
        String testID = stringNotEmpty("Įveskite testo pavadinimą/modulio nr.", sc);
        boolean randomQuestions = booleanTN("Ar klausimus sumaišyti?", sc);
        HashMap<Integer, Question> questions = addQuestions(sc);
        int numberOfQuestions = randomQuestions ? intNotEmpty("Kiek klausimų pateikti iš rinkinio?", sc) : questions.size();
        int passGrade = intNotEmpty("Kiek reikia teisingų klausimų, kad studentas išlaikytų testą?", sc);
        TestDB.tests.put(testID, new Test(testID, questions, randomQuestions, numberOfQuestions, passGrade));

        Serializer serializer = new Serializer();
        try {
            serializer.printTestFile(testSource);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    private HashMap<Integer, Question> addQuestions(Scanner sc) {
        HashMap<Integer, Question> questions = new HashMap<>();
        int qNR = 0;
        boolean kartoti;
        do {
            qNR++;
            String questionText = stringNotEmpty("Įveskite klausimą nr."+qNR, sc);
            List<String> answers = addAnswersToQuestion(sc);
            int trueAnswer = intNotEmpty("Kuris atsakymas teisingas?", sc);
            questions.put(qNR, new Question(questionText, answers, trueAnswer));
            kartoti = booleanTN("Įvesti dar vieną klausimą?", sc);
        } while (kartoti);
        return questions;
    }

    private List<String> addAnswersToQuestion(Scanner sc) {
        List<String> answers = new ArrayList<>();
        int aNR = 0;
        boolean kartoti = true;
        do {
            aNR++;
            System.out.println("Įveskite atsakymo variantą nr. "+aNR+" (\"\" - pabaigti)");
            String answerText = sc.nextLine();
            if (answerText == "") {
                kartoti = false;
            } else {

                answers.add("["+aNR+"] "+answerText);
            }
        } while (kartoti);
        return answers;
    }

}