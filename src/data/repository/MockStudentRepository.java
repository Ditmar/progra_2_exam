package data.repository;

import domain.model.Student;
import domain.repository.StudentRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class MockStudentRepository implements StudentRepository {
    private final List<Student> students = new ArrayList<>();
    private final AtomicInteger idGenerator = new AtomicInteger(1);

    public MockStudentRepository() {
        addSampleData();
    }

    private void addSampleData() {
        students.add(new Student(idGenerator.getAndIncrement(), "Alvarez", "Diego", "543210", "22"));
        students.add(new Student(idGenerator.getAndIncrement(), "Torres", "Lucia", "987654", "30"));
        students.add(new Student(idGenerator.getAndIncrement(), "Ramirez", "Carlos", "135791", "45"));
        students.add(new Student(idGenerator.getAndIncrement(), "Flores", "Gabriela", "246810", "18"));
        students.add(new Student(idGenerator.getAndIncrement(), "Diaz", "Fernando", "112233", "27"));
        students.add(new Student(idGenerator.getAndIncrement(), "Vargas", "Valeria", "445566", "35"));
        students.add(new Student(idGenerator.getAndIncrement(), "Castillo", "Alejandro", "778899", "51"));
        students.add(new Student(idGenerator.getAndIncrement(), "Morales", "Daniela", "990011", "23"));
        students.add(new Student(idGenerator.getAndIncrement(), "Herrera", "Javier", "223344", "40"));
        students.add(new Student(idGenerator.getAndIncrement(), "Castro", "Camila", "556677", "26"));
        students.add(new Student(idGenerator.getAndIncrement(), "Medina", "Ricardo", "889900", "48"));
        students.add(new Student(idGenerator.getAndIncrement(), "Aguilar", "Victoria", "121314", "33"));
        students.add(new Student(idGenerator.getAndIncrement(), "Suarez", "Sebastian", "151617", "20"));
        students.add(new Student(idGenerator.getAndIncrement(), "Salazar", "Natalia", "181920", "29"));
        students.add(new Student(idGenerator.getAndIncrement(), "Delgado", "Juan", "212223", "55"));
        students.add(new Student(idGenerator.getAndIncrement(), "Rios", "Valentina", "242526", "41"));
        students.add(new Student(idGenerator.getAndIncrement(), "Mendoza", "Gabriel", "272829", "19"));
        students.add(new Student(idGenerator.getAndIncrement(), "Ortega", "Isabella", "303132", "37"));
        students.add(new Student(idGenerator.getAndIncrement(), "Rojas", "Nicolas", "333435", "62"));
        students.add(new Student(idGenerator.getAndIncrement(), "Guerrero", "Paulina", "363738", "25"));
    }

    @Override
    public ArrayList<Student> findAll() {
        return new ArrayList<>(students);
    }

    @Override
    public void save(Student student) {
        if (student.getId() == 0) {
            student.setId(idGenerator.getAndIncrement());
            students.add(student);
        } else {
            update(student);
        }
    }

    @Override
    public void update(Student student) {
        int index = findIndexById(student.getId());
        if (index >= 0) {
            students.set(index, student);
        } else {
            throw new IllegalArgumentException("Estudiante con ID " + student.getId() + " no encontrado");
        }
    }

    @Override
    public void delete(int id) {
        int index = findIndexById(id);
        if (index >= 0) {
            students.remove(index);
        } else {
            throw new IllegalArgumentException("Estudiante con ID " + id + " no encontrado");
        }
    }

    private int findIndexById(int id) {
        for (int i = 0; i < students.size(); i++) {
            if (students.get(i).getId() == id) {
                return i;
            }
        }
        return -1;
    }
}