package domain.usecase;

import domain.repository.StudentRepository;

public class DeleteStudentUseCase {
    private final StudentRepository repository;

    public DeleteStudentUseCase(StudentRepository repository) {
        this.repository = repository;
    }

    public void execute(int id) {
        if (id <= 0) {
            throw new IllegalArgumentException("ID inválido para eliminar");
        }
        repository.delete(id);
    }
}