import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class FeedbackFormActivity extends AppCompatActivity {

    private EditText studentName;
    private RatingBar[] questionRatings;
    private Button submitButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback_form);

        studentName = findViewById(R.id.student_name);
        questionRatings = new RatingBar[10];

        // Initialize RatingBars for 10 questions
        for (int i = 0; i < questionRatings.length; i++) {
            String ratingBarID = "rating_question_" + (i + 1);
            int resID = getResources().getIdentifier(ratingBarID, "id", getPackageName());
            questionRatings[i] = findViewById(resID);
        }

        submitButton = findViewById(R.id.submit_feedback_button);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitFeedback();
            }
        });
    }

    private void submitFeedback() {
        String name = studentName.getText().toString();
        StringBuilder feedback = new StringBuilder();

        if (name.isEmpty()) {
            Toast.makeText(this, "Please enter your name.", Toast.LENGTH_SHORT).show();
            return;
        }

        feedback.append("Feedback from: " + name + '\n');

        for (int i = 0; i < questionRatings.length; i++) {
            int rating = (int) questionRatings[i].getRating();
            feedback.append("Question " + (i + 1) + ": " + rating + '\n');
        }

        // Here you may handle the feedback (e.g., send it to a server or save it locally)
        // For now, just show a toast
        Toast.makeText(this, "Feedback submitted!\n" + feedback.toString(), Toast.LENGTH_LONG).show();
    }
}
