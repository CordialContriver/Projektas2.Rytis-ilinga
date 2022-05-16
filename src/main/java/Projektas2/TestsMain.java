package Projektas2;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Scanner;

public class TestsMain {
    private static final String userFilePath = "C:\\Code_academy\\PaskaitosJAVA\\Projektas2\\src\\main\\java\\Projektas2\\UserDB\\Users.json";

    public static void main(String[] args) throws IOException {
        Scanner sc = new Scanner(System.in);
        TestsMain tm = new TestsMain();
        new TestDB("src/main/java/Tests/");

        tm.loginMenu(sc);
    }

    private void loginMenu(Scanner sc) {
        UserDB userDB = new UserDB(userFilePath);
        boolean repeat = true;
        do {
            System.out.println("[1] Prisijungti\n[2] Registruotis\n[3] Baigti darbą");
            switch (sc.nextLine()) {
                case "1" -> userDB.loginUser(sc);
                case "2" -> userDB.registerUser(sc);
                case "3" -> repeat = false;
                default -> System.out.println("Tokio pasirinkimo nėra");
            }
        } while (repeat);
        sc.close();
        System.out.println("Viso gero");
    }


    public static String stringNotEmpty(String s, Scanner sc) {
        System.out.println(s);
        while (true) {
            String input = sc.nextLine();
            if (!input.equals("")) {
                return input;
            } else {
                System.out.println("Nieko neįvedėte. Pakartokite.");
            }
        }
    }

    public static int intNotEmpty(String s, Scanner sc) {
        System.out.println(s);
        while (true) {
            try {
                return Integer.parseInt(sc.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Įveskite sveikąjį skaičių.");
            }
        }
    }

    public static boolean booleanTN(String s, Scanner sc) {
        System.out.println(s+" [T]/[N]");
        while (true) {
            switch (sc.nextLine().trim().toUpperCase()) {
                case "T" -> {
                    return true;
                }
                case "N" -> {
                    return false;
                }
                default -> System.out.println("Tokio pasirinkimo nėra");
            }
        }
    }




}
