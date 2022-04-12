package logicClasses;

public class Score {

    // -------- ATTRIBUTES ------- //
    private int idScore;
    private int score;
    private User user;

    // ------- CONSTRUCTOR ------- //

    public Score(User user, int score) {
        this.user = user;
        this.score = score;
    }

    // ------ GETTERS & SETTERS ----- //

    public int getIdScore() {
        return idScore;
    }

    public void setIdScore(int idScore) {
        this.idScore = idScore;
    }

    public User getUser() {
        return this.user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    // ---- METHODS ----- //

    @Override
    public String toString() {
        return "Score{" +
                "idScore=" + idScore +
                ", User=" + user.toString() +
                ", score=" + score +
                '}';
    }
}
