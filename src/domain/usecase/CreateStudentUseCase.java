package domain.usecase;

import domain.model.Student;
import domain.repository.StudentRepository;
import domain.service.StudentValidator;

public class CreateStudentUseCase {
    private final StudentRepository repository;
    public CreateStudentUseCase(StudentRepository repository) {
        this.repository = repository;
    }
    public Student execute(Student student) {
        StudentValidator.validate(student);
        repository.save(student);
        return student;
    }
}