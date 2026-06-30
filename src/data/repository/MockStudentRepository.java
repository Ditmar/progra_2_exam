package data.repository;

import java.util.ArrayList;
import domain.model.Student;
import domain.repository.StudentRepository;
public class MockStudentRepository implements StudentRepository {
    private final ArrayList<Student> students = new ArrayList<>();

    public MockStudentRepository() {
        students.add(new Student(1, "Alvarez", "Diego", "543210", 22));
        students.add(new Student(2, "Torres", "Lucia", "987654", 30));
        students.add(new Student(3, "Ramirez", "Carlos", "135791", 45));
        students.add(new Student(4, "Flores", "Gabriela", "246810", 18));
        students.add(new Student(5, "Diaz", "Fernando", "112233", 27));
        students.add(new Student(6, "Vargas", "Valeria", "445566", 35));
        students.add(new Student(7, "Castillo", "Alejandro", "778899", 51));
        students.add(new Student(8, "Morales", "Daniela", "990011", 23));
        students.add(new Student(9, "Herrera", "Javier", "223344", 40));
        students.add(new Student(10, "Castro", "Camila", "556677", 26));
        students.add(new Student(11, "Medina", "Ricardo", "889900", 48));
        students.add(new Student(12, "Aguilar", "Victoria", "121314", 33));
        students.add(new Student(13, "Suarez", "Sebastian", "151617", 20));
        students.add(new Student(14, "Salazar", "Natalia", "181920", 29));
        students.add(new Student(15, "Delgado", "Juan", "212223", 55));
        students.add(new Student(16, "Rios", "Valentina", "242526", 41));
        students.add(new Student(17, "Mendoza", "Gabriel", "272829", 19));
        students.add(new Student(18, "Ortega", "Isabella", "303132", 37));
        students.add(new Student(19, "Rojas", "Nicolas", "333435", 62));
        students.add(new Student(20, "Guerrero", "Paulina", "363738", 25));
    }

    @Override
    public ArrayList<Student> findAll() {
        // Devolvemos la lista completa de estudiantes
        return students;
    }

    @Override
    public void save(Student student) {
       
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
    }

    @Override
    public void delete(int id) {
        students.removeIf(student -> student.getId() == id);
    }
}
    

