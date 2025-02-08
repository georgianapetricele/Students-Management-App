package org.example.mpp_backend.validation;

import org.example.mpp_backend.model.Student;

public class StudentValidation {
    public static void validate(Student student) {
        try {
            // Parse age as integer
            int age = student.getAge();
            if (age <= 0 || age > 120) {
                throw new IllegalArgumentException("Invalid age. Age must be between 1 and 120.");
            }
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid age format. Age must be an integer.");
        }

        if (student.getName() == null || student.getName().isEmpty()) {
            throw new IllegalArgumentException("Name cannot be empty.");
        }

        if (student.getMajor() == null || student.getMajor().isEmpty()) {
            throw new IllegalArgumentException("Major cannot be empty.");
        }
    }
}