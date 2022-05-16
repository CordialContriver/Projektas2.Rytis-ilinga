package Projektas2;

import org.apache.commons.codec.digest.DigestUtils;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Scanner;

import static Projektas2.TestsMain.stringNotEmpty;

public class User implements Serializable {
    private String id;
    private String name;
    private String surname;
    private String password;
    private String passwordSalt;
    private UserType userType;

    public User(){
    }

    public User(String id, String name, String surname, String password, String passwordSalt, UserType userType) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.password = password;
        this.passwordSalt = passwordSalt;
        this.userType=userType;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPasswordSalt() {
        return passwordSalt;
    }

    public void setPasswordSalt(String passwordSalt) {
        this.passwordSalt = passwordSalt;
    }

    public UserType getUserType() {
        return userType;
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
    }



}