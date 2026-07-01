package domain.usecase;

import domain.model.Student;
import domain.repository.StudentRepository;
import domain.service.StudentValidator;

public class UpdateStudentUseCase {
    private final StudentRepository repository;
    public UpdateStudentUseCase(StudentRepository repository) {
        this.repository = repository;
    }
    public void execute(Student student) {
        if (student.getId() <= 0) {
            throw new IllegalArgumentException("ID inválido para actualizar");
        }
        StudentValidator.validate(student);
        repository.update(student);
    }
}