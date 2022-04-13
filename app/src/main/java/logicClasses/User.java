package logicClasses;

import java.util.Objects;

public class User {

    // -------- ATTRIBUTES ------- //
    private String mail;
    private String password;
    private int idUser;

    // ------- CONSTRUCTOR ------- //
    public User(String mail, String password) {
        this.mail = mail;
        this.password = password;
    }

    // Empty constructor if needed
    public User(){
        this.mail = "";
        this.password = "";
    }

    // ------ GETTERS & SETTERS ----- //
    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getIdUser() {
        return idUser;
    }

    // ---- METHODS ----- //
    @Override
    public String toString() {
        return "User{" +
                "mail='" + mail + '\'' +
                ", password='" + password + '\'' +
                ", idUser=" + idUser +
                '}';
    }

    public boolean isEmpty(){
        if (this.mail.isEmpty() & this.password.isEmpty()){
            return true;
        }
        return false;
    }

    public boolean equals(User userToCompare) {
        if (userToCompare.getMail().equals(this.mail) & userToCompare.getPassword().equals(this.password)){
            return true;
        }
        return false;
    }
}



