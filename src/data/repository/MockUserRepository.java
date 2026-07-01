package data.repository;

import domain.model.Student;
import domain.repository.StudentRepository;
import java.util.ArrayList;
import java.util.List;

public class MockStudentRepository implements StudentRepository {
    private final List<Student> dbMemory = new ArrayList<>();
    private int autoIncrementId = 1;

    public MockStudentRepository() {
        // Migración exacta de tus datos estáticos hardcodeados en la vista anterior
        dbMemory.add(new Student(autoIncrementId++, "Alvarez", "Diego", "543210", "22"));
        dbMemory.add(new Student(autoIncrementId++, "Torres", "Lucia", "987654", "30"));
        dbMemory.add(new Student(autoIncrementId++, "Ramirez", "Carlos", "135791", "45"));
        dbMemory.add(new Student(autoIncrementId++, "Flores", "Gabriela", "246810", "18"));
        dbMemory.add(new Student(autoIncrementId++, "Diaz", "Fernando", "112233", "27"));
        dbMemory.add(new Student(autoIncrementId++, "Vargas", "Valeria", "445566", "35"));
        dbMemory.add(new Student(autoIncrementId++, "Castillo", "Alejandro", "778899", "51"));
        dbMemory.add(new Student(autoIncrementId++, "Morales", "Daniela", "990011", "23"));
        dbMemory.add(new Student(autoIncrementId++, "Herrera", "Javier", "223344", "40"));
        dbMemory.add(new Student(autoIncrementId++, "Castro", "Camila", "556677", "26"));
        dbMemory.add(new Student(autoIncrementId++, "Medina", "Ricardo", "889900", "48"));
        dbMemory.add(new Student(autoIncrementId++, "Aguilar", "Victoria", "121314", "33"));
        dbMemory.add(new Student(autoIncrementId++, "Suarez", "Sebastian", "151617", "20"));
        dbMemory.add(new Student(autoIncrementId++, "Salazar", "Natalia", "181920", "29"));
        dbMemory.add(new Student(autoIncrementId++, "Delgado", "Juan", "212223", "55"));
        dbMemory.add(new Student(autoIncrementId++, "Rios", "Valentina", "242526", "41"));
        dbMemory.add(new Student(autoIncrementId++, "Mendoza", "Gabriel", "272829", "19"));
        dbMemory.add(new Student(autoIncrementId++, "Ortega", "Isabella", "303132", "37"));
        dbMemory.add(new Student(autoIncrementId++, "Rojas", "Nicolas", "333435", "62"));
        dbMemory.add(new Student(autoIncrementId++, "Guerrero", "Paulina", "363738", "25"));
    }

    @Override
    public List<Student> findAll() {
        return new ArrayList<>(dbMemory);
    }

    @Override
    public void save(Student student) {
        student.setId(autoIncrementId++);
        dbMemory.add(student);
    }

    @Override
    public void update(Student student) {
        for (int i = 0; i < dbMemory.size(); i++) {
            if (dbMemory.get(i).getId() == student.getId()) {
                dbMemory.set(i, student);
                break;
            }
        }
    }

    @Override
    public void delete(int id) {
        dbMemory.removeIf(s -> s.getId() == id);
    }
}