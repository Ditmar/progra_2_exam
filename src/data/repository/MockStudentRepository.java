package data.repository;

import java.util.ArrayList;

import domain.model.Student;
import domain.repository.StudentRepository;

public class MockStudentRepository  implements StudentRepository {
    private final ArrayList<Student> students = new ArrayList<>();
    private int nextId = 1;

    public MockStudentRepository() {
        students.add(new Student(nextId++, "Alvarez", "Diego", "543210", "22"));
        students.add(new Student(nextId++, "Torres", "Lucia", "987654", "30"));
        students.add(new Student(nextId++, "Ramirez", "Carlos", "135791", "45"));
        students.add(new Student(nextId++, "Flores", "Gabriela", "246810", "18"));
        students.add(new Student(nextId++, "Diaz", "Fernando", "112233", "27"));
        students.add(new Student(nextId++, "Vargas", "Valeria", "445566", "35"));
        students.add(new Student(nextId++, "Castillo", "Alejandro", "778899", "51"));
        students.add(new Student(nextId++, "Morales", "Daniela", "990011", "23"));
        students.add(new Student(nextId++, "Herrera", "Javier", "223344", "40"));
        students.add(new Student(nextId++, "Castro", "Camila", "556677", "26"));
        students.add(new Student(nextId++, "Medina", "Ricardo", "889900", "48"));
        students.add(new Student(nextId++, "Aguilar", "Victoria", "121314", "33"));
        students.add(new Student(nextId++, "Suarez", "Sebastian", "151617", "20"));
        students.add(new Student(nextId++, "Salazar", "Natalia", "181920", "29"));
        students.add(new Student(nextId++, "Delgado", "Juan", "212223", "55"));
        students.add(new Student(nextId++, "Rios", "Valentina", "242526", "41"));
        students.add(new Student(nextId++, "Mendoza", "Gabriel", "272829", "19"));
        students.add(new Student(nextId++, "Ortega", "Isabella", "303132", "37"));
        students.add(new Student(nextId++, "Rojas", "Nicolas", "333435", "62"));
        students.add(new Student(nextId++, "Guerrero", "Paulina", "363738", "25"));
    }
    @Override
    public ArrayList<Student> findAll() {
        return new ArrayList<>(students);
    }
    @Override
    public void save(Student student) {
        student.setId(nextId++);
        students.add(student);
    }
    @Override
    public void update(Student student) {
        for (int i = 0; i < students.size(); i++) {
            if (students.get(i).getId() == student.getId()) {
                students.set(i, student);
                return;
            }
        }
        throw new IllegalArgumentException("Estudinate no encontrado");

    }
    @Override
    public void delete(int id) {
        boolean removed = students.removeIf(s -> s.getId() == id);
        if (!removed) throw new IllegalArgumentException("Estudiante no encontrado");
    }
    
}
