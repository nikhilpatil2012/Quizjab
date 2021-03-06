package ideapot.controller;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class QuizzyDatabase extends SQLiteOpenHelper {

    public QuizzyDatabase(Context context, String name, CursorFactory factory,
                          int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String dataTable="create table QUIZDATA( "
                + "id"
                + " integer primary key autoincrement,"
                + "CATAGORY text ,CATAGORY_NAME text,CATAGORY_IMG_PATH text,CHAP_ID text not null UNIQUE, CHAP_NAME text not null UNIQUE, CHAP_IMG_PATH text); ";

        String scoreTable="create table SCOREDATA("
                + "id"
                + " integer primary key autoincrement,"
                + "CATAGORY text,CATAGORY_NAME text,CHAP_ID text not null UNIQUE,CHAP_NAME text,GLOBAL_RANK text,TOTAL_SCORE text,CORRECT_QUES text, COUNT text, WIN text,LOOSE text,KILL_TIME text); ";


        String friendsTable = "create table FRIENDSDATA("
                + "id"
                + " integer primary key autoincrement,"
                + "Uid text not null UNIQUE, Name text, Pic text not null UNIQUE); ";

        String friendsScoreTable = "create table FRIENDS_SCORE_DATA("
                +                 "id"
                +                 " integer primary key autoincrement,"
                +                 "USER_CODE text,CHAP_ID text,GLOBAL_RANK text,TOTAL_SCORE text,WIN text,LOOSE text,KILL_TIME text,TAG text); ";

        String user_friends_Table="create table USER_FRIENDS_TABLE("
                + "id"
                + " integer primary key autoincrement,"
                + "F_ID text not null UNIQUE,USER_CODE text); ";

        String user_logs_Table="create table USER_LOGS_TABLE("
                + "id"
                + " integer primary key autoincrement,"
                + "USER_LOGS text); ";


        db.execSQL(dataTable);
        db.execSQL(scoreTable);
        db.execSQL(friendsTable);
        db.execSQL(friendsScoreTable);
        db.execSQL(user_friends_Table);
        db.execSQL(user_logs_Table);

    }

    public void insertLogs(String log){

        ContentValues cv = new ContentValues();
        cv.put("USER_LOGS", log);


        try
        {
             getWritableDatabase().insertOrThrow("USER_LOGS_TABLE", null, cv);

        }catch (SQLiteConstraintException e)
        {
            Log.w("Database_Error", e.getMessage());
        }catch (Exception e){

            Log.w("Database_Error", e.getMessage());
        }

    }

    public  Cursor getUserLogs(){

        Cursor c = getWritableDatabase().rawQuery("select * from USER_LOGS_TABLE", null);

        return c;
    }

    public void insertUpdatesQUIZDATA(String CATAGORY,String CATAGORY_NAME,String CATAGORY_IMG_PATH,String CHAP_ID, String CHAP_NAME, String CHAP_IMG_PATH) {
        ContentValues cv = new ContentValues();
        cv.put("CATAGORY", CATAGORY);
        cv.put("CATAGORY_NAME", CATAGORY_NAME);
        cv.put("CATAGORY_IMG_PATH", CATAGORY_IMG_PATH);
        cv.put("CHAP_ID", CHAP_ID);
        cv.put("CHAP_NAME", CHAP_NAME);
        cv.put("CHAP_IMG_PATH", CHAP_IMG_PATH);

        try
        {
            getWritableDatabase().insertOrThrow("QUIZDATA", null, cv);
        }catch (SQLiteConstraintException e)
        {

        }catch (Exception e){

        }

    }

    public void insertUpdatesSCOREDATA(String CATAGORY,String CATAGORY_NAME,String CHAP_ID,String CHAP_NAME,String GLOBAL_RANK,String TOTAL_SCORE, String CORRECT_QUES, String COUNT) {
        ContentValues cv = new ContentValues();
        cv.put("CATAGORY", CATAGORY);
        cv.put("CATAGORY_NAME", CATAGORY_NAME);
        cv.put("CHAP_ID", CHAP_ID);
        cv.put("CHAP_NAME", CHAP_NAME);
        cv.put("GLOBAL_RANK", GLOBAL_RANK);
        cv.put("TOTAL_SCORE", TOTAL_SCORE);
        cv.put("CORRECT_QUES", CORRECT_QUES);
        cv.put("COUNT", COUNT);

        cv.put("WIN", COUNT);
        cv.put("LOOSE", COUNT);
        cv.put("KILL_TIME", COUNT);

        try
        {
            getWritableDatabase().insertOrThrow("SCOREDATA", null, cv);
        }catch (SQLiteConstraintException e)
        {

        }catch (Exception e)
        {

        }

    }

    public int insertFriends(String Id, String Name, String Pic){

        ContentValues cv = new ContentValues();
        cv.put("Uid", Id);
        cv.put("Name", Name);
        cv.put("Pic", Pic);

        try {

            getWritableDatabase().insertOrThrow("FRIENDSDATA", null, cv);
            return 1;

        }catch (SQLiteConstraintException ex){return 0;}
        catch (Exception ex){return 0;}

    }


    public String getUserNameAndWithFBId()
    {

        return null;
    }


    public boolean updateTotalScore(String CHAP_ID, String TOTAL_SCORE) {

        Cursor c = getDataSCOREDATA(CHAP_ID);
        c.moveToFirst();
        String rowId = c.getString(0);
        c.close();
        ContentValues args = new ContentValues();
        args.put("TOTAL_SCORE", TOTAL_SCORE);
        return getWritableDatabase().update("SCOREDATA", args, "id='"+rowId+"'", null) > 0;
    }

    public boolean updateGlobalRank(String CHAP_ID, String GLOBAL_RANK) {
        Cursor c = getDataSCOREDATA(CHAP_ID);
        c.moveToFirst();
        String rowId = c.getString(0);
        c.close();
        ContentValues args = new ContentValues();
        args.put("GLOBAL_RANK", GLOBAL_RANK);
        return getWritableDatabase().update("SCOREDATA", args, "id='"+rowId+"'", null) > 0;
    }


    public boolean updateFullScore(String CHAP_ID, String GLOBAL_RANK,String TOTAL_SCORE,String CORRECT_QUES,String COUNT) {
        Cursor c = getDataSCOREDATA( CHAP_ID);
        c.moveToFirst();
        String rowId = c.getString(0);
        c.close();
        ContentValues args = new ContentValues();
        args.put("GLOBAL_RANK", GLOBAL_RANK);
        args.put("TOTAL_SCORE", TOTAL_SCORE);
        args.put("CORRECT_QUES", CORRECT_QUES);
        args.put("COUNT", COUNT);
        return getWritableDatabase().update("SCOREDATA", args, "id='"+rowId+"'", null) > 0;
    }

    public boolean updateTotalCount(String CHAP_ID, String COUNT) {
        Cursor c = getDataSCOREDATA( CHAP_ID);
        c.moveToFirst();
        String rowId = c.getString(0);
        c.close();
        ContentValues args = new ContentValues();
        args.put("COUNT", COUNT);
        return getWritableDatabase().update("SCOREDATA", args, "id='"+rowId+"'", null) > 0;
    }

    public boolean updateTotalCorrect(String CHAP_ID, String CORRECT_QUES) {
        Cursor c = getDataSCOREDATA( CHAP_ID);
        c.moveToFirst();
        String rowId = c.getString(0);
        c.close();
        ContentValues args = new ContentValues();
        args.put("CORRECT_QUES", CORRECT_QUES);
        return getWritableDatabase().update("SCOREDATA", args, "id='"+rowId+"'", null) > 0;
    }

    public boolean updateWin(String CHAP_ID) {
        try
        {
            Cursor c = getDataSCOREDATA( CHAP_ID);
            c.moveToFirst();
            String rowId = c.getString(0);
            c.close();
            ContentValues args = new ContentValues();
            args.put("WIN", String.valueOf(Integer.valueOf(getWin(CHAP_ID))+1));
            return getWritableDatabase().update("SCOREDATA", args, "id='"+rowId+"'", null) > 0;
        }catch (Exception e){
            return false;
        }
    }

    public boolean updateLoose(String CHAP_ID) {
        try
        {
            Cursor c = getDataSCOREDATA( CHAP_ID);
            c.moveToFirst();
            String rowId = c.getString(0);
            c.close();
            ContentValues args = new ContentValues();
            args.put("LOOSE", String.valueOf(Integer.valueOf(getLoose(CHAP_ID))+1));
            return getWritableDatabase().update("SCOREDATA", args, "id='"+rowId+"'", null) > 0;
        }catch (Exception e){
            return false;
        }
    }

    public String getScoreTotalAllCatagory()
    {
        Cursor c = getWritableDatabase().query( "SCOREDATA", new String[]{"TOTAL_SCORE"}, null, null, null,null, null, null);
        int score = 0;
        if(c.moveToFirst())
        {
            do {
                score = score + Integer.valueOf(c.getString(0));
            }while (c.moveToNext());
        }
        c.close();
        return String.valueOf(score);
    }

    public String getTitleProfileAllCatagory()
    {
        Cursor c = getWritableDatabase().query( "SCOREDATA", new String[]{"TOTAL_SCORE"}, null, null, null,null, null, null);
        int score = 0;
        int c1 = 0;
        if(c.moveToFirst())
        {
            do {
                if(Integer.valueOf(c.getString(0))>0)
                {
                    c1++;
                }
                score = score + Integer.valueOf(c.getString(0));

            }while (c.moveToNext());
        }
        c.close();

        try{

            score = score/c1;

        }catch (ArithmeticException ex){}
        catch (Exception ex){}



        if(score<=400)
        {
            return "Newbee";
        }
        else if(score>400 && score <=800)
        {
            return "Hustler";
        }
        else if(score>800 && score <=1200)
        {
            return "Pro";
        }
        else if(score>1200 && score <=1600)
        {
            return "Avenger";
        }
        else
        {
            return "Genius";
        }
    }

    public String getWinTotalAllCatagory()
    {
        Cursor c = getWritableDatabase().query( "SCOREDATA", new String[]{"WIN"}, null, null, null,null, null, null);
        int score = 0;
        if(c.moveToFirst())
        {
            do {
                score = score + Integer.valueOf(c.getString(0));
            }while (c.moveToNext());
        }
        c.close();
        return String.valueOf(score);
    }
    public String getLooseTotalAllCatagory()
    {
        Cursor c = getWritableDatabase().query( "SCOREDATA", new String[]{"LOOSE"}, null, null, null,null, null, null);
        int score = 0;
        if(c.moveToFirst())
        {
            do {
                score = score + Integer.valueOf(c.getString(0));
            }while (c.moveToNext());
        }
        c.close();
        return String.valueOf(score);
    }

    public String getKillTimeTotalAllCatagory()
    {
        Cursor c = getWritableDatabase().query( "SCOREDATA", new String[]{"KILL_TIME"}, null, null, null,null, null, null);
        float score = 0;
        int c1 = 0;
        if(c.moveToFirst())
        {
            do {
                score = score + Float.valueOf(c.getString(0));
                if(Float.valueOf(c.getString(0))>0){c1++;}
            }while (c.moveToNext());
        }

        score =  (score/c1);
        c.close();

        if(String.valueOf(score).length()>4)
        {
            return String.valueOf(score).substring(0,4);
        }
        else
        {
            return String.valueOf(score);
        }
    }

    public String getTotalWinAllCatagory()
    {
        Cursor c = getWritableDatabase().query( "SCOREDATA", new String[]{"WIN"}, null, null, null,null, null, null);
        int score = 0;
        if(c.moveToFirst())
        {
            do {
                score = score + Integer.valueOf(c.getString(0));
            }while (c.moveToNext());
        }
        c.close();
        return String.valueOf(score);
    }

    public String getTotalLooseAllCatagory()
    {
        Cursor c = getWritableDatabase().query( "SCOREDATA", new String[]{"LOOSE"}, null, null, null,null, null, null);
        int score = 0;
        if(c.moveToFirst())
        {
            do {
                score = score + Integer.valueOf(c.getString(0));
            }while (c.moveToNext());
        }
        c.close();
        return String.valueOf(score);
    }

    /**
     * function to store data of friends, following parameters are required :
     *
     * @param USER_CODE user_code of user
     * @param CHAP_ID chapter id
     * @param GLOBAL_RANK global rank count
     * @param TOTAL_SCORE total score count
     * @param WIN win game count
     * @param LOOSE loose game count
     * @param KILL_TIME kill time of user
     * @param TAG tag of user like New bee ,Hustler ,Pro ,Avenger ,Genius
     *
     */
    public void insertDataOfFriend(String USER_CODE,String CHAP_ID,String GLOBAL_RANK,String TOTAL_SCORE,String WIN,String LOOSE,String KILL_TIME,String TAG)
    {
        Cursor c = getWritableDatabase().query("FRIENDS_SCORE_DATA", null, "USER_CODE=? AND CHAP_ID=?" ,new String[] {USER_CODE,CHAP_ID}, null, null, null, null);

        if(c.moveToFirst()) //record exhist
        {
            String rowId = c.getString(0);
            c.close();
            ContentValues args = new ContentValues();
            args.put("GLOBAL_RANK", GLOBAL_RANK);
            args.put("TOTAL_SCORE", TOTAL_SCORE);
            args.put("WIN", WIN);
            args.put("LOOSE", LOOSE);
            args.put("KILL_TIME", KILL_TIME);
            args.put("TAG", TAG);
            getWritableDatabase().update("FRIENDS_SCORE_DATA", args, "id='"+rowId+"'", null);
        }
        else
        {
            c.close();
            ContentValues cv = new ContentValues();
            cv.put("USER_CODE", USER_CODE);
            cv.put("CHAP_ID", CHAP_ID);
            cv.put("GLOBAL_RANK", GLOBAL_RANK);
            cv.put("TOTAL_SCORE", TOTAL_SCORE);
            cv.put("WIN", WIN);
            cv.put("LOOSE", LOOSE);
            cv.put("KILL_TIME", KILL_TIME);
            try
            {
                getWritableDatabase().insertOrThrow("FRIENDS_SCORE_DATA", null, cv);
            }catch (SQLiteConstraintException e)
            {

            }catch (Exception e){

            }

        }

    }


    public Cursor getDataSCOREDATA(String CHAP_ID) {
        Log.w("ChapterID", CHAP_ID);
        Cursor c = getWritableDatabase().query( "SCOREDATA", null,"CHAP_ID"+"=?", new String[] { CHAP_ID }, null,null, null, null);
        return c;
    }



    public String getGlobalRank(String CHAP_ID) {
        Cursor c = getDataSCOREDATA( CHAP_ID);
        c.moveToFirst();
        String rank = c.getString(c.getColumnIndex("GLOBAL_RANK"));
        c.close();
        return rank;
    }

    public String getTotalScore(String CHAP_ID) {
        Cursor c = getDataSCOREDATA( CHAP_ID);
        c.moveToFirst();
        String rank = c.getString(c.getColumnIndex("TOTAL_SCORE"));
        c.close();
        return rank;
    }

    public String getTotalCount(String CHAP_ID) {
        Cursor c = getDataSCOREDATA( CHAP_ID);
        c.moveToFirst();
        String rank = c.getString(c.getColumnIndex("COUNT"));
        c.close();
        return rank;
    }


    public Cursor getFriendsList() {

        Cursor c = getWritableDatabase().rawQuery("select * from FRIENDSDATA", null);

        return c;

    }


    public Cursor getUpdatesQUIZDATA() {

        Cursor c = getWritableDatabase().rawQuery("select * from QUIZDATA", null);

        return c;
    }


    public Cursor getListOfChaptersInCatagory(String CATAGORY_VAL)
    {
        return getWritableDatabase().query("QUIZDATA",new String[]{"CATAGORY","CATAGORY_NAME","CHAP_ID","CHAP_NAME"},"CATAGORY "+"=?",new String[]{CATAGORY_VAL},null,null,"CHAP_ID"+" DESC",null);
    }

    public Cursor getListOfChapters(){

        return getWritableDatabase().query("QUIZDATA",new String[]{"CATAGORY","CATAGORY_NAME"},null,null,null,null,"CATAGORY"+" DESC",null);
    }

    public Cursor getUpdatesSCOREDATA() {

        Cursor c = getWritableDatabase().rawQuery("select * from SCOREDATA", null);

        return c;
    }




    public String getTotalCorrect(String CHAP_ID) {
        Cursor c = getDataSCOREDATA( CHAP_ID);
        c.moveToFirst();
        String rank = c.getString(c.getColumnIndex("CORRECT_QUES"));
        c.close();
        return rank;
    }

    public boolean updateKillTime(String CHAP_ID, String KILL_TIME) {
        Cursor c = getDataSCOREDATA( CHAP_ID);
        c.moveToFirst();
        String rowId = c.getString(0);
        c.close();
        ContentValues args = new ContentValues();
        args.put("KILL_TIME", KILL_TIME);
        return getWritableDatabase().update("SCOREDATA", args, "id='"+rowId+"'", null) > 0;
    }


    public String getWin(String CHAP_ID) {
        Cursor c = getDataSCOREDATA( CHAP_ID);
        c.moveToFirst();
        String rank = c.getString(c.getColumnIndex("WIN"));
        c.close();
        return rank;
    }

    public String getLoose(String CHAP_ID) {
        Cursor c = getDataSCOREDATA( CHAP_ID);
        c.moveToFirst();
        String rank = c.getString(c.getColumnIndex("LOOSE"));
        c.close();
        return rank;
    }

    /**
     * Insert data into user-friends table
     *
     * @param F_ID  facebook id of friend
     * @param USER_CODE  user code of friend
     * @return 1 if insertion is OK else 0
     */
    public int insertUserFriendsTable(String F_ID, String USER_CODE) {
        try
        {
            ContentValues cv = new ContentValues();
            cv.put("F_ID", F_ID);
            cv.put("USER_CODE", USER_CODE);
            getWritableDatabase().insertOrThrow("USER_FRIENDS_TABLE", null, cv);
            return 1;
        } catch (SQLiteConstraintException e)
        {
            return 0;
        }catch (Exception e)
        {
            return 0;
        }
    }

    /**
     * Get data of all user code of friends in user-friend table
     *
     * @return Cursor of all friends user codes
     */

    public Cursor getUserCodesFriends()
    {
        return getWritableDatabase().query("USER_FRIENDS_TABLE", new String[]{"USER_CODE"}, null, null, null,null, null, null);
    }

    public Cursor getFBIdFriends()
    {
        return getWritableDatabase().query("USER_FRIENDS_TABLE", new String[]{"F_ID"}, null, null, null,null, null, null);
    }

    public String getUserCode(String F_ID) {
        Cursor c = getWritableDatabase().query( "USER_FRIENDS_TABLE", new String[]{"USER_CODE"},"F_ID"+"=?", new String[] { F_ID }, null,null, null, null);
        try
        {
            if(c.moveToFirst())
            {
                return c.getString(0);
            }
            else
            {
                return "null";
            }
        }finally {
            c.close();
        }
    }

    public String getFBIdWithUserCode(String USER_CODE) {
        Cursor c = getWritableDatabase().query( "USER_FRIENDS_TABLE", new String[]{"F_ID"},"USER_CODE"+"=?", new String[] { USER_CODE }, null,null, null, null);
        try
        {
            if(c.moveToFirst())
            {
                return c.getString(0);
            }
            else
            {
                return "null";
            }
        }finally {
            c.close();
        }
    }

    public String getNamedWithFbID(String FB_ID) {
        Cursor c = getWritableDatabase().query( "FRIENDSDATA", new String[]{"Name"},"Uid"+"=?", new String[] { FB_ID }, null,null, null, null);
        try
        {
            if(c.moveToFirst())
            {
                return c.getString(0);
            }
            else
            {
                return "null";
            }
        }finally {
            c.close();
        }
    }

    public String getTotalScoreFriendsScoreData(String USER_CODE,String CHAP_ID) {
        Cursor c = getWritableDatabase().query( "FRIENDS_SCORE_DATA", new String[]{"TOTAL_SCORE"},"USER_CODE=? AND CHAP_ID=?", new String[] { USER_CODE, CHAP_ID }, null,null, null, null);
        try
        {
            if(c.moveToFirst())
            {
                return c.getString(0);
            }
            else
            {
                return "";
            }
        }finally {
            c.close();
        }
    }
    public Cursor getFullRecordScoreFriendsScoreData(String USER_CODE,String CHAP_ID) {
        return getWritableDatabase().query( "FRIENDS_SCORE_DATA", null,"USER_CODE=? AND CHAP_ID=?", new String[] { USER_CODE, CHAP_ID }, null,null, null, null);
    }

    @Override
    public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {


    }

    // not null UNIQUE
}
