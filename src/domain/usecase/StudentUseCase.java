package domain.usecase;
import domain.model.Student;
import domain.repository.StudentRepository;
import java.util.List;
public class StudentUseCase {
    private final StudentRepository repository;
    public StudentUseCase(StudentRepository repository) {
        this.repository = repository;
    }

    public List<Student> getAll() {
        return repository.findAll();
    }

    public void create(String lastname, String name, String ci, int grade) {
        if (lastname == null || lastname.isBlank()) {
            throw new IllegalArgumentException("El apellido no es válido");
        }
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("El nombre no es válido");
        }
        if (ci == null || ci.isBlank()) {
            throw new IllegalArgumentException("La cédula de identidad (CI) no es válida");
        }
        if (grade < 0 || grade > 100) { // Validación del rango de nota (0 a 100)
            throw new IllegalArgumentException("La nota debe estar entre 0 y 100");
        }

        int nextId = repository.findAll().size() + 1;

        Student student = new Student(nextId, lastname, name, ci, grade);
        repository.save(student);
    }

    public void update(int id, String lastname, String name, String ci, int grade) {
        if (lastname == null || lastname.isBlank()) {
            throw new IllegalArgumentException("El apellido no es válido para actualizar");
        }
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("El nombre no es válido para actualizar");
        }
        if (ci == null || ci.isBlank()) {
            throw new IllegalArgumentException("La cédula de identidad (CI) no es válida");
        }
        if (grade < 0 || grade > 100) {
            throw new IllegalArgumentException("La nota a actualizar debe estar entre 0 y 100");
        }

        Student student = new Student(id, lastname, name, ci, grade);
        repository.update(student);
    }

    public void delete(int id) {
        
        repository.delete(id);
    }
}
