package data.repository;

import domain.model.Student;
import domain.repository.StudentRepository;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class MockStudentRepository implements StudentRepository {
    private final Map<Integer, Student> store = new ConcurrentHashMap<>();
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
    }

    @Override
    public ArrayList<Student> findAll() {
        return new ArrayList<>(store.values());
    }

    @Override
    public void save(Student student) {
        if (student.getId() == 0) {
            int newId = idGenerator.getAndIncrement();
            student.setId(newId);
        }
        store.put(student.getId(), student);
    }

    @Override
    public void update(Student student) {
        if (!store.containsKey(student.getId())) {
            throw new IllegalArgumentException("Estudiante no encontrado para actualizar");
        }
        store.put(student.getId(), student);
    }

    @Override
    public void delete(int id) {
        if (!store.containsKey(id)) {
            throw new IllegalArgumentException("Estudiante no encontrado para eliminar");
        }
        store.remove(id);
    }
}