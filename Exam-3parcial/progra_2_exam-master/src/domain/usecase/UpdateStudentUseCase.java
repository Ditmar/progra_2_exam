package domain.usecase;

import domain.model.Student;
import domain.repository.StudentRepository;

public class UpdateStudentUseCase {

    private final StudentRepository repository;

    public UpdateStudentUseCase(StudentRepository repository) {
        this.repository = repository;
    }

    public void execute(Student student) {

        validate(student);

        repository.update(student);
    }

    private void validate(Student student) {

        if (student.getLastname() == null || student.getLastname().isBlank())
            throw new IllegalArgumentException("Apellido obligatorio.");

        if (student.getName() == null || student.getName().isBlank())
            throw new IllegalArgumentException("Nombre obligatorio.");

        if (student.getCi() == null || student.getCi().isBlank())
            throw new IllegalArgumentException("CI obligatorio.");

        if (!student.getCi().matches("\\d+"))
            throw new IllegalArgumentException("CI inválido.");

        if (student.getGrade() == null || student.getGrade().isBlank())
            throw new IllegalArgumentException("Grado obligatorio.");

        try{

            int grade = Integer.parseInt(student.getGrade());

            if(grade < 0 || grade > 100){
                throw new IllegalArgumentException("La nota debe estar entre 0 y 100.");
            }
        }catch(NumberFormatException e) {
            throw new IllegalArgumentException("La nota debe ser un numero valido.");
        }

        if (!student.getLastname().matches("[A-Za-zÁÉÍÓÚáéíóúÑñ ]+")) {
            throw new IllegalArgumentException("El apellido solo debe contener letras.");}
            
            if (!student.getName().matches("[A-Za-zÁÉÍÓÚáéíóúÑñ ]+")) {
                throw new IllegalArgumentException("El nombre solo debe contener letras.");
            }

    }

}