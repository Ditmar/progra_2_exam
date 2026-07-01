package domain.usecase;

import domain.model.Student;
import domain.repository.StudentRepository;
import java.util.List;

public class ManageStudentUseCase {
    
    private final StudentRepository repository;

    public ManageStudentUseCase(StudentRepository repository) {
        this.repository = repository;
    }

    public List<Student> getStudents() {
        return repository.findAll();
    }

    public void saveStudent(String lastname, String name, String ci, String grade) {
        validateFields(lastname, name, ci, grade);
        Student student = new Student(lastname, name, ci, grade);
        repository.save(student);
    }
    
    public void updateStudent(String lastname, String name, String ci, String grade) {
        validateFields(lastname, name, ci, grade);
        Student student = new Student(lastname, name, ci, grade);
        repository.update(student);
    }

    public void deleteStudent(String ci) {
        if (ci == null || ci.isBlank()) {
            throw new IllegalArgumentException("El CI no puede estar vacío para eliminar.");
        }
        repository.delete(ci);
    }

    private void validateFields(String lastname, String name, String ci, String grade) {
        if (name == null || name.isBlank() || lastname == null || lastname.isBlank()) {
            throw new IllegalArgumentException("El nombre y apellido son obligatorios.");
        }
        if (ci == null || ci.isBlank()) {
            throw new IllegalArgumentException("El CI es obligatorio.");
        }
        if (!ci.matches("\\d+")) {
            throw new IllegalArgumentException("El CI solo debe contener números.");
        }
        
        try {
            int gradeValue = Integer.parseInt(grade);
            if (gradeValue < 0 || gradeValue > 100) {
                throw new IllegalArgumentException("La nota debe estar entre 0 y 100.");
            }
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("La nota debe ser un valor numérico válido.");
        }
    }
}