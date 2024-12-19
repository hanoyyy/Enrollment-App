package com.example.enrollmentapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SelectSubjectActivity extends AppCompatActivity {

    // Declare UI components
    CheckBox subject1, subject2, subject3, subject4, subject5, subject6, subject7, subject8;
    Button submitButton;
    ArrayList<String> selectedSubjects;
    int totalCredits;

    // Firebase variables
    FirebaseAuth auth;
    FirebaseFirestore firestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_subject);

        // Initialize Firebase
        auth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();

        // Initialize UI components
        subject1 = findViewById(R.id.subject1);
        subject2 = findViewById(R.id.subject2);


        subject3 = findViewById(R.id.subject3);
        subject4 = findViewById(R.id.subject4);
        subject5 = findViewById(R.id.subject5);
        subject6 = findViewById(R.id.subject6);
        subject7 = findViewById(R.id.subject7);
        subject8 = findViewById(R.id.subject8);
        submitButton = findViewById(R.id.submitButton);

        // Button click listener
        submitButton.setOnClickListener(v -> calculateSelection());
    }

    private void calculateSelection() {
        selectedSubjects = new ArrayList<>();
        totalCredits = 0;

        // Each subject now has a maximum of 3 credits
        if (subject1.isChecked()) {
            selectedSubjects.add("Mathematics (3 Credits)");
            totalCredits += 3;
        }
        if (subject2.isChecked()) {
            selectedSubjects.add("Physics (3 Credits)");
            totalCredits += 3;
        }
        if (subject3.isChecked()) {
            selectedSubjects.add("Chemistry (3 Credits)");
            totalCredits += 3;
        }
        if (subject4.isChecked()) {
            selectedSubjects.add("Biology (3 Credits)");
            totalCredits += 3;
        }
        if (subject5.isChecked()) {
            selectedSubjects.add("English (3 Credits)");
            totalCredits += 3;
        }
        if (subject6.isChecked()) {
            selectedSubjects.add("History (3 Credits)");
            totalCredits += 3;
        }
        if (subject7.isChecked()) {
            selectedSubjects.add("Geography (3 Credits)");
            totalCredits += 3;
        }
        if (subject8.isChecked()) {
            selectedSubjects.add("Economic (3 Credits)");
            totalCredits += 3;
        }

        // Validation: Fail if total credits exceed 24
        if (totalCredits >= 24) {
            Toast.makeText(this, "Total credits cannot exceed 24! Please adjust your selection.", Toast.LENGTH_SHORT).show();
            return;
        }

        // Store selection in Firestore
        storeInFirestore();

        // Pass selected subjects and total credits to EnrollSummaryActivity
        Intent intent = new Intent(SelectSubjectActivity.this, EnrollSummaryActivity.class);
        intent.putStringArrayListExtra("subjects", selectedSubjects);
        intent.putExtra("totalCredits", totalCredits);
        startActivity(intent);
    }

    private void storeInFirestore() {
        // Get current user ID
        String userId = auth.getCurrentUser().getUid();

        // Create a map for subject and credit details
        Map<String, Object> enrollmentData = new HashMap<>();
        enrollmentData.put("selectedSubjects", selectedSubjects);
        enrollmentData.put("totalCredits", totalCredits);

        // Add data to Firestore under the user's document
        firestore.collection("Enrollments").document(userId)
                .set(enrollmentData)
                .addOnSuccessListener(aVoid -> Toast.makeText(this, "Enrollment saved successfully!", Toast.LENGTH_SHORT).show())
                .addOnFailureListener(e -> Toast.makeText(this, "Failed to save: " + e.getMessage(), Toast.LENGTH_SHORT).show());
    }
}