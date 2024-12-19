package com.example.enrollmentapp;

public class Student {
    public String name;
    public String email;
    public int credits;

    // Default constructor required for Firebase
    public Student() {
    }

    public Student(String name, String email, int credits) {
        this.name = name;
        this.email = email;
        this.credits = credits;
    }
}