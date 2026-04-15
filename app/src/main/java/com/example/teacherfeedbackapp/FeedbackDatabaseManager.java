package com.example.teacherfeedbackapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import java.util.ArrayList;
import java.util.List;

public class FeedbackDatabaseManager {
    private FeedbackDatabase dbHelper;
    private SQLiteDatabase database;

    public FeedbackDatabaseManager(Context context) {
        dbHelper = new FeedbackDatabase(context);
    }

    public void open() {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public long saveFeedback(FeedbackModel feedback) {
        open();
        ContentValues values = new ContentValues();
        values.put(FeedbackDatabase.COLUMN_STUDENT_NAME, feedback.getStudentName());
        values.put(FeedbackDatabase.COLUMN_ROLL_NO, feedback.getRollNo());
        values.put(FeedbackDatabase.COLUMN_DIVISION, feedback.getDivision());
        values.put(FeedbackDatabase.COLUMN_TEACHER_ID, feedback.getTeacherId());
        values.put(FeedbackDatabase.COLUMN_TEACHER_NAME, feedback.getTeacherName());
        values.put(FeedbackDatabase.COLUMN_RATINGS, feedback.ratingsToJson());

        long result = database.insert(FeedbackDatabase.TABLE_FEEDBACK, null, values);
        close();
        return result;
    }

    public List<FeedbackModel> getAllFeedback() {
        open();
        List<FeedbackModel> feedbackList = new ArrayList<>();
        Cursor cursor = database.query(FeedbackDatabase.TABLE_FEEDBACK, null, null, null, null, null,
                FeedbackDatabase.COLUMN_TIMESTAMP + " DESC");

        if (cursor.moveToFirst()) {
            do {
                FeedbackModel feedback = new FeedbackModel();

                int idIndex = cursor.getColumnIndex(FeedbackDatabase.COLUMN_ID);
                int studentNameIndex = cursor.getColumnIndex(FeedbackDatabase.COLUMN_STUDENT_NAME);
                int rollNoIndex = cursor.getColumnIndex(FeedbackDatabase.COLUMN_ROLL_NO);
                int divisionIndex = cursor.getColumnIndex(FeedbackDatabase.COLUMN_DIVISION);
                int teacherIdIndex = cursor.getColumnIndex(FeedbackDatabase.COLUMN_TEACHER_ID);
                int teacherNameIndex = cursor.getColumnIndex(FeedbackDatabase.COLUMN_TEACHER_NAME);
                int ratingsIndex = cursor.getColumnIndex(FeedbackDatabase.COLUMN_RATINGS);
                int timestampIndex = cursor.getColumnIndex(FeedbackDatabase.COLUMN_TIMESTAMP);

                if (idIndex >= 0) feedback.setId(cursor.getInt(idIndex));
                if (studentNameIndex >= 0) feedback.setStudentName(cursor.getString(studentNameIndex));
                if (rollNoIndex >= 0) feedback.setRollNo(cursor.getString(rollNoIndex));
                if (divisionIndex >= 0) feedback.setDivision(cursor.getString(divisionIndex));
                if (teacherIdIndex >= 0) feedback.setTeacherId(cursor.getInt(teacherIdIndex));
                if (teacherNameIndex >= 0) feedback.setTeacherName(cursor.getString(teacherNameIndex));
                if (timestampIndex >= 0) feedback.setTimestamp(cursor.getString(timestampIndex));

                if (ratingsIndex >= 0) {
                    try {
                        String ratingsJson = cursor.getString(ratingsIndex);
                        feedback.setRatings(FeedbackModel.jsonToRatings(ratingsJson));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                feedbackList.add(feedback);
            } while (cursor.moveToNext());
        }
        cursor.close();
        close();
        return feedbackList;
    }

    public List<FeedbackModel> getFeedbackByTeacher(int teacherId) {
        open();
        List<FeedbackModel> feedbackList = new ArrayList<>();
        Cursor cursor = database.query(FeedbackDatabase.TABLE_FEEDBACK, null,
                FeedbackDatabase.COLUMN_TEACHER_ID + "=?",
                new String[]{String.valueOf(teacherId)}, null, null, null);

        if (cursor.moveToFirst()) {
            do {
                FeedbackModel feedback = new FeedbackModel();

                int idIndex = cursor.getColumnIndex(FeedbackDatabase.COLUMN_ID);
                int studentNameIndex = cursor.getColumnIndex(FeedbackDatabase.COLUMN_STUDENT_NAME);
                int rollNoIndex = cursor.getColumnIndex(FeedbackDatabase.COLUMN_ROLL_NO);
                int divisionIndex = cursor.getColumnIndex(FeedbackDatabase.COLUMN_DIVISION);
                int teacherIdIndex = cursor.getColumnIndex(FeedbackDatabase.COLUMN_TEACHER_ID);
                int teacherNameIndex = cursor.getColumnIndex(FeedbackDatabase.COLUMN_TEACHER_NAME);
                int ratingsIndex = cursor.getColumnIndex(FeedbackDatabase.COLUMN_RATINGS);

                if (idIndex >= 0) feedback.setId(cursor.getInt(idIndex));
                if (studentNameIndex >= 0) feedback.setStudentName(cursor.getString(studentNameIndex));
                if (rollNoIndex >= 0) feedback.setRollNo(cursor.getString(rollNoIndex));
                if (divisionIndex >= 0) feedback.setDivision(cursor.getString(divisionIndex));
                if (teacherIdIndex >= 0) feedback.setTeacherId(cursor.getInt(teacherIdIndex));
                if (teacherNameIndex >= 0) feedback.setTeacherName(cursor.getString(teacherNameIndex));

                if (ratingsIndex >= 0) {
                    try {
                        String ratingsJson = cursor.getString(ratingsIndex);
                        feedback.setRatings(FeedbackModel.jsonToRatings(ratingsJson));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                feedbackList.add(feedback);
            } while (cursor.moveToNext());
        }
        cursor.close();
        close();
        return feedbackList;
    }
}