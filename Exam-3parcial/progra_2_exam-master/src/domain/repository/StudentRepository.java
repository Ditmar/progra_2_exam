package domain.repository;

import java.util.List;

import domain.model.Student;

public interface StudentRepository {
    List<Student> findAll();

    void save(Student student);

    void update(Student student);

    void delete(String ci);

    boolean existsByCi(String ci);

}
