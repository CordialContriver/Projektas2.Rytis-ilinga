package Projektas2;

import org.apache.commons.codec.digest.DigestUtils;

import java.io.IOException;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

import static Projektas2.TestDB.testsAttempts;
import static Projektas2.TestsMain.*;
import static Projektas2.TestsMain.intNotEmpty;

public class UserDB <userFilePath>{
    public static HashMap<String, User> users = new HashMap<>();
    private String userFilePath;

    public UserDB() {
    }

    public UserDB(String userFilePath) {
        this.userFilePath=userFilePath;
        Serializer serializer = new Serializer();
        try {
            users = serializer.readUserFile(userFilePath);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Nerastas vartotojų failas.");
        }
    }

    private boolean checkLogin(String loginID, String loginPass) {
        try {
            User user = users.get(loginID);
            String hashLoginPass = DigestUtils.sha1Hex(loginPass+user.getPasswordSalt());
            return hashLoginPass.equals(user.getPassword());
        }catch (NullPointerException e){
            return false;
        }

    }

    public void loginUser(Scanner sc) {
        System.out.print("Įveskite ID: ");
        String loginID = sc.nextLine();
        System.out.print("Slaptažodis:  ");
        String loginPass = sc.nextLine();
        if (checkLogin(loginID, loginPass)) {
            if (users.get(loginID).getUserType() == UserType.TEACHER) {
                new TeacherMenu(users.get(loginID),sc);
            } else {
                new StudentMenu(users.get(loginID),sc);
            }
        } else {
            System.out.println("Neteisingi prisijungimo duomenys.");
        }
    }

    public void registerUser(Scanner sc) {
        boolean isTeacher = booleanTN("Ar esate pedagogas?", sc);
        String name = stringNotEmpty("Įveskite vardą", sc);
        String surname = stringNotEmpty("Įveskite pavardę", sc);
        if (isUserNew(name, surname)) {
            String newUserID = (((isTeacher) ? "m" : "")+name.toLowerCase().charAt(0)
                    +surname.toLowerCase().charAt(0))+users.size();

            System.out.printf("Jūsų vartotojo ID: %s\n", newUserID);
            String salt = createSalt(name, surname);
            String password = null;
            while (password == null) {
                password = createPassword(salt, sc);
            }
            UserType userType = (isTeacher) ? UserType.TEACHER : UserType.STUDENT;

            users.put(newUserID, new User(newUserID, name, surname, password, salt, userType));
            Serializer serializer=new Serializer();
            try {
                serializer.printUserFile(userFilePath);
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.println("Sėkmingai priregistravome jus prie sistemos");
        } else {
            System.out.println("Toks vartotojas jau yra");
        }
    }

    private String createSalt(String name, String surname) {
        return LocalDateTime.now().toString().substring(20)
                +name.charAt(name.length()-1)
                +surname.charAt(surname.length()-1);
    }

    protected boolean isUserNew(String name, String surname) {
        if (users == null) {
            return true;
        }
        for (User user : users.values()) {
            if (user.getName().equals(name) && user.getSurname().equals(surname)) {
                System.out.println("Toks vartotojas jau yra");
                return false;
            }
        }
        return true;
    }

    private boolean testOldPassword(Scanner sc, String password, String passwordSalt) {
        System.out.println("Įveskite dabartinį slaptažodį");
        for (int i = 0; i < 5; i++) {
            String oldPassHash = DigestUtils.sha1Hex(sc.nextLine()+passwordSalt);
            if (oldPassHash.equals(password)) {
                return true;
            } else {
                System.out.println("Dabartinis slaptažodis neteisingas.");
            }
        }
        System.out.println("Nepavyko patvirtinti dabartinio slaptažodžio.");
        return false;
    }

    private String createPassword(String salt, Scanner sc) {
        String newPass = stringNotEmpty("Įveskite naują slaptažodį", sc);
        System.out.println("Pakartokite slaptažodį");
        boolean passRepeatSuccesful = false;
        for (int i = 0; i < 5; i++) {
            if (sc.nextLine().equals(newPass)) {
                passRepeatSuccesful = true;
                break;
            } else {
                System.out.println("Slaptažodžiai nesutampa, pakartokite.");
            }
        }
        if (passRepeatSuccesful) {
            return DigestUtils.sha1Hex(newPass+salt);
        } else {
            System.out.println("Nepavyko pakartoti slaptažodžio.");
            return null;
        }
    }

}
