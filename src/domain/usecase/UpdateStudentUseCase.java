package domain.usecase;

import domain.model.Student;
import domain.repository.StudentRepository;

public class UpdateStudentUseCase {
    private final StudentRepository repository;

    public UpdateStudentUseCase(StudentRepository repository) {
        this.repository = repository;
    }

    public void execute(Student student) {
        if (student == null || student.getId() <= 0) {
            throw new IllegalArgumentException("ID de estudiante inválido");
        }
        new CreateStudentUseCase(repository).execute(student);
        repository.update(student);
    }
}