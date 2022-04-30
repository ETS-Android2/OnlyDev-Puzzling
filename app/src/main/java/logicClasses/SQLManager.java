package logicClasses;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.util.ArrayList;

public class SQLManager extends SQLiteOpenHelper {
    /**
     * This class will manage all the connections and calls needed from and to the SQLite database
     * created in the device memory.
     * Includes queries in order to create and retrieve rows in both tables created in the database.
     */

    private static final String CREATE_USER = "CREATE TABLE User (idUser INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "mail TEXT NOT NULL UNIQUE, password TEXT NOT NULL)";
    private static final String CREATE_SCORE = "CREATE TABLE Score (idScore INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "score INTEGER, level INTEGER, idUser INTEGER, FOREIGN KEY (idUser) REFERENCES User(idUser))";
    private static final String SELECT_ALL_SCORES = "SELECT mail, score, level FROM Score INNER JOIN User " +
            "ON User.idUser = Score.idUser ORDER BY score ASC";
    private static final String SELECT_USER = "SELECT mail, password FROM User WHERE mail = ?";

    public SQLManager(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_USER);
        sqLiteDatabase.execSQL(CREATE_SCORE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public long createOneUser(User user){
        /*
          Adds one row in the table User
          @param: User object type
          @return: -1 in case the insert has not been successful. Helps in the error flow handler.
         */
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        //Setting the values for each column from the table
        contentValues.put("mail", user.getMail());
        contentValues.put("password", user.getPassword());
        return sqLiteDatabase.insert("user", null, contentValues);
    }

    public ArrayList<ArrayList<String>> selectBestScores(){
        /*
          Will retrieve the 10 best scores gotten in an ascending order
          @param: null
          @return: ArrayList of Score type objects
         */
        ArrayList<ArrayList<String>> scores = new ArrayList<>();
        ArrayList<String> scoresRow = new ArrayList<>();
        int columnIndex;
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        try {
            Cursor cursor = sqLiteDatabase.rawQuery(SELECT_ALL_SCORES, null);
            cursor.moveToFirst();
            for (int rowPosition = 0; rowPosition < 10; rowPosition ++) {
                columnIndex = cursor.getColumnIndex("mail");
                scoresRow.add(cursor.getString(columnIndex));
                columnIndex = cursor.getColumnIndex("score");
                scoresRow.add(String.valueOf(cursor.getInt(columnIndex)));
                columnIndex = cursor.getColumnIndex("level");
                scoresRow.add(String.valueOf(cursor.getInt(columnIndex)));
                scores.add(scoresRow);
                scoresRow.clear(); //Empty auxiliar variable
                cursor.moveToNext(); //Getting next row from table
            }
            cursor.close();
        } catch (Exception e) {
            scoresRow.add("There is no scores to show. Try again later.");
            scores.add(scoresRow);
        }
        return scores;
    }

    public User retrieveUser(String mail){
        /*
          Will select the row where the mail corresponds, returning the mail and password in
          an array
          @param: null
          @return: user object. Attributes empty if user does not exist.
         */
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        User user = new User();
        int columnIndex;
        try{
            Cursor cursor = sqLiteDatabase.rawQuery(SELECT_USER, new String[] {mail});
            cursor.moveToFirst();
            columnIndex = cursor.getColumnIndex("mail");
            user.setMail(cursor.getString(columnIndex));
            columnIndex = cursor.getColumnIndex("password");
            user.setPassword(cursor.getString(columnIndex));
            cursor.close();
        } catch (Exception e){
            //Do nothing
        }
        return user;
    }

    public long createScore(Score score){
        /*
          Adds one row in the table Score
          @param score: Score object type
          @return: -1 in case the insert has not been successful. Helps in the error flow handler.
         */
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        //Setting the values for each column from the table
        contentValues.put("score", score.getScore());
        contentValues.put("level", score.getLevel());
        contentValues.put("idUser", score.getUser().getIdUser());
        return sqLiteDatabase.insert("Score", null, contentValues);
    }
}
