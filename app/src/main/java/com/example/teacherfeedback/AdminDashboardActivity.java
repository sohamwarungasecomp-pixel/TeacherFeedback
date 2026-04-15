package com.example.teacherfeedback;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.TextView;

public class AdminDashboardActivity extends AppCompatActivity {

    private TextView feedbackStats;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_dashboard);

        feedbackStats = findViewById(R.id.feedbackStats);

        // Retrieve and display faculty and division wise feedback statistics
        displayFeedbackStatistics();
    }

    private void displayFeedbackStatistics() {
        // Dummy data for demonstration purposes
        String facultyFeedback = "Faculty A: 75% positive feedback\nFaculty B: 85% positive feedback";
        String divisionFeedback = "Division 1: 80% positive feedback\nDivision 2: 90% positive feedback";

        String statistics = "Feedback Statistics:\n\n" + facultyFeedback + "\n\n" + divisionFeedback;
        feedbackStats.setText(statistics);
    }
}