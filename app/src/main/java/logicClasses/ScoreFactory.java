package logicClasses;

import com.example.myfirstapp.ScoreLayout;

public class ScoreFactory {
    public static Score createScore(User user, int score, int level) {
        return new Score(user, score, level);
    }

}
