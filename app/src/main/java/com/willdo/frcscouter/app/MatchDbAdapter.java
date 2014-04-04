package com.willdo.frcscouter.app;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by William on 4/3/14.
 */
public class MatchDbAdapter {
    public static final String KEY_TEAM = "team";
    public static final String KEY_MATCH = "match";
    public static final String KEY_ALLIANCE = "alliance";
    public static final String KEY_CRITA = "crita";
    public static final String KEY_CRITB = "critb";
    public static final String KEY_CRITC = "critc";
    public static final String KEY_CRITD = "critd";
    public static final String KEY_PENALTY = "penalty";
    public static final String KEY_COOP = "coop";
    public static final String KEY_DEFENSE = "defense";
    public static final String KEY_ROWID = "_id";

    private static final String TAG = "MatchDbAdapter";
    private DatabaseHelper mDbHelper;
    private SQLiteDatabase mDb;

    /**
     * Database creation sql statement
     */
    private static final String DATABASE_CREATE =
            "create table matches (_id integer primary key autoincrement, " +
                    "team integer not null, " +
                    "match integer not null, " +
                    "alliance integer not null, " +
                    "crita integer not null, " +
                    "critb integer not null, " +
                    "critc integer not null, " +
                    "critd integer not null, " +
                    "penalty integer not null, " +
                    "coop integer not null, " +
                    "defense integer not null" +
                    ");";

    private static final String DATABASE_NAME = "data";
    private static final String DATABASE_TABLE = "matches";
    private static final int DATABASE_VERSION = 2;

    private final Context mCtx;

    private static class DatabaseHelper extends SQLiteOpenHelper {

        DatabaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {

            db.execSQL(DATABASE_CREATE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            Log.w(TAG, "Upgrading database from version " + oldVersion + " to "
                    + newVersion + ", which will destroy all old data");
            db.execSQL("DROP TABLE IF EXISTS matches");
            onCreate(db);
        }
    }

    public MatchDbAdapter(Context ctx) {
        this.mCtx = ctx;
    }

    public MatchDbAdapter open() throws SQLException {
        mDbHelper = new DatabaseHelper(mCtx);
        mDb = mDbHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        mDbHelper.close();
    }

    public long createMatch(int team, int match, int alliance, int critA, int critB, int critC, int critD, int penalty, int coop, int defense) {
        ContentValues initialValues = new ContentValues();
        initialValues.put(KEY_TEAM, team);
        initialValues.put(KEY_MATCH, match);
        initialValues.put(KEY_ALLIANCE, alliance);
        initialValues.put(KEY_CRITA, critA);
        initialValues.put(KEY_CRITB, critB);
        initialValues.put(KEY_CRITC, critC);
        initialValues.put(KEY_CRITD, critD);
        initialValues.put(KEY_PENALTY, penalty);
        initialValues.put(KEY_COOP, coop);
        initialValues.put(KEY_DEFENSE, defense);

        return mDb.insert(DATABASE_TABLE, null, initialValues);
    }

    public boolean deleteMatch(long rowId) {
        return mDb.delete(DATABASE_TABLE, KEY_ROWID + "=" + rowId, null) > 0;
    }

    public Cursor fetchAllMatches() {
        return mDb.query(DATABASE_TABLE, new String[] {KEY_ROWID, KEY_TEAM,
                KEY_MATCH}, null, null, null, null, null);
    }

    public Cursor fetchMatchCursor(long rowId) throws SQLException {
        Cursor mCursor =
                mDb.query(true, DATABASE_TABLE,
                        new String[] {KEY_ROWID, KEY_TEAM, KEY_MATCH, KEY_ALLIANCE, KEY_CRITA, KEY_CRITB, KEY_CRITC, KEY_CRITD, KEY_PENALTY, KEY_COOP, KEY_DEFENSE}
                        , KEY_ROWID + "=" + rowId, null,
                        null, null, null, null);
        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        return mCursor;

    }
    public boolean updateMatch(long rowId, int team, int match, int alliance, int critA, int critB, int critC, int critD, int penalty, int coop, int defense) {
        ContentValues args = new ContentValues();
        args.put(KEY_TEAM, team);
        args.put(KEY_MATCH, match);
        args.put(KEY_ALLIANCE, alliance);
        args.put(KEY_CRITA, critA);
        args.put(KEY_CRITB, critB);
        args.put(KEY_CRITC, critC);
        args.put(KEY_CRITD, critD);
        args.put(KEY_PENALTY, penalty);
        args.put(KEY_COOP, coop);
        args.put(KEY_DEFENSE, defense);

        return mDb.update(DATABASE_TABLE, args, KEY_ROWID + "=" + rowId, null) > 0;
    }
}
