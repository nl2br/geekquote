package fr.mds.geekquote.helper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseOpenHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "quote.db";
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_TABLE = "quote";

    public static final String COLUMN_ID = "id";
    public static final String COLUMN_STR_QUOTE = "strQuote";
    public static final String COLUMN_RATING = "rating";
    public static final String COLUMN_DATE = "date";

    public static final String CREATE_DB = "CREATE TABLE " + DATABASE_TABLE + " (" +
    COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
    COLUMN_STR_QUOTE + " TEXT NOT NULL, " +
    COLUMN_RATING + " INTEGER DEFAULT 0, " +
    COLUMN_DATE + " INTEGER NOT NULL);";

    public DatabaseOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_DB);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String sql = "DROP TABLE IF EXISTS " + DATABASE_TABLE ;
        // drop
        db.execSQL(sql);
        // create
        onCreate(db);
    }
}
