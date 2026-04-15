package com.example.teacherfeedback;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class FeedbackDatabase extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "TeacherFeedback.db";
    private static final int DATABASE_VERSION = 1;

    public static final String TABLE_FEEDBACK = "feedback";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_STUDENT_NAME = "student_name";
    public static final String COLUMN_ROLL_NO = "roll_no";
    public static final String COLUMN_DIVISION = "division";
    public static final String COLUMN_FACULTY_NAME = "faculty_name";
    public static final String COLUMN_RATINGS = "ratings";
    public static final String COLUMN_TIMESTAMP = "timestamp";

    public FeedbackDatabase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_FEEDBACK_TABLE = "CREATE TABLE " + TABLE_FEEDBACK + " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_STUDENT_NAME + " TEXT, " + COLUMN_ROLL_NO + " TEXT, " + COLUMN_DIVISION + " TEXT, " + COLUMN_FACULTY_NAME + " TEXT, " + COLUMN_RATINGS + " TEXT, " + COLUMN_TIMESTAMP + " TIMESTAMP DEFAULT CURRENT_TIMESTAMP)";
        db.execSQL(CREATE_FEEDBACK_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_FEEDBACK);
        onCreate(db);
    }
}