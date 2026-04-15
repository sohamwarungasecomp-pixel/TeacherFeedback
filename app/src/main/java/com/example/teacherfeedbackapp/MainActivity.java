package com.example.teacherfeedbackapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private Button btnStudentFeedback;
    private Button btnAdminDashboard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnStudentFeedback = findViewById(R.id.btn_student_feedback);
        btnAdminDashboard = findViewById(R.id.btn_admin_dashboard);

        btnStudentFeedback.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, FeedbackFormActivity.class);
            startActivity(intent);
        });

        btnAdminDashboard.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, AdminDashboardActivity.class);
            startActivity(intent);
        });
    }
}