package domain.service;

import domain.model.Student;

public final class StudentValidator {
    private StudentValidator() {}

    public static void validate(Student student) {
        if (student == null) {
            throw new IllegalArgumentException("El estudiante no puede ser nulo");
        }
        if (student.getLastname() == null || student.getLastname().isBlank()) {
            throw new IllegalArgumentException("Apellido es obligatorio");
        }
        if (student.getName() == null || student.getName().isBlank()) {
            throw new IllegalArgumentException("Nombre es obligatorio");
        }
        if (student.getCi() == null || student.getCi().isBlank()) {
            throw new IllegalArgumentException("CI es obligatorio");
        }
        if (!student.getCi().matches("\\d+")) {
            throw new IllegalArgumentException("CI debe contener solo dígitos");
        }
        if (student.getGrade() == null || student.getGrade().isBlank()) {
            throw new IllegalArgumentException("Nota es obligatoria");
        }
        try {
            double grade = Double.parseDouble(student.getGrade());
            if (grade < 0 || grade > 100) {
                throw new IllegalArgumentException("La nota debe estar entre 0 y 100");
            }
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("La nota debe ser un número válido");
        }
    }
}