package com.example.enrollmentapp;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class EnrollSummaryActivity extends AppCompatActivity {

    TextView summaryTextView;
    FirebaseAuth auth;
    FirebaseFirestore firestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enroll_summary);

        summaryTextView = findViewById(R.id.summaryTextView);

        // Initialize Firebase
        auth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();

        // Load enrollment data from Firestore
        loadEnrollmentData();
    }

    private void loadEnrollmentData() {
        String userId = auth.getCurrentUser().getUid();

        firestore.collection("Enrollments").document(userId)
                .get()
                .addOnSuccessListener(documentSnapshot -> {
                    if (documentSnapshot.exists()) {
                        ArrayList<String> enrolledSubjects = (ArrayList<String>) documentSnapshot.get("selectedSubjects");
                        int totalCredits = documentSnapshot.getLong("totalCredits").intValue();

                        displaySummary(enrolledSubjects, totalCredits);
                    } else {
                        Toast.makeText(this, "No enrollment data found!", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(e -> Toast.makeText(this, "Failed to load data: " + e.getMessage(), Toast.LENGTH_SHORT).show());
    }

    private void displaySummary(ArrayList<String> enrolledSubjects, int totalCredits) {
        StringBuilder summary = new StringBuilder("Enrolled Subjects:\n");

        for (String subject : enrolledSubjects) {
            summary.append("- ").append(subject).append("\n");
        }

        summary.append("\nTotal Credits: ").append(totalCredits);
        summaryTextView.setText(summary.toString());
    }
}