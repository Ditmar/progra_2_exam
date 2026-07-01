package domain.usecase;

import domain.model.Student;
import domain.repository.StudentRepository;
import java.util.List;

public class ManageStudentsUseCase {
    private final StudentRepository repository;

    public ManageStudentsUseCase(StudentRepository repository) {
        this.repository = repository;
    }

    public List<Student> getAllStudents() {
        return repository.findAll();
    }

    public void createStudent(Student student) {
        validateBusinessRules(student);
        repository.save(student);
    }

    public void modifyStudent(Student student) {
        validateBusinessRules(student);
        repository.update(student);
    }

    public void removeStudent(int id) {
        repository.delete(id);
    }

    private void validateBusinessRules(Student student) {
        if (student.getCi() == null || student.getCi().trim().isEmpty() ||
            student.getName() == null || student.getName().trim().isEmpty() ||
            student.getLastname() == null || student.getLastname().trim().isEmpty()) {
            throw new IllegalArgumentException("Campos obligatorios incompletos (CI, Nombre y Apellido).");
        }
    }
}