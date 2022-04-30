package logicClasses;

public class Score {

    // -------- ATTRIBUTES ------- //
    private int idScore;
    private int score;
    private User user;
    private int level;

    // ------- CONSTRUCTOR ------- //

    public Score(User user, int score, int level) {
        this.user = user;
        this.score = score;
        this.level = level;
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

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    // ---- METHODS ----- //

    @Override
    public String toString() {
        return "Score{" +
                "idScore=" + idScore +
                ", User=" + user.toString() +
                ", score=" + score +
                ", level=" + level +
                '}';
    }
}
