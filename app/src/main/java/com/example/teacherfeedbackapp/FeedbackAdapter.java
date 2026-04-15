package com.example.teacherfeedbackapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import java.util.List;

public class FeedbackAdapter extends ArrayAdapter<FeedbackModel> {

    private Context context;
    private List<FeedbackModel> feedbackList;

    public FeedbackAdapter(Context context, List<FeedbackModel> feedbackList) {
        super(context, 0, feedbackList);
        this.context = context;
        this.feedbackList = feedbackList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_feedback, parent, false);
        }

        FeedbackModel feedback = feedbackList.get(position);

        TextView tvStudentName = convertView.findViewById(R.id.tv_student_name);
        TextView tvTeacherName = convertView.findViewById(R.id.tv_teacher_name);
        TextView tvRatings = convertView.findViewById(R.id.tv_ratings);
        TextView tvTimestamp = convertView.findViewById(R.id.tv_timestamp);

        tvStudentName.setText("Student: " + feedback.getStudentName() + " (Roll: " + feedback.getRollNo() + ")");
        tvTeacherName.setText("Teacher: " + feedback.getTeacherName() + " | Division: " + feedback.getDivision());

        int[] ratings = feedback.getRatings();
        double average = 0;
        for (int rating : ratings) {
            average += rating;
        }
        average /= ratings.length;

        tvRatings.setText("Average Rating: " + String.format("%.2f", average) + "/10");
        tvTimestamp.setText("Submitted: " + feedback.getTimestamp());

        return convertView;
    }
}