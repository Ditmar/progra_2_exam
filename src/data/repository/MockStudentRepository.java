package data.repository;

import domain.model.Student;
import domain.repository.StudentRepository;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class MockStudentRepository implements StudentRepository {
    private final Map<Integer, Student> store = new LinkedHashMap<>();
    private final AtomicInteger idGenerator = new AtomicInteger(1);

    public MockStudentRepository() {
        addSampleData();
    }

    private void addSampleData() {
        save(new Student("Alvarez", "Diego", "543210", "22"));
        save(new Student("Torres", "Lucia", "987654", "30"));
        save(new Student("Ramirez", "Carlos", "135791", "45"));
        save(new Student("Flores", "Gabriela", "246810", "18"));
        save(new Student("Diaz", "Fernando", "112233", "27"));
        save(new Student("Vargas", "Valeria", "445566", "35"));
        save(new Student("Castillo", "Alejandro", "778899", "51"));
        save(new Student("Morales", "Daniela", "990011", "23"));
        save(new Student("Herrera", "Javier", "223344", "40"));
        save(new Student("Castro", "Camila", "556677", "26"));
    }

    @Override
    public ArrayList<Student> findAll() {
        return new ArrayList<>(store.values());
    }

    @Override
    public void save(Student student) {
        if (student.getId() == 0) {
            student.setId(idGenerator.getAndIncrement());
        }
        store.put(student.getId(), student);
    }

    @Override
    public void update(Student student) {
        if (!store.containsKey(student.getId())) {
            throw new IllegalArgumentException("Estudiante no encontrado");
        }
        store.put(student.getId(), student);
    }

    @Override
    public void delete(int id) {
        if (!store.containsKey(id)) {
            throw new IllegalArgumentException("Estudiante no encontrado");
        }
        store.remove(id);
    }
}