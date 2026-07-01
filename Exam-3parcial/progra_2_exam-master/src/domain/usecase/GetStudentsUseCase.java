package domain.usecase; 

import java.util.List;

import domain.model.Student;
import domain.repository.StudentRepository;

public class GetStudentsUseCase {
    private final StudentRepository repository;

    public GetStudentsUseCase(StudentRepository repository){
        this.repository = repository;
    }

    public List<Student> execute() {
    return repository.findAll();
    }

}
