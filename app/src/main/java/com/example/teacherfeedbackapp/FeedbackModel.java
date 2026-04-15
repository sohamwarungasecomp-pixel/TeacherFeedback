package com.example.teacherfeedbackapp;

import org.json.JSONArray;
import org.json.JSONException;

public class FeedbackModel {
    private int id;
    private String studentName;
    private String rollNo;
    private String division;
    private int teacherId;
    private String teacherName;
    private int[] ratings;
    private String timestamp;

    public FeedbackModel() {}

    public FeedbackModel(String studentName, String rollNo, String division,
                         int teacherId, String teacherName, int[] ratings) {
        this.studentName = studentName;
        this.rollNo = rollNo;
        this.division = division;
        this.teacherId = teacherId;
        this.teacherName = teacherName;
        this.ratings = ratings;
    }

    public String ratingsToJson() {
        JSONArray jsonArray = new JSONArray();
        for (int rating : ratings) {
            jsonArray.put(rating);
        }
        return jsonArray.toString();
    }

    public static int[] jsonToRatings(String jsonString) throws JSONException {
        JSONArray jsonArray = new JSONArray(jsonString);
        int[] ratings = new int[jsonArray.length()];
        for (int i = 0; i < jsonArray.length(); i++) {
            ratings[i] = jsonArray.getInt(i);
        }
        return ratings;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getStudentName() { return studentName; }
    public void setStudentName(String studentName) { this.studentName = studentName; }

    public String getRollNo() { return rollNo; }
    public void setRollNo(String rollNo) { this.rollNo = rollNo; }

    public String getDivision() { return division; }
    public void setDivision(String division) { this.division = division; }

    public int getTeacherId() { return teacherId; }
    public void setTeacherId(int teacherId) { this.teacherId = teacherId; }

    public String getTeacherName() { return teacherName; }
    public void setTeacherName(String teacherName) { this.teacherName = teacherName; }

    public int[] getRatings() { return ratings; }
    public void setRatings(int[] ratings) { this.ratings = ratings; }

    public String getTimestamp() { return timestamp; }
    public void setTimestamp(String timestamp) { this.timestamp = timestamp; }
}