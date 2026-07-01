package domain.usecase;

import domain.model.Student;
import domain.repository.StudentRepository;
import java.util.List;

public class GetStudentsUseCase {
    private final StudentRepository repository;

    public GetStudentsUseCase(StudentRepository repository) {
        this.repository = repository;
    }

    public List<Student> execute() {
        return repository.findAll();
    }
}