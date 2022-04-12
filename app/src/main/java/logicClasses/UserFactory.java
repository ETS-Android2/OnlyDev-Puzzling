package logicClasses;

public class UserFactory {

    public static User createUser(String mail, String password) {
        return new User(mail, password);
    }
}
