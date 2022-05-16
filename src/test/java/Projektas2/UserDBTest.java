package Projektas2;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;

import java.time.LocalDateTime;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class UserDBTest {
    @InjectMocks
    private UserDB userDB = new UserDB();
    private HashMap<String, User> testUsers=new HashMap<>();


    @Test
    void testIfSaltCreatedIfNameIsBlank(){
        String name = "";
        String surname = "test";
        boolean testFail = true;
        try {
            String salt = LocalDateTime.now().toString().substring(20)
                    +name.charAt(name.length()-1)
                    +surname.charAt(name.length()-1);
        }catch (IndexOutOfBoundsException e){
            testFail=false;
        }
        assertFalse(testFail);
    }

    @Test
    void testIfSaltCreatedIfSurnameIsBlank(){
        String name = "test";
        String surname = "";
        boolean testFail = true;
        try {
            String salt = LocalDateTime.now().toString().substring(20)
                    +name.charAt(name.length()-1)
                    +surname.charAt(name.length()-1);
        }catch (IndexOutOfBoundsException e){
            testFail=false;
        }
        assertFalse(testFail);
    }

    @Test
    void testIfUserCreatedIfPasswordIsBlank(){
        String password ="";

        boolean testFail = true;
        User newUser = new User("id","test",
                "test",password,"testSalt", UserType.STUDENT);
        assertTrue(newUser instanceof User);
    }




}