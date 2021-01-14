package rmit.aad.week5_maplocation;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import static android.content.ContentValues.TAG;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "Assignment.DB";

    public static final String TABLE_USER = "Users";
    public static final String ID_USER = "_id";
    public static final String EMAIL = "email";
    public static final String PASSWORD = "password";

    public DatabaseHelper(@Nullable Context context) {
        super(context, DB_NAME, null, 1);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_USER_TABLE =
                "create table " + TABLE_USER + " (" +
                        ID_USER + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                        EMAIL + " TEXT NOT NULL," +
                        PASSWORD + " TEXT NOT NULL);";

        db.execSQL(CREATE_USER_TABLE);

    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);

        onCreate(db);
    }

    public Boolean insertData(String EMAIL,String PASSWORD){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("email", EMAIL);
        contentValues.put("password", PASSWORD);
        long result = db.insert("Users",null,contentValues);
        if (result == -1) return false;
        else
            return true;
    }

    public boolean checkemail(String EMAIL){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("Select * from Users where EMAIL = ?", new String[]{EMAIL});
        if (cursor.getCount()>0){
            return true;
        }else {
            return false;
        }
    }
    public boolean checkemailpassword (String EMAIL, String PASSWORD){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("Select * from Users where EMAIL = ? and PASSWORD = ?",new String[]{EMAIL,PASSWORD});
        if (cursor.getCount()>0)
            return true;
        else
            return false;
    }
}

