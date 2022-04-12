package logicClasses;

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
}



