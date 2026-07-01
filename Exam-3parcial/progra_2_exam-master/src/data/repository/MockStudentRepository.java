package data.repository;

import java.util.ArrayList;
import java.util.List;

import domain.model.Student;
import domain.repository.StudentRepository;

public class MockStudentRepository implements StudentRepository{

    private final ArrayList<Student> students = new ArrayList<>();

        public MockStudentRepository(){
        students.add(new Student("Perez", "Juan", "123456", "100"));
        students.add(new Student("Lopez", "Maria", "654321", "95"));
        students.add(new Student("Garcia", "Pedro", "789456", "87"));
        students.add(new Student("Alvarez", "Diego", "543210", "22"));
        students.add(new Student("Torres", "Lucia", "987654", "30"));
        students.add(new Student("Ramirez", "Carlos", "135791", "45"));
        students.add(new Student("Flores", "Gabriela", "246810", "18"));
        students.add(new Student("Diaz", "Fernando", "112233", "27"));
        students.add(new Student("Vargas", "Valeria", "445566", "35"));
        students.add(new Student("Castillo", "Alejandro", "778899", "51"));
        students.add(new Student("Morales", "Daniela", "990011", "23"));
        students.add(new Student("Herrera", "Javier", "223344", "40"));
        students.add(new Student("Castro", "Camila", "556677", "26"));
        students.add(new Student("Medina", "Ricardo", "889900", "48"));
        students.add(new Student("Aguilar", "Victoria", "121314", "33"));
        students.add(new Student("Suarez", "Sebastian", "151617", "20"));
        students.add(new Student("Salazar", "Natalia", "181920", "29"));
        students.add(new Student("Delgado", "Juan", "212223", "55"));
        students.add(new Student("Rios", "Valentina", "242526", "41"));
        students.add(new Student("Mendoza", "Gabriel", "272829", "19"));
        students.add(new Student("Ortega", "Isabella", "303132", "37"));
        students.add(new Student("Rojas", "Nicolas", "333435", "62"));
        students.add(new Student("Guerrero", "Paulina", "363738", "25"));

        }

        @Override
        public List<Student> findAll() {
            return new ArrayList<>(students);
        }
    
        @Override
        public void save(Student student){
            students.add(student);
        }

        @Override
        public void update(Student student){
            for(int i = 0; i < students.size(); i++){

                if(students.get(i).getCi().equals(student.getCi())){
                    students.set(i, student);
                    return;
                }
            }
            throw new IllegalArgumentException("No existe un estudiante con CI: " + student.getCi());
        }

        @Override
        public void delete(String ci){
            students.removeIf(student -> student.getCi().equals(ci));
        }

        @Override
        public boolean existsByCi(String ci) {
            return students.stream()
            .anyMatch(student -> student.getCi().equals(ci));
        }

}
