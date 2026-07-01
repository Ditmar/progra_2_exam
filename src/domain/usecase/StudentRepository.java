package domain.usecase;

import domain.model.Student;
import domain.repository.StudentRepository;
import java.util.List;

public class ManageStudentsUseCase {
    private final StudentRepository repository;

    public ManageStudentsUseCase(StudentRepository repository) {
        this.repository = repository;
    }

    public List<Student> executeGetAll() {
        return repository.findAll();
    }

    public void executeSave(Student student) {
        // 1.3 Validaciones de negocio exigidas
        if (student.getCi() == null || student.getCi().trim().isEmpty() ||
            student.getName() == null || student.getName().trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre y la CI son campos obligatorios.");
        }
        // Aquí puedes agregar más lógica/validaciones (ej. rangos de notas si aplica)
        repository.save(student);
    }

    public void executeUpdate(Student student) {
        repository.update(student);
    }

    public void executeDelete(int id) {
        repository.delete(id);
    }
}