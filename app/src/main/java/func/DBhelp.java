package func;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Swati garg on 30-06-2015.
 */
public class DBhelp extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "Eventify.db";
    private static final String INT_TYPE = " INT";
    private static final String TEXT_TYPE = " TEXT";
    private static final String COMMA_SEP = ",";
    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + TableEntry.TABLE_NAME + " (" +
                    TableEntry.COLUMN_NAME_ID + TEXT_TYPE + "PRIMARY KEY"+ COMMA_SEP +
                    TableEntry.COLUMN_NAME + TEXT_TYPE + COMMA_SEP +
                    TableEntry.COLUMN_NAME_TIME + TEXT_TYPE + COMMA_SEP +
                    TableEntry.COLUMN_NAME_DESC + TEXT_TYPE + COMMA_SEP +
                    TableEntry.COLUMN_NAME_LINK + TEXT_TYPE + COMMA_SEP +
                    TableEntry.COLUMN_NAME_LAT + TEXT_TYPE + COMMA_SEP +
                    TableEntry.COLUMN_NAME_VEN + TEXT_TYPE + COMMA_SEP +
                    TableEntry.COLUMN_NAME_LON + TEXT_TYPE +
            " )";
    private static final String SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS " + TableEntry.TABLE_NAME;
    public DBhelp(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }
}