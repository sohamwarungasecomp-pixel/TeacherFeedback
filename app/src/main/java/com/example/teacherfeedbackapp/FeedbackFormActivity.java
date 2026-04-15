
package com.example.teacherfeedbackapp;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

public class FeedbackFormActivity extends AppCompatActivity {

    private EditText etStudentName, etRollNo;
    private Spinner spDivision, spTeacher;
    private LinearLayout llRatingContainer;
    private Button btnSubmit;
    private FeedbackDatabaseManager dbManager;

    private String[] questions = {
            "1. Teaching Clarity",
            "2. Subject Knowledge",
            "3. Communication Skills",
            "4. Course Organization",
            "5. Student Engagement",
            "6. Availability for Help",
            "7. Feedback Quality",
            "8. Use of Resources",
            "9. Punctuality",
            "10. Overall Teaching Effectiveness"
    };

    private String[] teachers = {
            "Mr. Teacher 1",
            "Ms. Teacher 2",
            "Mr. Teacher 3",
            "Ms. Teacher 4",
            "Mr. Teacher 5",
            "Ms. Teacher 6"
    };

    private String[] divisions = {"A", "B", "C", "D"};

    private RatingBar[] ratingBars;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback_form);

        dbManager = new FeedbackDatabaseManager(this);
        initializeViews();
        setupSpinners();
        createRatingBars();
    }

    private void initializeViews() {
        etStudentName = findViewById(R.id.et_student_name);
        etRollNo = findViewById(R.id.et_roll_no);
        spDivision = findViewById(R.id.sp_division);
        spTeacher = findViewById(R.id.sp_teacher);
        llRatingContainer = findViewById(R.id.ll_rating_container);
        btnSubmit = findViewById(R.id.btn_submit);

        btnSubmit.setOnClickListener(v -> submitFeedback());
    }

    private void setupSpinners() {
        ArrayAdapter<String> divisionAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, divisions);
        divisionAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spDivision.setAdapter(divisionAdapter);

        ArrayAdapter<String> teacherAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, teachers);
        teacherAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spTeacher.setAdapter(teacherAdapter);
    }

    private void createRatingBars() {
        ratingBars = new RatingBar[10];

        for (int i = 0; i < 10; i++) {
            // Create LinearLayout for each question
            LinearLayout questionLayout = new LinearLayout(this);
            questionLayout.setOrientation(LinearLayout.VERTICAL);
            questionLayout.setPadding(0, 12, 0, 12);

            // Create TextView for question
            TextView tvQuestion = new TextView(this);
            tvQuestion.setText(questions[i]);
            tvQuestion.setTextSize(14);
            tvQuestion.setTextColor(ContextCompat.getColor(this, android.R.color.black));

            // Create RatingBar
            RatingBar ratingBar = new RatingBar(this);
            ratingBar.setNumStars(10);
            ratingBar.setStepSize(1);
            ratingBar.setRating(0);

            ratingBars[i] = ratingBar;

            // Add to layout
            questionLayout.addView(tvQuestion);
            questionLayout.addView(ratingBar);

            llRatingContainer.addView(questionLayout);
        }
    }

    private void submitFeedback() {
        String studentName = etStudentName.getText().toString().trim();
        String rollNo = etRollNo.getText().toString().trim();
        String division = spDivision.getSelectedItem().toString();
        String teacherName = spTeacher.getSelectedItem().toString();

        if (studentName.isEmpty() || rollNo.isEmpty()) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        // Check if any rating is 0
        int[] ratings = new int[10];
        for (int i = 0; i < 10; i++) {
            ratings[i] = (int) ratingBars[i].getRating();
            if (ratings[i] == 0) {
                Toast.makeText(this, "Please rate all questions", Toast.LENGTH_SHORT).show();
                return;
            }
        }

        FeedbackModel feedback = new FeedbackModel(studentName, rollNo, division,
                1, teacherName, ratings);

        long result = dbManager.saveFeedback(feedback);
        if (result > 0) {
            Toast.makeText(this, "Feedback submitted successfully!", Toast.LENGTH_SHORT).show();
            clearForm();
        } else {
            Toast.makeText(this, "Error saving feedback", Toast.LENGTH_SHORT).show();
        }
    }

    private void clearForm() {
        etStudentName.setText("");
        etRollNo.setText("");
        for (RatingBar rb : ratingBars) {
            rb.setRating(0);
        }
    }
}