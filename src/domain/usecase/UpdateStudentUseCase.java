package domain.usecase;

import domain.model.Student;
import domain.repository.StudentRepository;

public class UpdateStudentUseCase {
    private final StudentRepository repository;

    public UpdateStudentUseCase(StudentRepository repository){
        this.repository = repository;
    }

    public void execute(int id, String lastname, String name, String ci, String grade) {
        if (lastname == null || lastname.isBlank()) throw new IllegalArgumentException("Apellido requerido");
        if (name == null || name.isBlank()) throw new IllegalArgumentException("Nombre requerido");
        if (ci == null || ci.isBlank()) throw new IllegalArgumentException("CI requerido");
        if (grade == null || grade.isBlank()) throw new IllegalArgumentException("Nota requerida");
        try {
            float g = Float.parseFloat(grade);
            if (g<0 || g>100) throw new IllegalArgumentException("La nota debe estar entre 0 y 100");

        } catch (NumberFormatException e){
            throw new IllegalArgumentException("La nota debe ser un numero");
        }
        Student student = new Student(id, lastname, name, ci, grade);
        repository.update(student);
    }
}
