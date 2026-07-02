package domain.usecase;

import java.util.ArrayList;

import domain.model.Student;
import domain.repository.StudentRepository;


public class StudentUseCase {

    private final StudentRepository repository;

    public StudentUseCase(StudentRepository repository) {
        this.repository = repository;
    }

    public ArrayList<Student> getAll() {
        return repository.findAll();
    }

    public void create(String lastname, String name, String ci, String grade) {
        validateFields(lastname, name, ci, grade);
        Student student = new Student(lastname, name, ci, grade);
        repository.save(student);
    }

    public void edit(int id, String lastname, String name, String ci, String grade) {
        validateFields(lastname, name, ci, grade);
        Student student = new Student(id, lastname, name, ci, grade);
        repository.update(student);
    }

    public void remove(int id) {
        if (id <= 0) {
            throw new IllegalArgumentException("ID de estudiante inválido.");
        }
        repository.delete(id);
    }

    

    private void validateFields(String lastname, String name, String ci, String grade) {
        if (lastname == null || lastname.isBlank()) {
            throw new IllegalArgumentException("El apellido es obligatorio.");
        }
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("El nombre es obligatorio.");
        }
        if (ci == null || ci.isBlank()) {
            throw new IllegalArgumentException("El CI es obligatorio.");
        }
        if (!ci.matches("\\d{5,10}")) {
            throw new IllegalArgumentException("El CI debe contener entre 5 y 10 dígitos numéricos.");
        }
        if (grade == null || grade.isBlank()) {
            throw new IllegalArgumentException("La nota es obligatoria.");
        }
        try {
            int g = Integer.parseInt(grade.trim());
            if (g < 0 || g > 100) {
                throw new IllegalArgumentException("La nota debe estar entre 0 y 100.");
            }
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("La nota debe ser un número entero.");
        }
    }
}