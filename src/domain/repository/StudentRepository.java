package domain.repository;

import java.util.ArrayList;

import domain.model.Student;

public interface StudentRepository {
    ArrayList<Student> findAll();

    void save(Student student);

    void update(Student student);

    void delete(int id);

}