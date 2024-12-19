package com.example.enrollmentapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class EnrollMenuActivity extends AppCompatActivity {

    Button selectSubjectButton, viewSummaryButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enroll_menu);

        selectSubjectButton = findViewById(R.id.selectSubjectButton);
        viewSummaryButton = findViewById(R.id.viewSummaryButton);

        selectSubjectButton.setOnClickListener(v -> {
            startActivity(new Intent(EnrollMenuActivity.this, SelectSubjectActivity.class));
        });

        viewSummaryButton.setOnClickListener(v -> {
            startActivity(new Intent(EnrollMenuActivity.this, EnrollSummaryActivity.class));
        });
    }
}
