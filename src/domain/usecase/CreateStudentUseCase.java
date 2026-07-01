package domain.usecase;

import domain.model.Student;
import domain.repository.StudentRepository;

public class CreateStudentUseCase {
    private final StudentRepository repository;

    public CreateStudentUseCase(StudentRepository repository) {
        this.repository = repository;
    }

    public Student execute(Student student) {
        validate(student);
        repository.save(student);
        return student;
    }

    private void validate(Student student) {
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