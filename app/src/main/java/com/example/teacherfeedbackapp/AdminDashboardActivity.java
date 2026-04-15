
package com.example.teacherfeedbackapp;

import android.os.Bundle;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.List;

public class AdminDashboardActivity extends AppCompatActivity {

    private ListView lvFeedback;
    private TextView tvStats;
    private FeedbackDatabaseManager dbManager;
    private FeedbackAdapter feedbackAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_dashboard);

        dbManager = new FeedbackDatabaseManager(this);
        lvFeedback = findViewById(R.id.lv_feedback);
        tvStats = findViewById(R.id.tv_stats);

        loadFeedbackData();
        displayStats();
    }

    private void loadFeedbackData() {
        List<FeedbackModel> feedbackList = dbManager.getAllFeedback();
        feedbackAdapter = new FeedbackAdapter(this, feedbackList);
        lvFeedback.setAdapter(feedbackAdapter);
    }

    private void displayStats() {
        List<FeedbackModel> feedbackList = dbManager.getAllFeedback();
        int totalFeedback = feedbackList.size();

        // Calculate average ratings
        int[] totalRatings = new int[10];
        for (FeedbackModel feedback : feedbackList) {
            int[] ratings = feedback.getRatings();
            for (int i = 0; i < 10; i++) {
                totalRatings[i] += ratings[i];
            }
        }

        StringBuilder stats = new StringBuilder();
        stats.append("📊 STATISTICS\n");
        stats.append("═══════════════════════════════\n");
        stats.append("Total Feedback Received: ").append(totalFeedback).append("\n\n");

        String[] questions = {"Teaching Clarity", "Subject Knowledge", "Communication Skills",
                "Course Organization", "Student Engagement", "Availability for Help",
                "Feedback Quality", "Use of Resources", "Punctuality",
                "Overall Teaching Effectiveness"};

        for (int i = 0; i < 10; i++) {
            double average = totalFeedback > 0 ? (double) totalRatings[i] / totalFeedback : 0;
            stats.append(String.format("Q%d. %s: %.2f/10\n", i + 1, questions[i], average));
        }

        tvStats.setText(stats.toString());
    }
}