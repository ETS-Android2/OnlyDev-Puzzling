package logicClasses;

import com.example.myfirstapp.ScoreLayout;

public class ScoreFactory {
    public static Score createScore(User user, int score) {
        return new Score(user, score);
    }

}
